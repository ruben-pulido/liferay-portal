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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.CollectionItemLayoutStructureItem;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class ExportImportDisplayPagesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_serviceContext1 = ServiceContextTestUtil.getServiceContext(
			_group1, TestPropsValues.getUserId());
		_serviceContext2 = ServiceContextTestUtil.getServiceContext(
			_group2, TestPropsValues.getUserId());
	}

	private String _getJsonFileAsString(
			String jsonFileName, Map<String, String> values)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_read(jsonFileName, values));

		return jsonObject.toString();
	}

	@Test
	public void testExportImportDisplayPageCollectionDisplayFragmentTextFieldWithMapping() throws Exception {
		String className = "com.liferay.journal.model.JournalArticle";

		long classNameId = _portal.getClassNameId(className);

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		long classTypeId = 0;

		List<ClassType> classTypes = infoDisplayContributor.getClassTypes(
			_group1.getGroupId(), LocaleUtil.getSiteDefault());

		for (ClassType classType : classTypes) {
			if (Objects.equals(classType.getName(), "Basic Web Content")) {
				classTypeId = classType.getClassTypeId();
			}
		}

		Assert.assertNotEquals(0, classTypeId);

		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext1.getUserId(),
				_serviceContext1.getScopeGroupId(), 0, classNameId, classTypeId,
				"Display Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext1);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group1.getGroupId(),
				"Collection One",
				_getTypeSettings(
					_group1.getGroupId(), PortalUtil.getClassNameId(
						JournalArticle.class)),
				_serviceContext1);

		Map<String, String> editableValuesValues = HashMapBuilder.put(
			"classNameId",
			String.valueOf(
				_portal.getClassNameId(AssetListEntry.class.getName()))
		).put(
			"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
		).put(
			"itemType", assetListEntry.getAssetEntryType()
		).put(
			"title", assetListEntry.getTitle()
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		).build();

		FragmentEntry collectionFragmentEntry = _addFragmentEntry(
			_group1.getGroupId(),
			"fragment_entry_with_configuration_collectionselector_dynamic_" +
				"collection.html",
			"configuration_collectionselector.json", new HashMap<>());

		FragmentEntryLink collectionFragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		collectionFragmentEntryLink.setHtml(collectionFragmentEntry.getHtml());
		collectionFragmentEntryLink.setConfiguration(collectionFragmentEntry.getConfiguration());
		collectionFragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration_" +
					"collectionselector_dynamic_collection.json",
				editableValuesValues));

		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";

		FragmentEntry fragmentEntry = _addFragmentEntry(
			_group1.getGroupId(), "test-text-fragment", "Test Text Fragment",
			html);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group1.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0,
				_portal.getClassNameId(Layout.class),
				layoutPageTemplateEntry1.getPlid(), StringPool.BLANK, html,
				StringPool.BLANK,
				_read(
					"export_import_display_page_with_collection_display_" +
						"fragment_field_text_config.json"),
				_read(
					"export_import_display_page_with_collection_display_" +
						"fragment_field_text_editable_values.json"),
				StringPool.BLANK, 0, null, _serviceContext1);

		HashMap<String, String> valuesMap = HashMapBuilder.put(
				"CLASS_PK",
				String.valueOf(assetListEntry.getAssetListEntryId())
			).put(
				"ITEM_SUBTYPE",
				assetListEntry.getAssetEntryType()
			).put(
				"CLASS_NAME_ID",
				String.valueOf(
					_portal.getClassNameId(AssetListEntry.class.getName()))
			).put(
				"FRAGMENT_ENTRY_LINK1_ID",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).build();

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group1.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			layoutPageTemplateEntry1.getPlid(),
			StringUtil.replace(
				_read(
					"export_import_display_page_with_collection_display_" +
						"layout_data.json"),
				"${", "}", valuesMap),
			_serviceContext1);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext1);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			_group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			fileEntry.getFileEntryId());

		long[] layoutPageTemplateEntryIds = {
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId()
		};

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			layoutPageTemplateEntryIds);

		_addFragmentEntry(
			_group2.getGroupId(), "test-text-fragment", "Test Text Fragment",
			html);

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
					false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplatesImporterResultEntry layoutPageTemplateImportEntry =
			layoutPageTemplatesImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry2);

		LayoutPageTemplateStructure layoutPageTemplateStructure1 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry1.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry1.getPlid());
		LayoutPageTemplateStructure layoutPageTemplateStructure2 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry2.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry2.getPlid());

		LayoutStructure layoutStructure1 = LayoutStructure.of(
			layoutPageTemplateStructure1.getData(0));
		LayoutStructure layoutStructure2 = LayoutStructure.of(
			layoutPageTemplateStructure2.getData(0));

		CollectionLayoutStructureItem collectionLayoutStructureItem1 =
			_getCollectionLayoutStructureItem(layoutStructure1);
		CollectionLayoutStructureItem collectionLayoutStructureItem2 =
			_getCollectionLayoutStructureItem(layoutStructure2);

		_validateCollectionLayoutStructureItem(
			collectionLayoutStructureItem1, collectionLayoutStructureItem2);

		List<String> collectionLayoutStructureItemChildrenItemIds1 =
			collectionLayoutStructureItem1.getChildrenItemIds();
		List<String> collectionLayoutStructureItemChildrenItemIds2 =
			collectionLayoutStructureItem2.getChildrenItemIds();

		CollectionItemLayoutStructureItem collectionItemLayoutStructureItem1 =
			(CollectionItemLayoutStructureItem)layoutStructure1.getLayoutStructureItem(
				collectionLayoutStructureItemChildrenItemIds1.get(0));
		CollectionItemLayoutStructureItem collectionItemLayoutStructureItem2 =
			(CollectionItemLayoutStructureItem)layoutStructure2.getLayoutStructureItem(
				collectionLayoutStructureItemChildrenItemIds2.get(0));

		_validateCollectionItemLayoutStructureItem(
			collectionItemLayoutStructureItem1, collectionItemLayoutStructureItem2);

		List<String> collectionItemLayoutStructureItemChildrenItemIds1 =
			collectionItemLayoutStructureItem1.getChildrenItemIds();
		List<String> collectionItemLayoutStructureItemChildrenItemIds2 =
			collectionItemLayoutStructureItem2.getChildrenItemIds();

		FragmentLayoutStructureItem fragmentLayoutStructureItem1 =
			(FragmentLayoutStructureItem)
				layoutStructure1.getLayoutStructureItem(
					collectionItemLayoutStructureItemChildrenItemIds1.get(0));
		FragmentLayoutStructureItem fragmentLayoutStructureItem2 =
			(FragmentLayoutStructureItem)
				layoutStructure2.getLayoutStructureItem(
					collectionItemLayoutStructureItemChildrenItemIds2.get(0));

		_validateFragmentLayoutStructureItem(
			fragmentLayoutStructureItem1, fragmentLayoutStructureItem2);
	}

	private String _getTypeSettings(long groupId, long classNameId) {
		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.put("anyAssetType", String.valueOf(classNameId));
		unicodeProperties.put(
			"anyClassTypeDLFileEntryAssetRendererFactory", "true");
		unicodeProperties.put(
			"anyClassTypeJournalArticleAssetRendererFactory", "true");
		unicodeProperties.put("classNameIds", String.valueOf(classNameId));
		unicodeProperties.put("groupIds", String.valueOf(groupId));
		unicodeProperties.put("orderByColumn1", "modifiedDate");
		unicodeProperties.put("orderByColumn2", "title");
		unicodeProperties.put("orderByType1", "DESC");
		unicodeProperties.put("orderByType2", "ASC");
		unicodeProperties.put(
			"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory",
			"false");
		unicodeProperties.put(
			"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory",
			"false");

		return unicodeProperties.toString();
	}

	private CollectionLayoutStructureItem _getCollectionLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof CollectionLayoutStructureItem);

		return (CollectionLayoutStructureItem)layoutStructureItem;
	}

	private LayoutStructureItem _getMainChildLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<String> childrenItemIds =
			mainLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		String childItemId = childrenItemIds.get(0);

		return layoutStructure.getLayoutStructureItem(childItemId);
	}

	private void _validateCollectionLayoutStructureItem(
		CollectionLayoutStructureItem expectedCollectionLayoutStructureItem,
		CollectionLayoutStructureItem actualCollectionLayoutStructureItem) {

		Assert.assertEquals(
			expectedCollectionLayoutStructureItem.getNumberOfColumns(),
			actualCollectionLayoutStructureItem.getNumberOfColumns());
		Assert.assertEquals(
			expectedCollectionLayoutStructureItem.getNumberOfItems(),
			actualCollectionLayoutStructureItem.getNumberOfItems());
	}

	private void _validateFragmentLayoutStructureItem(
			FragmentLayoutStructureItem expectedFragmentLayoutStructureItem,
			FragmentLayoutStructureItem actualFragmentLayoutStructureItem)
		throws Exception {

		long expectedFragmentEntryLinkId =
			expectedFragmentLayoutStructureItem.getFragmentEntryLinkId();
		long actualFragmentEntryLinkId =
			actualFragmentLayoutStructureItem.getFragmentEntryLinkId();

		FragmentEntryLink expectedFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				expectedFragmentEntryLinkId);
		FragmentEntryLink actualFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				actualFragmentEntryLinkId);

		String expectedEditableValues =
			expectedFragmentEntryLink.getEditableValues();
		String actualEditableValues =
			actualFragmentEntryLink.getEditableValues();

		JSONObject expectedEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(expectedEditableValues);
		JSONObject actualEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(actualEditableValues);

		JSONObject expectedBackgroundImageFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.background.image." +
					"BackgroundImageFragmentEntryProcessor");
		JSONObject actualBackgroundImageFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.background.image." +
					"BackgroundImageFragmentEntryProcessor");

		Assert.assertEquals(
			expectedBackgroundImageFragmentEntryProcessorJSONObject.
				toJSONString(),
			actualBackgroundImageFragmentEntryProcessorJSONObject.
				toJSONString());

		JSONObject expectedEditableFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");
		JSONObject actualEditableFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		JSONObject expectedElementTextJSONObject =
			expectedEditableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-text");
		JSONObject actualElementTextJSONObject =
			actualEditableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-text");

		Assert.assertEquals(
			expectedElementTextJSONObject.getString("en_US"),
			actualElementTextJSONObject.getString("en_US"));

		Assert.assertEquals(
			expectedElementTextJSONObject.getString("es_ES"),
			actualElementTextJSONObject.getString("es_ES"));

		JSONObject expectedElementTextConfigJSONObject =
			expectedElementTextJSONObject.getJSONObject("config");
		JSONObject actualElementTextConfigJSONObject =
			actualElementTextJSONObject.getJSONObject("config");

		Assert.assertEquals(
			expectedElementTextConfigJSONObject.toJSONString(),
			actualElementTextConfigJSONObject.toJSONString());

		JSONObject expectedFreeMarkerFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");
		JSONObject actualFreeMarkerFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");

		Assert.assertEquals(
			expectedFreeMarkerFragmentEntryProcessorJSONObject.toJSONString(),
			actualFreeMarkerFragmentEntryProcessorJSONObject.toJSONString());

		Assert.assertEquals(
			expectedFragmentEntryLink.getPosition(),
			actualFragmentEntryLink.getPosition());
	}

	private void _validateCollectionItemLayoutStructureItem(
		CollectionItemLayoutStructureItem expectedCollectionItemLayoutStructureItem,
		CollectionItemLayoutStructureItem actualCollectionItemLayoutStructureItem) {

		Assert.assertEquals(
			expectedCollectionItemLayoutStructureItem.getItemConfigJSONObject(),
			actualCollectionItemLayoutStructureItem.getItemConfigJSONObject());
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
			StringPool.BLANK, html, StringPool.BLANK, StringPool.BLANK, 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private FragmentEntry _addFragmentEntry(
			long groupId, String htmlFile, String configurationFile,
			Map<String, String> values)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				groupId, "Fragment Collection", StringPool.BLANK,
				serviceContext);

		String configuration = null;

		if (configurationFile != null) {
			configuration = _read(configurationFile);

			configuration = StringUtil.replace(
				configuration, "${", "}", values);
		}

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollection.getFragmentCollectionId(),
			"fragment-entry", "Fragment Entry", null,
			_read(htmlFile), null, configuration, 0, 0,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testExportImportDisplayPage() throws Exception {
		String className = "com.liferay.journal.model.JournalArticle";

		long classNameId = _portal.getClassNameId(className);

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		long classTypeId = 0;

		List<ClassType> classTypes = infoDisplayContributor.getClassTypes(
			_group1.getGroupId(), LocaleUtil.getSiteDefault());

		for (ClassType classType : classTypes) {
			if (Objects.equals(classType.getName(), "Basic Web Content")) {
				classTypeId = classType.getClassTypeId();
			}
		}

		Assert.assertNotEquals(0, classTypeId);

		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext1.getUserId(),
				_serviceContext1.getScopeGroupId(), 0, classNameId, classTypeId,
				"Display Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext1);

		Layout layout1 = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry1.getPlid());

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group1.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			layoutPageTemplateEntry1.getPlid(),
			_read("export_import_display_page_layout_data.json"),
			_serviceContext1);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext1);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			_group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			fileEntry.getFileEntryId());

		long[] layoutPageTemplateEntryIds = {
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId()
		};

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			layoutPageTemplateEntryIds);

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
					false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplatesImporterResultEntry layoutPageTemplateImportEntry =
			layoutPageTemplatesImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry2);

		Layout layout2 = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry2.getPlid());

		Assert.assertNotNull(layout2);

		Assert.assertEquals(
			layout1.getMasterLayoutPlid(), layout2.getMasterLayoutPlid());

		Assert.assertEquals(
			layoutPageTemplateEntry1.getName(),
			layoutPageTemplateEntry2.getName());
		Assert.assertEquals(
			layoutPageTemplateEntry1.getType(),
			layoutPageTemplateEntry2.getType());

		LayoutPageTemplateStructure layoutPageTemplateStructure1 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry1.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry1.getPlid());
		LayoutPageTemplateStructure layoutPageTemplateStructure2 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry2.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry2.getPlid());

		LayoutStructure layoutStructure1 = LayoutStructure.of(
			layoutPageTemplateStructure1.getData(0));
		LayoutStructure layoutStructure2 = LayoutStructure.of(
			layoutPageTemplateStructure2.getData(0));

		_validateRootLayoutStructureItem(
			(RootLayoutStructureItem)
				layoutStructure1.getMainLayoutStructureItem(),
			(RootLayoutStructureItem)
				layoutStructure2.getMainLayoutStructureItem());
	}

	private String _read(String fileName, Map<String, String> values)
		throws Exception {
		String template = new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));

		return StringUtil.replace(template, "${", "}", values);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateRootLayoutStructureItem(
		RootLayoutStructureItem expectedRootLayoutStructureItem,
		RootLayoutStructureItem actualRootLayoutStructureItem) {

		Assert.assertEquals(
			expectedRootLayoutStructureItem.getChildrenItemIds(),
			actualRootLayoutStructureItem.getChildrenItemIds());

		JSONObject expectedItemConfigJSONObject =
			expectedRootLayoutStructureItem.getItemConfigJSONObject();
		JSONObject actualItemConfigJSONObject =
			actualRootLayoutStructureItem.getItemConfigJSONObject();

		Assert.assertEquals(
			expectedItemConfigJSONObject.toJSONString(),
			actualItemConfigJSONObject.toJSONString());

		Assert.assertEquals(
			expectedRootLayoutStructureItem.getItemType(),
			actualRootLayoutStructureItem.getItemType());
		Assert.assertEquals(
			expectedRootLayoutStructureItem.getParentItemId(),
			actualRootLayoutStructureItem.getParentItemId());
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryService _fragmentEntryService;
	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template/export_display_page"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

}