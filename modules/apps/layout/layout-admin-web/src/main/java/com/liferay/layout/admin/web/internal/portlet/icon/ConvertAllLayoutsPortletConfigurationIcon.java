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

package com.liferay.layout.admin.web.internal.portlet.icon;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
	service = PortletConfigurationIcon.class
)
public class ConvertAllLayoutsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {
//	extends BaseJSPPortletConfigurationIcon {

//	@Override
//	public String getJspPath() {
//		return "/layout/convert_all_layouts.jsp";
//	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)),
			"convert-all-to-content-page");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getSiteGroupId()));
		portletURL.setParameter(
			"mvcRenderCommandName", "/layout/get_convertible_layouts");


//
//		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
//
//		Portlet portlet = _portletLocalService.getPortletById(
//			portletDisplay.getId());
//
//		PortletURL portletURL = PortletURLFactoryUtil.create(
//			portletRequest,
//			PortletProviderUtil.getPortletId(
//				DDMStructure.class.getName(), PortletProvider.Action.VIEW),
//			PortletRequest.RENDER_PHASE);

//		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("backURL", themeDisplay.getURLCurrent());

//		portletURL.setParameter(
//			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
//		portletURL.setParameter(
//			"refererPortletName", DDLPortletKeys.DYNAMIC_DATA_LISTS);
//		portletURL.setParameter(
//			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
//		portletURL.setParameter("showAncestorScopes", Boolean.TRUE.toString());


		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 101;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Reference
	private Portal _portal;

//	@Override
//	@Reference(
//		target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)",
//		unbind = "-"
//	)
//	public void setServletContext(ServletContext servletContext) {
//		super.setServletContext(servletContext);
//	}

//	@Reference(
//		target = "(resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME + ")"
//	)
//	private PortletResourcePermission _portletResourcePermission;

//	@Reference(
//		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
//	)
//	private ModelResourcePermission<Folder> _folderModelResourcePermission;

}