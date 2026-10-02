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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

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
	@TestInfo({"LPD-A", "LPD-B"})
	public void testExportImport() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection();

		String configuration = JSONUtil.put(
			"fieldSets",
			JSONUtil.put(
				JSONUtil.put(
					"fields",
					JSONUtil.put(
						JSONUtil.put(
							"dataType", "string"
						).put(
							"defaultValue", "Default"
						).put(
							"label", "Text"
						).put(
							"name", "text"
						).put(
							"type", "text"
						))))
		).toString();

		FragmentEntry fragmentEntry = _addFragmentEntry(
			configuration, fragmentCollection);

		FragmentEntry draftFragmentEntry = _fragmentEntryLocalService.getDraft(
			fragmentEntry.getFragmentEntryId());

		draftFragmentEntry.setHtml("<div>Draft</div>");

		_fragmentEntryLocalService.updateDraft(draftFragmentEntry);

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

		FragmentEntry importedFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				fragmentEntry.getExternalReferenceCode(),
				_importedGroup.getGroupId());

		JSONAssert.assertEquals(
			configuration, importedFragmentEntry.getConfiguration(), false);

		Assert.assertEquals(
			fragmentEntry.getCss(), importedFragmentEntry.getCss());
		Assert.assertEquals(
			importedFragmentCollection.getFragmentCollectionId(),
			importedFragmentEntry.getFragmentCollectionId());
		Assert.assertEquals(
			fragmentEntry.getFragmentEntryKey(),
			importedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			fragmentEntry.getHtml(), importedFragmentEntry.getHtml());
		Assert.assertEquals(
			fragmentEntry.getJs(), importedFragmentEntry.getJs());
		Assert.assertEquals(
			fragmentEntry.getName(), importedFragmentEntry.getName());
		Assert.assertEquals(
			fragmentEntry.getUuid(), importedFragmentEntry.getUuid());

		FragmentEntry importedDraftFragmentEntry =
			_fragmentEntryLocalService.fetchDraft(
				importedFragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"<div>Draft</div>", importedDraftFragmentEntry.getHtml());
	}

	@Test
	@TestInfo("LPD-B")
	public void testExportImportWithMissingItemReference() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0);

		FragmentEntry fragmentEntry = _addFragmentEntry(
			JSONUtil.put(
				"fieldSets",
				JSONUtil.put(
					JSONUtil.put(
						"fields",
						JSONUtil.put(
							JSONUtil.put(
								"defaultValue",
								JSONUtil.put(
									"className", JournalArticle.class.getName()
								).put(
									"classNameId",
									String.valueOf(
										PortalUtil.getClassNameId(
											JournalArticle.class.getName()))
								).put(
									"classPK",
									String.valueOf(
										journalArticle.getResourcePrimKey())
								)
							).put(
								"label", "Item"
							).put(
								"name", "item"
							).put(
								"type", "itemSelector"
							))))
			).toString(),
			_addFragmentCollection());

		File larFile = _export(null, null);

		_journalArticleLocalService.deleteArticle(journalArticle);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.headless.admin.fragment.internal.util." +
					"ConfigurationUtil",
				LoggerTestUtil.WARN)) {

			_import(larFile);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Optional reference generated for missing entity with ",
					"class name ", JournalArticle.class.getName(),
					", external reference code ",
					journalArticle.getExternalReferenceCode(),
					", and null scope with current scope ID ",
					_importedGroup.getGroupId()),
				logEntry.getMessage());
		}

		FragmentEntry importedFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				fragmentEntry.getExternalReferenceCode(),
				_importedGroup.getGroupId());

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
			importedFragmentEntry.getConfiguration());

		JSONObject defaultValueJSONObject = JSONUtil.getValueAsJSONObject(
			configurationJSONObject, "JSONArray/fieldSets", "Object/0",
			"JSONArray/fields", "Object/0", "JSONObject/defaultValue");

		Assert.assertEquals(
			journalArticle.getExternalReferenceCode(),
			defaultValueJSONObject.getString("externalReferenceCode"));
	}

	private FragmentCollection _addFragmentCollection() throws Exception {
		return _fragmentCollectionLocalService.addFragmentCollection(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, _getServiceContext());
	}

	private FragmentEntry _addFragmentEntry(
			String configuration, FragmentCollection fragmentCollection)
		throws Exception {

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			".test {}", "<div>Approved</div>", "console.log('test');", false,
			configuration, null, 0, false, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, _getServiceContext());
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

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _importedGroup;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}