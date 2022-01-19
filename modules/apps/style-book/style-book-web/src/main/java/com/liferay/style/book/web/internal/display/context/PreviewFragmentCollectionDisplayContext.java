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

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rubén Pulido
 */
public class PreviewFragmentCollectionDisplayContext {

	public PreviewFragmentCollectionDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_renderRequest, "groupId");

		return _groupId;
	}

	public String getFragmentCollectionKey() {
		if (_fragmentCollectionKey != null) {
			return _fragmentCollectionKey;
		}

		_fragmentCollectionKey = ParamUtil.getString(
			_renderRequest, "fragmentCollectionKey");

		return _fragmentCollectionKey;
	}

	private String _getFragmentEntryRenderURL(String fragmentEntryKey)
		throws Exception {

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_httpServletRequest, StyleBookPortletKeys.STYLE_BOOK,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/style_book/render_fragment_entry"
		).setParameter(
			"groupId", _getGroupId()
		).setParameter(
			"fragmentEntryKey", fragmentEntryKey
		).setParameter(
			"p_l_mode", Constants.PREVIEW
		).buildString();
	}

	public JSONArray getFragmentsArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				_getGroupId(), getFragmentCollectionKey());

		if (fragmentCollection == null) {
			return jsonArray;
		}

		List<FragmentEntry> fragmentEntries =
			FragmentEntryLocalServiceUtil.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		for (FragmentEntry fragmentEntry : fragmentEntries) {

			jsonArray.put(
				JSONUtil.put(
					"name", fragmentEntry.getName()
				).put(
					"configuration",
					JSONFactoryUtil.createJSONObject(fragmentEntry.getConfiguration())
				).put(
					"previewURL", _getFragmentEntryRenderURL(fragmentEntry.getFragmentEntryKey())
				)
			);
		}

		return jsonArray;
	}

	public String getStyleBookPortletNamespace() {
		return StyleBookPortletKeys.STYLE_BOOK;
	}

	private String _fragmentCollectionKey;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;

}