/*
 * *
 *  * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *  *
 *  * This library is free software; you can redistribute it and/or modify it under
 *  * the terms of the GNU Lesser General Public License as published by the Free
 *  * Software Foundation; either version 2.1 of the License, or (at your option)
 *  * any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful, but WITHOUT
 *  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  * details.
 *
 */

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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionNameComparator;
import com.liferay.layout.util.comparator.LayoutCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.liferay.portal.kernel.util.GroupThreadLocal.getGroupId;

/**
 * @author Rubén Pulido
 */
public class ConvertAllLayoutsAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ConvertAllLayoutsAdminManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			LayoutsAdminDisplayContext layoutsAdminDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			layoutsAdminDisplayContext.getLayoutsSearchContainer());

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			httpServletRequest);
		_httpServletRequest = httpServletRequest;
		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "convertAllPagesManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "CONVERT_ALL_LAYOUTS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"privateLayout",
			String.valueOf(_layoutsAdminDisplayContext.isPrivateLayout()));

		return portletURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "convertiblePages";
	}

	@Override
	public String getSearchFormName() {
		return "fm";
	}

	@Override
	public String getSortingOrder() {
		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return null;
		}

		if (_layoutsAdminDisplayContext.isSearch()) {
			return super.getSortingOrder();
		}

		return null;
	}

	@Override
	public Boolean isDisabled() {
		if (Objects.equals(
				_layoutsAdminDisplayContext.getDisplayStyle(),
				"miller-columns")) {

			return false;
		}

		return super.isDisabled();
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return null;
		}

		if (_layoutsAdminDisplayContext.isSearch()) {
			return new String[] {"create-date"};
		}

		return null;
	}

	public boolean isFirstColumn() {
		if (_firstColumn != null) {
			return _firstColumn;
		}

		_firstColumn = ParamUtil.getBoolean(
			_httpServletRequest, "firstColumn", false);

		return _firstColumn;
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype()) {
			_privateLayout = true;

			return _privateLayout;
		}

		Layout layout = _themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		return _privateLayout;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "path");

		if (isSearch()) {
			_orderByCol = "create-date";
		}

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_liferayPortletRequest, "tabs1", "pages");

		return _tabs1;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		return portletURL;
	}

	public SearchContainer getLayoutsSearchContainer() throws PortalException {
		if (_layoutsSearchContainer != null) {
			return _layoutsSearchContainer;
		}

		String emptyResultMessage = "there-are-no-public-pages";

		if (isPrivateLayout()) {
			emptyResultMessage = "there-are-no-private-pages";
		}

		SearchContainer layoutsSearchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, emptyResultMessage);

		layoutsSearchContainer.setOrderByCol(_getOrderByCol());

		String orderByType = _getOrderByType();

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Layout> orderByComparator = null;

		if (Objects.equals(_getOrderByCol(), "create-date")) {
			orderByComparator = new LayoutCreateDateComparator(orderByAsc);
		}

		layoutsSearchContainer.setOrderByComparator(orderByComparator);

		layoutsSearchContainer.setOrderByType(_getOrderByType());

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_liferayPortletResponse);

		layoutsSearchContainer.setRowChecker(emptyOnClickRowChecker);

		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), isPrivateLayout(), getKeywords(),
			new String[] {
				LayoutConstants.TYPE_CONTENT, LayoutConstants.TYPE_EMBEDDED,
				LayoutConstants.TYPE_LINK_TO_LAYOUT,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_URL
			});

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), isPrivateLayout(), getKeywords(),
			new String[] {
				LayoutConstants.TYPE_CONTENT, LayoutConstants.TYPE_EMBEDDED,
				LayoutConstants.TYPE_LINK_TO_LAYOUT,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_URL
			},
			layoutsSearchContainer.getStart(), layoutsSearchContainer.getEnd(),
			layoutsSearchContainer.getOrderByComparator());

		layoutsSearchContainer.setTotal(layoutsCount);
		layoutsSearchContainer.setResults(layouts);

		_layoutsSearchContainer = layoutsSearchContainer;

		return _layoutsSearchContainer;
	}

	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConvertAllLayoutsAdminManagementToolbarDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private Boolean _firstColumn;
	private String _keywords;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private SearchContainer _layoutsSearchContainer;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private String _orderByCol;
	private String _orderByType;
	private Boolean _privateLayout;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}