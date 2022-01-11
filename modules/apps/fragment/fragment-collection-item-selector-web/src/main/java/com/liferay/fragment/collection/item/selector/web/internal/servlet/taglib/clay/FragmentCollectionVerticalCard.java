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

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public FragmentCollectionVerticalCard(
		FragmentCollection fragmentCollection, RowChecker rowChecker) {

		super(fragmentCollection, rowChecker);

		_fragmentCollection = fragmentCollection;
	}

	@Override
	public String getInputValue() {
		return _fragmentCollection.getFragmentCollectionKey();
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
		return _fragmentCollection.getName();
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	private final FragmentCollection _fragmentCollection;

}