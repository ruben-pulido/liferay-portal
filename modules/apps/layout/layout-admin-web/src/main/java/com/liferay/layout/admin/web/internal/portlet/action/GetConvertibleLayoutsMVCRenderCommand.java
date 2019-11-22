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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/get_convertible_layouts"
	},
	service = MVCRenderCommand.class
)
public class GetConvertibleLayoutsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long groupId = ParamUtil.getLong(renderRequest, "groupId");

			if (groupId > 0) {
				List<Layout> convertibleLayouts = new ArrayList();
				List<Layout> notConvertibleLayouts = new ArrayList();

				List<Layout> layouts = _layoutLocalService.getLayouts(
					groupId, false);

				for (Layout layout : layouts) {

					UnicodeProperties typeSettingsProperties =
						layout.getTypeSettingsProperties();

					String layoutTemplateId = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

					LayoutConverter layoutConverter =
						_layoutConverterRegistry.getLayoutConverter(
							layoutTemplateId);

					if (layoutConverter.isConvertible(layout)) {
						convertibleLayouts.add(layout);
					}
					else {
						notConvertibleLayouts.add(layout);
					}

				}

				renderRequest.setAttribute(
					LayoutAdminWebKeys.CONVERTIBLE_LAYOUTS, convertibleLayouts);
				renderRequest.setAttribute(
					LayoutAdminWebKeys.NON_CONVERTIBLE_LAYOUTS, notConvertibleLayouts);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/convert_all_layouts.jsp";
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;
}