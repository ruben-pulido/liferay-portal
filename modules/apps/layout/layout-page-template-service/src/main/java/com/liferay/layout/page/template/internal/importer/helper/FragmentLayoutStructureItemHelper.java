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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentInstanceDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.InlineLink;
import com.liferay.headless.delivery.dto.v1_0.InlineValue;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class FragmentLayoutStructureItemHelper
	implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		LayoutStructure layoutStructure, PageElement pageElement,
		String parentItemId, int position) {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			fragmentCollectionContributorTracker, layoutPageTemplateEntry,
			pageElement, position);

		if (fragmentEntryLink == null) {
			return null;
		}

		return layoutStructure.addFragmentLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), parentItemId, position);
	}

	private FragmentEntryLink _addFragmentEntryLink(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		PageElement pageElement, int position) {

		FragmentInstanceDefinition fragmentInstanceDefinition =
			(FragmentInstanceDefinition)pageElement.getDefinition();

		if (fragmentInstanceDefinition == null) {
			return null;
		}

		Fragment fragment = fragmentInstanceDefinition.getFragment();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentCollectionContributorTracker, fragment.getFragmentKey(),
			layoutPageTemplateEntry);

		long fragmentEntryId = 0;
		String html = StringPool.BLANK;
		String js = StringPool.BLANK;
		String css = StringPool.BLANK;
		String configuration = StringPool.BLANK;

		if (fragmentEntry != null) {
			fragmentEntryId = fragmentEntry.getFragmentEntryId();

			html = fragmentEntry.getHtml();
			js = fragmentEntry.getJs();
			css = fragmentEntry.getCss();
			configuration = fragmentEntry.getConfiguration();
		}

		JSONObject jsonObject = JSONUtil.put(
			_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			JSONFactoryUtil.createJSONObject()
		).put(
			_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			_createEditablesValuesJSONObject(
				fragmentInstanceDefinition.getFragmentFields())
		);

		try {
			return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				layoutPageTemplateEntry.getUserId(),
				layoutPageTemplateEntry.getGroupId(), 0, fragmentEntryId,
				PortalUtil.getClassNameId(Layout.class.getName()),
				layoutPageTemplateEntry.getPlid(), css, html, js, configuration,
				jsonObject.toString(), StringUtil.randomId(), position,
				fragment.getFragmentKey(),
				ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return null;
	}

	private JSONObject _createEditablesValuesJSONObject(
		FragmentField[] fragmentFields) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentFields == null) {
			return jsonObject;
		}

		for (FragmentField fragmentField : fragmentFields) {
			JSONObject fragmentFieldJSONObject =
				JSONFactoryUtil.createJSONObject();

			String fragmentFieldId = fragmentField.getId();

			if (Validator.isNull(fragmentFieldId)) {
				continue;
			}

			FragmentFieldText fragmentFieldText =
				(FragmentFieldText)fragmentField.getValue();

			if (fragmentFieldText == null) {
				continue;
			}

			JSONObject fragmentLinkJSONObject = _createFragmentLinkJSONObject(
				fragmentFieldText.getFragmentLink());

			if (fragmentLinkJSONObject != null) {
				fragmentFieldJSONObject.put("config", fragmentLinkJSONObject);
			}

			JSONObject localizationJSONObject = _createLocalizationJSONObject(
				fragmentFieldText);

			try {
				jsonObject.put(
					fragmentFieldId,
					JSONUtil.merge(
						fragmentFieldJSONObject, localizationJSONObject));
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn(jsonException, jsonException);
				}
			}
		}

		return jsonObject;
	}

	private JSONObject _createFragmentLinkJSONObject(
		FragmentLink fragmentLink) {

		JSONObject fragmentLinkJSONObject = JSONFactoryUtil.createJSONObject();

		if (fragmentLink == null) {
			return fragmentLinkJSONObject;
		}

		fragmentLinkJSONObject.put("target", fragmentLink.getTarget());

		InlineLink inlineLink = (InlineLink)fragmentLink.getValue();

		if (inlineLink != null) {
			fragmentLinkJSONObject.put("href", inlineLink.getHref());
		}

		return fragmentLinkJSONObject;
	}

	private JSONObject _createLocalizationJSONObject(
		FragmentFieldText fragmentFieldText) {

		JSONObject localizationJSONObject = JSONFactoryUtil.createJSONObject();

		InlineValue inlineValue = (InlineValue)fragmentFieldText.getText();

		Map<String, String> i18nMap = inlineValue.getValue_i18n();

		if (i18nMap != null) {
			for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
				localizationJSONObject.put(entry.getKey(), entry.getValue());
			}
		}

		return localizationJSONObject;
	}

	private FragmentEntry _getFragmentEntry(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		String fragmentKey, LayoutPageTemplateEntry layoutPageTemplateEntry) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				layoutPageTemplateEntry.getGroupId(), fragmentKey);

		if (fragmentEntry == null) {
			fragmentEntry =
				fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentKey);
		}

		return fragmentEntry;
	}

	private static final String _BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.background.image." +
			"BackgroundImageFragmentEntryProcessor";

	private static final String _EDITABLE_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLayoutStructureItemHelper.class);

}