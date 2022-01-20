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

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class PreviewFragmentCollectionDisplayContext {

	public PreviewFragmentCollectionDisplayContext(
		HttpServletRequest httpServletRequest,
//		RenderRequest renderRequest,
		ResourceResponse resourceResponse) {

		_httpServletRequest = httpServletRequest;
//		_resourceRequest = resourceRequest;
//		_renderRequest = renderRequest;
		_resourceResponse = resourceResponse;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)httpServletRequest.getAttribute(
				StyleBookWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
	}

	public String getFragmentCollectionKey() {
		if (_fragmentCollectionKey != null) {
			return _fragmentCollectionKey;
		}

		_fragmentCollectionKey = ParamUtil.getString(
			_httpServletRequest, "fragmentCollectionKey");

		return _fragmentCollectionKey;
	}

	private String _getFragmentEntryRenderURL(String fragmentEntryKey)
		throws Exception {

		String renderFragmentEntryLinkUrl =
			(String)_httpServletRequest.getAttribute(
				"RENDER_FRAGMENT_ENTRY_LINK_URL"
			);

		return HttpUtil.addParameter(
			renderFragmentEntryLinkUrl,
//			"_"+ StyleBookPortletKeys.STYLE_BOOK +  "_fragmentEntryKey",
			"_com_liferay_style_book_web_internal_portlet_StyleBookPortlet_fragmentEntryKey",
			fragmentEntryKey);

//		return ResourceURLBuilder.createResourceURL(
//			_resourceResponse
////		).setCMD(
////			Constants.SAVE
//		).setParameter(
//			"groupId", _getGroupId()
//		).setParameter(
//			"fragmentEntryKey", fragmentEntryKey
//		).setResourceID(
//			"/style_book/render_fragment_entry_link"
//		).buildString();

//		return PortletURLBuilder.create(
//			PortletURLFactoryUtil.create(
//				_httpServletRequest, StyleBookPortletKeys.STYLE_BOOK,
//				PortletRequest.RENDER_PHASE)
//		).setMVCRenderCommandName(
//			"/style_book/render_fragment_entry"
//		).setParameter(
//			"groupId", _getGroupId()
//		).setParameter(
//			"fragmentEntryKey", fragmentEntryKey
//		).setParameter(
//			"p_l_mode", Constants.PREVIEW
//		).buildString();
	}

	public JSONArray getFragmentsArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				_getGroupId(), getFragmentCollectionKey());

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		if (fragmentCollection != null) {
			fragmentEntries =
				FragmentEntryLocalServiceUtil.getFragmentEntries(
					fragmentCollection.getFragmentCollectionId());
		}

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorTracker.getFragmentCollectionContributor(
				getFragmentCollectionKey());

		if (fragmentCollectionContributor != null) {
			fragmentEntries = fragmentCollectionContributor.getFragmentEntries();
		}

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			jsonArray.put(
				JSONUtil.put(
					"configuration",
					JSONFactoryUtil.createJSONObject(
						fragmentEntry.getConfiguration())
				).put(
					"name", fragmentEntry.getName()
				).put(
					"previewURL",
					_getFragmentEntryRenderURL(
						fragmentEntry.getFragmentEntryKey())
				));
		}

		return jsonArray;
	}

	public String getStyleBookPortletNamespace() {
		return StyleBookPortletKeys.STYLE_BOOK;
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		return _groupId;
	}

	private String _fragmentCollectionKey;
	private final FragmentCollectionContributorTracker _fragmentCollectionContributorTracker;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
//	private final RenderRequest _renderRequest;
//	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;

}