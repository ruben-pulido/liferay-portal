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
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;

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

	public DefaultFragmentRendererContext getDefaultFragmentRendererContext() {
		FragmentEntry fragmentEntry = _getFragmentEntry();

//		String css = BeanParamUtil.getString(
//			fragmentEntry, _httpServletRequest, "css");
//		String html = BeanParamUtil.getString(
//			fragmentEntry, _httpServletRequest, "html");
//		String js = BeanParamUtil.getString(
//			fragmentEntry, _httpServletRequest, "js");
//		String configuration = BeanParamUtil.getString(
//			fragmentEntry, _httpServletRequest, "configuration");

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

		defaultFragmentRendererContext.setEditableValues(
			ParamUtil.get(
				_httpServletRequest, "editableValues", StringPool.BLANK)
		);
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