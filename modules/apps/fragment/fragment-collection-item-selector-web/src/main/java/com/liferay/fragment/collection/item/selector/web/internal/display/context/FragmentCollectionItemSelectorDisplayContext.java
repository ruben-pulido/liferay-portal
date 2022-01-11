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

package com.liferay.fragment.collection.item.selector.web.internal.display.context;

import com.liferay.fragment.collection.item.selector.FragmentCollectionItemSelectorReturnType;
import com.liferay.fragment.collection.item.selector.criterion.FragmentCollectionItemSelectorCriterion;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionItemSelectorDisplayContext {

	public FragmentCollectionItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		PortletURL portletURL, ItemSelector itemSelector,
		FragmentCollectionItemSelectorCriterion
			fragmentCollectionItemSelectorCriterion,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
		_portletURL = portletURL;
		_itemSelector = itemSelector;
		_fragmentCollectionItemSelectorCriterion =
			fragmentCollectionItemSelectorCriterion;
		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public int getFragmentCollectionContributorsCount() {
		if (_fragmentCollectionContributorTracker == null) {
			return 0;
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		return fragmentCollectionContributors.size();
	}

	public SearchContainer<FragmentCollectionContributor>
		getFragmentCollectionContributorSearchContainer() {

		SearchContainer<FragmentCollectionContributor> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-items-to-display");

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_getFragmentCollectionContributors();

		searchContainer.setResultsAndTotal(
			() -> ListUtil.subList(
				fragmentCollectionContributors, searchContainer.getStart(),
				searchContainer.getEnd()),
			fragmentCollectionContributors.size());

		return searchContainer;
	}

	public String getFragmentCollectionItemSelectorURL(long groupId) {
		FragmentCollectionItemSelectorCriterion
			fragmentCollectionItemSelectorCriterion =
				new FragmentCollectionItemSelectorCriterion();

		fragmentCollectionItemSelectorCriterion.setGroupId(groupId);

		fragmentCollectionItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new FragmentCollectionItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "selectFragmentCollection",
			fragmentCollectionItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public SearchContainer<FragmentCollection>
		getFragmentCollectionSearchContainer() {

		SearchContainer<FragmentCollection> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-items-to-display");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(true);

		searchContainer.setResultsAndTotal(
			() -> FragmentCollectionServiceUtil.getFragmentCollections(
				getGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), fragmentCollectionNameComparator),
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				getGroupId()));

		return searchContainer;
	}

	public Integer getGlobalFragmentCollectionsCount() {
		if (_themeDisplay.getScopeGroupId() ==
				_themeDisplay.getCompanyGroupId()) {

			return 0;
		}

		return FragmentCollectionServiceUtil.getFragmentCollectionsCount(
			_themeDisplay.getCompanyGroupId());
	}

	public int getGroupFragmentCollectionsCount() {
		return FragmentCollectionServiceUtil.getFragmentCollectionsCount(
			_themeDisplay.getScopeGroupId());
	}

	public long getGroupId() {
		if (_fragmentCollectionItemSelectorCriterion.getGroupId() == -1) {
			return CompanyConstants.SYSTEM;
		}

		return _fragmentCollectionItemSelectorCriterion.getGroupId();
	}

	public String getGroupName() throws PortalException {
		long groupId = getGroupId();

		if (groupId == CompanyConstants.SYSTEM) {
			return LanguageUtil.get(_themeDisplay.getLocale(), "default");
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(_themeDisplay.getLocale());
	}

	public String getGroupName(long groupId) throws PortalException {
		if (groupId == CompanyConstants.SYSTEM) {
			return LanguageUtil.get(_themeDisplay.getLocale(), "default");
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(_themeDisplay.getLocale());
	}

	private List<FragmentCollectionContributor>
		_getFragmentCollectionContributors() {

		if (_fragmentCollectionContributorTracker == null) {
			return Collections.emptyList();
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		Collections.sort(
			fragmentCollectionContributors,
			new FragmentCollectionContributorNameComparator(
				_themeDisplay.getLocale()));

		return fragmentCollectionContributors;
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentCollectionItemSelectorCriterion
		_fragmentCollectionItemSelectorCriterion;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final PortletURL _portletURL;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}