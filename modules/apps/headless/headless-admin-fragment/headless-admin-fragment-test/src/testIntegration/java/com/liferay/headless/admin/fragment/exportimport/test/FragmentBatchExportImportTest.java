/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentBatchExportImportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_importedGroup = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-A")
	public void testExportImport() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection();

		_exportImport();

		FragmentCollection importedFragmentCollection =
			_fragmentCollectionLocalService.
				getFragmentCollectionByExternalReferenceCode(
					fragmentCollection.getExternalReferenceCode(),
					_importedGroup.getGroupId());

		Assert.assertEquals(
			fragmentCollection.getDescription(),
			importedFragmentCollection.getDescription());
		Assert.assertEquals(
			fragmentCollection.getFragmentCollectionKey(),
			importedFragmentCollection.getFragmentCollectionKey());
		Assert.assertEquals(
			fragmentCollection.getName(), importedFragmentCollection.getName());
		Assert.assertEquals(
			fragmentCollection.getUuid(), importedFragmentCollection.getUuid());
	}

	private FragmentCollection _addFragmentCollection() throws Exception {
		return _fragmentCollectionLocalService.addFragmentCollection(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, _getServiceContext());
	}

	private File _export(Date endDate, Date startDate) throws Exception {
		User user = TestPropsValues.getUser();

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportPortletSettingsMap(
					user, layout.getPlid(), _group.getGroupId(),
					FragmentPortletKeys.FRAGMENT, _getParameterMap(),
					StringPool.BLANK);

		if ((endDate != null) && (startDate != null)) {
			settingsMap.put("endDate", endDate);
			settingsMap.put("startDate", startDate);
		}

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
					settingsMap);

		return _exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);
	}

	private void _exportImport() throws Exception {
		_import(_export(null, null));
	}

	private Map<String, String[]> _getParameterMap() {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_ALL});

		return parameterMap;
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	private void _import(File larFile) throws Exception {
		User user = TestPropsValues.getUser();

		Layout layout = LayoutTestUtil.addTypePortletLayout(_importedGroup);

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportPortletSettingsMap(
							user, layout.getPlid(), _importedGroup.getGroupId(),
							FragmentPortletKeys.FRAGMENT, _getParameterMap()));

		_exportImportLocalService.importPortletInfo(
			exportImportConfiguration, larFile);
	}

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _importedGroup;

}