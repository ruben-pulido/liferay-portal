/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.MasterLayoutImportEntry;
import com.liferay.layout.page.template.importer.MasterLayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class MasterLayoutsImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();
	}

	@Test
	public void testImportMasterLayoutDropzoneUnallowedFragments()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry("dropzone-unallowed-fragments");

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<String> childrenItemIds =
			mainLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		String childItemId = childrenItemIds.get(0);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(childItemId);

		Assert.assertTrue(
			layoutStructureItem instanceof DropZoneLayoutStructureItem);

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)layoutStructureItem;

		Assert.assertNotNull(layoutStructure);

		Assert.assertEquals(
			Arrays.asList(
				"BASIC_COMPONENT-button", "BASIC_COMPONENT-card",
				"FEATURED_CONTENT-banner-center",
				"com.liferay.fragment.internal.renderer." +
					"ContentObjectFragmentRenderer",
				"content-display"),
			dropZoneLayoutStructureItem.getFragmentEntryKeys());
		Assert.assertTrue(
			dropZoneLayoutStructureItem.isAllowNewFragmentEntries());
	}

	private void _addZipWriterEntry(ZipWriter zipWriter, URL url)
		throws IOException {

		String entryPath = url.getPath();

		String zipPath = StringUtil.removeSubstring(
			entryPath, _LAYOUT_PATE_TEMPLATES_PATH);

		zipWriter.addEntry(zipPath, url.openStream());
	}

	private File _generateZipFile(String basePath) throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		StringBuilder sb = new StringBuilder(3);

		sb.append(_LAYOUT_PATE_TEMPLATES_PATH + basePath);
		sb.append(StringPool.FORWARD_SLASH + _ROOT_FOLDER);
		sb.append(StringPool.FORWARD_SLASH);

		Enumeration<URL> enumeration = _bundle.findEntries(
			sb.toString(),
			LayoutPageTemplateExportImportConstants.FILE_NAME_MASTER_LAYOUT,
			true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(zipWriter, url);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private LayoutPageTemplateEntry _getImportLayoutPageTemplateEntry(
			String component)
		throws Exception {

		File file = _generateZipFile(component);

		List<MasterLayoutImportEntry> masterLayoutImportEntries = null;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			masterLayoutImportEntries = _masterLayoutsImporter.importFile(
				_user.getUserId(), _group.getGroupId(), file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(masterLayoutImportEntries);

		Assert.assertEquals(
			masterLayoutImportEntries.toString(), 1,
			masterLayoutImportEntries.size());

		MasterLayoutImportEntry masterLayoutImportEntry =
			masterLayoutImportEntries.get(0);

		Assert.assertEquals(
			MasterLayoutImportEntry.Status.IMPORTED,
			masterLayoutImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			masterLayoutImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	private void _populateZipWriter(ZipWriter zipWriter, URL url)
		throws IOException {

		String zipPath = StringUtil.removeSubstring(
			url.getFile(), _LAYOUT_PATE_TEMPLATES_PATH);

		zipWriter.addEntry(zipPath, url.openStream());

		String path = FileUtil.getPath(url.getPath());

		Enumeration<URL> enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_MASTER_LAYOUT,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl);
		}

		enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_DEFINITION,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl);
		}
	}

	private static final String _LAYOUT_PATE_TEMPLATES_PATH =
		"com/liferay/layout/page/template/internal/importer/test/dependencies/";

	private static final String _ROOT_FOLDER = "master-pages";

	private Bundle _bundle;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private MasterLayoutsImporter _masterLayoutsImporter;

	@Inject
	private Portal _portal;

	private User _user;

}