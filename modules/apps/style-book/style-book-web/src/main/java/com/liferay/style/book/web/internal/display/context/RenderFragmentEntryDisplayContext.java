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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class RenderFragmentEntryDisplayContext {

	public RenderFragmentEntryDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				_httpServletRequest.getAttribute(
					// TODO
					"FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER");
//					FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);


	}

	public DefaultFragmentRendererContext getDefaultFragmentRendererContext()
		throws JSONException {
		FragmentEntry fragmentEntry = _getFragmentEntry();

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		long fragmentEntryId = 0;

		if (fragmentEntry != null) {
			fragmentEntryId = fragmentEntry.getFragmentEntryId();
		}

		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);

		fragmentEntryLink.setCss(fragmentEntry.getCss());
		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setJs(fragmentEntry.getJs());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		String configurationValues = ParamUtil.get(
			_httpServletRequest, "configurationValues", StringPool.BLANK);

		defaultFragmentRendererContext.setUseCachedContent(false);

		if (Validator.isNotNull(configurationValues)) {
			JSONObject configurationValuesJSONObject =
				JSONFactoryUtil.createJSONObject(configurationValues);

			JSONObject editableValuesJSONObject = JSONUtil.put(
				"com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor",
				configurationValuesJSONObject
			);

			defaultFragmentRendererContext.setEditableValues(
				editableValuesJSONObject.toString());
		}

		defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);

		return defaultFragmentRendererContext;
	}

	private FragmentEntry _getFragmentEntry() {
		long groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId");
		String fragmentEntryKey = ParamUtil.getString(
			_httpServletRequest, "fragmentEntryKey");

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry == null) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryKey);
		}

		return fragmentEntry;
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final HttpServletRequest _httpServletRequest;

}