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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.liferay.portal.kernel.util.GetterUtil.DEFAULT_INTEGER;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_structured_content"
	},
	service = MVCActionCommand.class
)
public class AddStructuredContentMVCActionCommand extends BaseMVCActionCommand {

	// TODO Solo tenemos
	// titulo, structura, campos de la structura

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Updating article " +
					MapUtil.toString(actionRequest.getParameterMap()));
		}

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String articleId = ParamUtil.getString(actionRequest, "articleId");
		boolean autoArticleId = ParamUtil.getBoolean(
			actionRequest, "autoArticleId");
//		double version = ParamUtil.getDouble(actionRequest, "version");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "titleMapAsXML");

		String ddmStructureKey = "BASIC-WEB-CONTENT";

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_portal.getSiteGroupId(groupId),
			_portal.getClassNameId(JournalArticle.class), ddmStructureKey,
			true);

		ServiceContext serviceContext =
			ServiceContextFactory.getInstance(actionRequest);

		Fields fields = DDMUtil.getFields(
			ddmStructure.getStructureId(), serviceContext);

		String content = _journalConverter.getContent(ddmStructure, fields);

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLanguageId(content));

		if ((classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) &&
			!_hasDefaultLocale(titleMap, articleDefaultLocale)) {

			titleMap.put(
				articleDefaultLocale,
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest), "untitled-x",
					HtmlUtil.escape(
						ddmStructure.getName(themeDisplay.getLocale()))));
		}

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");
		Map<Locale, String> friendlyURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "friendlyURL");

		String ddmTemplateKey = ParamUtil.getString(
			actionRequest, "ddmTemplateKey");
		int displayPageType = ParamUtil.getInteger(
			actionRequest, "displayPageType");

		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		boolean neverExpire = true;

		if (!PropsValues.SCHEDULER_ENABLED) {
			neverExpire = true;
		}

		boolean neverReview = true;

		if (!PropsValues.SCHEDULER_ENABLED) {
			neverReview = true;
		}

		boolean indexable = ParamUtil.getBoolean(
			actionRequest, "indexable");

		String smallImageSource = ParamUtil.getString(
			actionRequest, "smallImageSource", "none");

		boolean smallImage = !Objects.equals(smallImageSource, "none");

		String smallImageURL = StringPool.BLANK;
		File smallFile = null;

		if (Objects.equals(smallImageSource, "url")) {
			smallImageURL = ParamUtil.getString(
				actionRequest, "smallImageURL");
		}

		String articleURL = ParamUtil.getString(actionRequest, "articleURL");

		_journalArticleService.addArticle(
			groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, titleMap, descriptionMap, friendlyURLMap,
			content, ddmStructureKey, ddmTemplateKey, layoutUuid,
			DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER,
			DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER,
			DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER,
			DEFAULT_INTEGER, neverExpire, DEFAULT_INTEGER,
			DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER,
			neverReview, indexable, smallImage, smallImageURL, null,
			null, articleURL, serviceContext);

		// Recent articles

		// TODO Can we skip this?
//		JournalUtil.addRecentArticle(actionRequest, article);

		// Journal content

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		long refererPlid = ParamUtil.getLong(actionRequest, "refererPlid");

		// TODO ??
//		if (Validator.isNotNull(portletResource) && (refererPlid > 0)) {
//			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
//				JournalArticle.class.getName(), article.getResourcePrimKey());
//
//			PortletPreferences portletPreferences =
//				PortletPreferencesFactoryUtil.getStrictPortletSetup(
//					_layoutLocalService.getLayout(refererPlid),
//					portletResource);
//
//			if (portletPreferences != null) {
//				portletPreferences.setValue(
//					"groupId", String.valueOf(article.getGroupId()));
//				portletPreferences.setValue(
//					"articleId", article.getArticleId());
//
//				if (assetEntry != null) {
//					portletPreferences.setValue(
//						"assetEntryId",
//						String.valueOf(assetEntry.getEntryId()));
//				}
//
//				portletPreferences.store();
//
//				updateContentSearch(
//					refererPlid, portletResource, article.getArticleId());
//			}
//
//			if (assetEntry != null) {
//				_updateAssetEntryUsage(
//					groupId, assetEntry, portletResource, refererPlid,
//					serviceContext);
//			}
//		}

		// Asset display page

		// TODO ????
//		_assetDisplayPageEntryFormProcessor.process(
//			JournalArticle.class.getName(), article.getResourcePrimKey(),
//			actionRequest);

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		// ??
		if (Validator.isNotNull(portletResource) &&
			(workflowAction != WorkflowConstants.ACTION_SAVE_DRAFT)) {

			MultiSessionMessages.add(
				actionRequest, portletResource + "requestProcessed");
		}

		boolean hideDefaultSuccessMessage = ParamUtil.getBoolean(
			actionRequest, "hideDefaultSuccessMessage");

		if (hideDefaultSuccessMessage) {
			hideDefaultSuccessMessage(actionRequest);
		}
	}

	private boolean _hasDefaultLocale(Map<Locale, String> map, Locale locale) {
		if (MapUtil.isEmpty(map)) {
			return false;
		}

		if (Validator.isNull(map.get(locale))) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddStructuredContentMVCActionCommand.class);

//	@Reference
//	private AssetDisplayPageEntryFormProcessor
//		_assetDisplayPageEntryFormProcessor;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Http _http;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;	
}
