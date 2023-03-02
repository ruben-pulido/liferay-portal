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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ImportExportInterEnvironmentLayoutPageTemplateEntriesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group1.getCompanyId());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailable()
		throws Exception {

		_addTextFragmentEntry(_group1.getGroupId());
		_addTextFragmentEntry(_group2.getGroupId());

		String journalArticleExternalReferenceCode =
			RandomTestUtil.randomString();

		JournalArticle journalArticle1 = _addJournalArticle(
			journalArticleExternalReferenceCode, _group1.getGroupId());
		JournalArticle journalArticle2 = _addJournalArticle(
			journalArticleExternalReferenceCode, _group2.getGroupId());

		Assert.assertNotEquals(
			journalArticle1.getResourcePrimKey(),
			journalArticle2.getResourcePrimKey());

		Assert.assertEquals(
			journalArticle1.getExternalReferenceCode(),
			journalArticle2.getExternalReferenceCode());

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK", String.valueOf(journalArticle1.getResourcePrimKey())
		).build();
		Map<String, String> stringValuesMap = HashMapBuilder.put(
			"SITE_KEY", _group1.getGroupKey()
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, stringValuesMap);
		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, stringValuesMap);

		_validateImportExport(
			expectedFile, inputFile, _group1.getGroupId(), _group2.getGroupId());
	}

	private AssetListEntry _addAssetListEntry(long groupId)
		throws PortalException {

		return _assetListEntryLocalService.addAssetListEntry(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_MANUAL,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private void _addDisplayPageTemplate(JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group1.getCreatorUserId(), _group1.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), _group1.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);
	}

	private FragmentEntry _addFragmentEntry(
			long groupId, String key, String name, String html)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), groupId, "Test Collection",
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(), key, name,
			StringPool.BLANK, html, StringPool.BLANK, false, StringPool.BLANK,
			null, 0, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private JournalArticle _addJournalArticle(
		String externalReferenceCode, long groupId) throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			externalReferenceCode, groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, StringPool.BLANK,
			true);

		journalArticle.setSmallImage(true);
		journalArticle.setSmallImageURL(
			"https://avatars1.githubusercontent.com/u/131436");

		return JournalTestUtil.updateArticle(journalArticle);
	}

	private void _addTextFragmentEntry(long groupId) throws Exception {
		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";

		_addFragmentEntry(
			groupId, "test-text-fragment", "Test Text Fragment", html);
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws IOException {

		String entryPath = url.getPath();

		String zipPath = StringUtil.removeSubstring(
			entryPath, _LAYOUT_PATE_TEMPLATES_PATH);

		String content = StringUtil.read(url.openStream());

		content = StringUtil.replace(content, "\"${", "}\"", numberValuesMap);
		content = StringUtil.replace(content, "£{", "}", stringValuesMap);

		zipWriter.addEntry(zipPath, content);
	}

	private File _generateZipFile(
			String testPath, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		Enumeration<URL> enumeration = _bundle.findEntries(
			StringBundler.concat(
				_LAYOUT_PATE_TEMPLATES_PATH + testPath,
				StringPool.FORWARD_SLASH + _ROOT_FOLDER,
				StringPool.FORWARD_SLASH),
			LayoutPageTemplateExportImportConstants.
				FILE_NAME_PAGE_TEMPLATE_COLLECTION,
			true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(
					zipWriter, url, numberValuesMap, stringValuesMap);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private LayoutPageTemplateEntry _getImportLayoutPageTemplateEntry(
			File file, long groupId, boolean overwrite,
			LayoutsImporterResultEntry.Status status)
		throws Exception {

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(_group1, TestPropsValues.getUserId()));

		try {
			layoutsImporterResultEntries = _layoutsImporter.importFile(
				TestPropsValues.getUserId(), groupId, 0, file, overwrite);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutPageTemplateImportEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(status, layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				groupId, layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	private ServiceContext _getServiceContext(Group group, long userId)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, userId);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(
			_layoutLocalService.getLayout(TestPropsValues.getPlid()));

		LayoutSet layoutSet = _group1.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group1.getGroupId());
		themeDisplay.setSiteGroupId(_group1.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private File _importExportLayoutPageTemplateEntry(
			File file, long groupId, boolean overwrite,
			LayoutsImporterResultEntry.Status status)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry(file, groupId, overwrite, status);

		return ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});
	}

	private void _populateZipWriter(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws IOException {

		String zipPath = StringUtil.removeSubstring(
			url.getFile(), _LAYOUT_PATE_TEMPLATES_PATH);

		zipWriter.addEntry(zipPath, url.openStream());

		String path = FileUtil.getPath(url.getPath());

		Enumeration<URL> enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_TEMPLATE,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}

		enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_DEFINITION,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}

		enumeration = _bundle.findEntries(path, "thumbnail.png", true);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}
	}

	private void _validateFile(File inputFile, File outputFile)
		throws Exception {

		ZipFile inputZipFile = new ZipFile(inputFile);

		Enumeration<? extends ZipEntry> inputEnumeration =
			inputZipFile.entries();

		Map<String, String> fileNameFileContentMap = new HashMap<>();

		int numberOfInputFiles = 0;

		while (inputEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = inputEnumeration.nextElement();

			if (!zipEntry.isDirectory()) {
				numberOfInputFiles++;

				String content = StringUtil.read(
					inputZipFile.getInputStream(zipEntry));

				String name = zipEntry.getName();

				String[] parts = name.split("/");

				fileNameFileContentMap.put(parts[parts.length - 1], content);
			}
		}

		ZipFile outputZipFile = new ZipFile(outputFile);

		Enumeration<? extends ZipEntry> outputEnumeration =
			outputZipFile.entries();

		int numberOfOutputFiles = 0;

		while (outputEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = outputEnumeration.nextElement();

			if (!zipEntry.isDirectory()) {
				numberOfOutputFiles++;

				String name = zipEntry.getName();

				String[] parts = name.split("/");

				Assert.assertEquals(
					_objectMapper.readTree(
						fileNameFileContentMap.get(parts[parts.length - 1])),
					_objectMapper.readTree(
						StringUtil.read(
							outputZipFile.getInputStream(zipEntry))));
			}
		}

		Assert.assertEquals(numberOfInputFiles, numberOfOutputFiles);
		Assert.assertTrue(numberOfInputFiles > 0);
	}

	private void _validateImportExport(
			File expectedFile, File inputFile, long groupId1, long groupId2)
		throws Exception {

		File outputFile1 = _importExportLayoutPageTemplateEntry(
			inputFile, groupId1, false,
			LayoutsImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile1);

		_groupLocalService.deleteGroup(groupId1);

		File outputFile2 = _importExportLayoutPageTemplateEntry(
			outputFile1, groupId2, true,
			LayoutsImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile2);
	}

	private static final String _LAYOUT_PATE_TEMPLATES_PATH =
		"com/liferay/layout/page/template/admin/web/internal/portlet/action" +
			"/test/dependencies/inter_environment_import_export/page_templates/";

	private static final Locale[] _locales = {
		LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.SPAIN
	};

	private static final String _ROOT_FOLDER = "page-templates";

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	private Bundle _bundle;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ObjectMapper _objectMapper;

	@Inject
	private Portal _portal;

}