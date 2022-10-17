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

package com.liferay.product.navigation.product.menu.web.internal.display.context;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.web.internal.constants.ProductNavigationProductMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class LayoutActionsDisplayContext {

	public LayoutActionsDisplayContext(
		HttpServletRequest httpServletRequest,
		Language language, LayoutService layoutService,
		RenderRequest renderRequest,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			renderRequest);

		_httpServletRequest = httpServletRequest;
		_language = language;
		_layoutService = layoutService;
		_renderRequest = renderRequest;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;

		_groupProvider = (GroupProvider)_liferayPortletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		_namespace = PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAddChildCollectionURLTemplate() throws Exception {
		PortletURL addChildCollectionURL = getAddCollectionLayoutURL();

		if (addChildCollectionURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addChildCollectionURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	public String getAddChildURLTemplate() throws Exception {
		PortletURL addLayoutURL = getAddLayoutURL();

		if (addLayoutURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addLayoutURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	public PortletURL getAddCollectionLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_collections.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public boolean isPrivateLayout() {
		return Objects.equals(
			ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT,
			_getPageTypeSelectedOption());
	}

	private boolean _isPageHierarchyOption(String pageTypeOption) {
		if (Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT) ||
			Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT)) {

			return true;
		}

		return false;
	}

	private boolean _isValidPageTypeSelectedOption(
		String pageTypeSelectedOption) {

		if (_isPageHierarchyOption(pageTypeSelectedOption)) {
			return true;
		}

		long siteNavigationMenuId = GetterUtil.getLong(pageTypeSelectedOption);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if ((siteNavigationMenu != null) &&
			(siteNavigationMenu.getGroupId() == getGroupId())) {

			return true;
		}

		return false;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = _groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		if (group != null) {
			_groupId = group.getGroupId();
		}
		else {
			_groupId = _themeDisplay.getSiteGroupId();
		}

		return _groupId;
	}


	private String _getPageTypeSelectedOption() {
		if (_pageTypeSelectedOption != null) {
			return _pageTypeSelectedOption;
		}

		String pageTypeSelectedOption =
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT;

		String pageTypeSelectedOptionSessionValue = SessionClicks.get(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest),
			getNamespace() +
				ProductNavigationProductMenuWebKeys.PAGE_TYPE_SELECTED_OPTION,
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT);

		if (_isValidPageTypeSelectedOption(
				pageTypeSelectedOptionSessionValue)) {

			pageTypeSelectedOption = pageTypeSelectedOptionSessionValue;
		}

		_pageTypeSelectedOption = pageTypeSelectedOption;

		return _pageTypeSelectedOption;
	}

	public String getNamespace() {
		return _namespace;
	}

	public PortletURL getAddLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_page_template_entry.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public PortletURL getConfigureLayoutSetURL() throws PortalException {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout_set"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getScopeGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).buildPortletURL();
	}

	public String getConfigureLayoutURLTemplate() throws Exception {
		return StringBundler.concat(
			getConfigureLayoutURL(), StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	public String getConfigureLayoutURL() throws PortalException {
		PortletURL configureLayoutURL = PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).buildPortletURL();

		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeAssetDisplay() || layout.isTypeControlPanel()) {
			String redirect = ParamUtil.getString(
				_liferayPortletRequest, "redirect",
				_themeDisplay.getURLCurrent());

			configureLayoutURL.setParameter("redirect", redirect);
			configureLayoutURL.setParameter("backURL", redirect);
		}
		else {
			configureLayoutURL.setParameter(
				"redirect", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
			configureLayoutURL.setParameter(
				"backURL", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
		}

		configureLayoutURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		configureLayoutURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return configureLayoutURL.toString();
	}

	public String getViewCollectionItemsURL()
		throws PortalException, WindowStateException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_liferayPortletRequest, AssetListEntry.class.getName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		Layout layout = _themeDisplay.getLayout();

		String redirect = PortalUtil.getLayoutRelativeURL(
			_themeDisplay.getLayout(), _themeDisplay);

		if (layout.isTypeAssetDisplay() || layout.isTypeControlPanel()) {
			redirect = ParamUtil.getString(
				_liferayPortletRequest, "redirect", redirect);
		}

		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("showActions", String.valueOf(Boolean.TRUE));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return StringBundler.concat(
			portletURL, StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(AssetListPortletKeys.ASSET_LIST),
			"collectionPK={collectionPK}&",
			PortalUtil.getPortletNamespace(AssetListPortletKeys.ASSET_LIST),
			"collectionType={collectionType}");
	}

	private String _getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String backURL = ParamUtil.getString(_renderRequest, "backURL");

		if (Validator.isNull(backURL)) {
			backURL = ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(
					PortalUtil.getHttpServletRequest(_liferayPortletRequest)),
				"backURL", _themeDisplay.getURLCurrent());
		}

		_backURL = backURL;

		return backURL;
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = PortalUtil.escapeRedirect(_getBackURL());
		}

		_redirect = redirect;

		return _redirect;
	}

	private JSONArray getActionsJSONArray() throws Exception {
		JSONArray actionsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONArray itemsJSONArray = JSONFactoryUtil.createJSONArray();

		itemsJSONArray.put(
			JSONUtil.put(
				"id", "add-child-page"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "add-child-page")
			).put(
				"href", getAddChildURLTemplate()
			).put(
				"type", "item"
			)
		).put(
			JSONUtil.put(
				"id", "add-child-collection-page"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "add-child-collection-page")
			).put(
				"href", getAddChildCollectionURLTemplate()
			).put(
				"type", "item"
			)
		).put(
			JSONUtil.put(
				"id", "configure"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "configure")
			).put(
				"href", getConfigureLayoutURLTemplate()
			).put(
				"type", "item"
			)
		).put(
			JSONUtil.put(
				"data",
				JSONUtil.put(
					"id", "view-collection-items"
				).put(
					"modalTitle",
					_language.get(_themeDisplay.getLocale(), "view-items")
				).put(
					"url", getViewCollectionItemsURL()
				)
			).put(
				"href", getViewCollectionItemsURL()
			).put(
				"id", "view-collection-items"
			).put(
				"label",
				_language.get(
					_themeDisplay.getLocale(), "view-collection-items")
			).put(
				"target", "_blank"
			).put(
				"type", "item"
			)
		);

		actionsJSONArray.put(
			JSONUtil.put(
				"type", "group"
			).put(
				"items", itemsJSONArray
			)
		);
		return actionsJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutActionsDisplayContext.class.getName());

	private String _backURL;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final LayoutService _layoutService;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final GroupProvider _groupProvider;
	private final String _namespace;
	private String _pageTypeSelectedOption;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private final ThemeDisplay _themeDisplay;

}