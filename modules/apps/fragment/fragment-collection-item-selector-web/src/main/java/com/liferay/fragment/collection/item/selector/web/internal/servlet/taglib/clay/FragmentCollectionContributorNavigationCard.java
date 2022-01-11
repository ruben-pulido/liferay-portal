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
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationCard;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionContributorNavigationCard
	implements NavigationCard {

	public FragmentCollectionContributorNavigationCard(
		FragmentCollectionContributor fragmentCollectionContributor,
		LiferayPortletResponse liferayPortletResponse) {

		_fragmentCollectionContributor = fragmentCollectionContributor;
		_liferayPortletResponse = liferayPortletResponse;
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
			"data-name", _fragmentCollectionContributor.getName()
		).put(
			"data-preview-url",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/style_book/preview_fragment_collection"
			).setParameter(
				"fragmentCollectionKey",
				_fragmentCollectionContributor.getFragmentCollectionKey()
			).buildString()
		).build();
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_fragmentCollectionContributor.getName());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isSmall() {
		return true;
	}

	private final FragmentCollectionContributor _fragmentCollectionContributor;
	private final LiferayPortletResponse _liferayPortletResponse;

}