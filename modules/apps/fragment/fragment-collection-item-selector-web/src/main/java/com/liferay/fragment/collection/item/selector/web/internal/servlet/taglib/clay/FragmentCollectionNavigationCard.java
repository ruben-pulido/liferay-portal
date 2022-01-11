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

package com.liferay.fragment.collection.item.selector.web.internal.servlet.taglib.clay;

import com.liferay.fragment.collection.item.selector.web.internal.constants.FragmentCollectionItemSelectorWebKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationCard;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionNavigationCard implements NavigationCard {

	public FragmentCollectionNavigationCard(
		BaseModel<?> baseModel, HttpServletRequest httpServletRequest, LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;

		_fragmentCollection = (FragmentCollection)baseModel;
	}

	@Override
	public String getCssClass() {
		return "selector-button";
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentCollectionItemSelectorWebKeys.
			FRAGMENT_COLLECTION_ITEM_SELECTOR_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-name", _fragmentCollection.getName()
		).put(
			"data-preview-url",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, "com_liferay_style_book_web_internal_portlet_StyleBookPortlet",
				PortletRequest.RENDER_PHASE)
//			PortletURLBuilder.createRenderURL(
//				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/style_book/preview_fragment_collection"
			).setParameter(
				"fragmentCollectionKey",
				_fragmentCollection.getFragmentCollectionKey()
			).buildString()
		).build();
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_fragmentCollection.getName());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isSmall() {
		return true;
	}

	private final FragmentCollection _fragmentCollection;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}