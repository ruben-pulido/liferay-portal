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

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionContributorVerticalCard implements VerticalCard {

	public FragmentCollectionContributorVerticalCard(
		FragmentCollectionContributor fragmentCollectionContributor) {

		_fragmentCollectionContributor = fragmentCollectionContributor;
	}

	@Override
	public String getInputValue() {
		return _fragmentCollectionContributor.getFragmentCollectionKey();
	}

	@Override
	public String getStickerCssClass() {
		return "fragment-collection-sticker";
	}

	@Override
	public String getStickerIcon() {
		return "edit-layout";
	}

	@Override
	public String getTitle() {
		return _fragmentCollectionContributor.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final FragmentCollectionContributor _fragmentCollectionContributor;

}