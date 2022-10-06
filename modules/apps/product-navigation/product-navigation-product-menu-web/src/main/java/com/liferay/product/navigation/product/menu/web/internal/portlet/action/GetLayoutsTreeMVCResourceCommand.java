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

package com.liferay.product.navigation.product.menu.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
	import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.layoutsadmin.util.LayoutsTreeUtil;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU,
		"mvc.command.name=/product_navigation_product_menu/get_layouts_tree"
	},
	service = MVCResourceCommand.class
)
public class GetLayoutsTreeMVCResourceCommand extends BaseMVCResourceCommand {

//	private JSONObject _getSampleItemJSONObject(int pageIndex) {
//
//		return JSONUtil.put(
//			"children",
//			JSONFactoryUtil.createJSONArray()
//		).put(
//			"id", pageIndex
//		).put(
//			"name",
//			"Blogs"
//		).put(
//			"url",
//			"http://www.liferay.com"
//		).put(
//			"paginated",
//			true
//		);
//	}

//	private JSONArray _getSampleJSONArray(int pageIndex) {
//
//		JSONArray itemsJSONArray =
//			JSONFactoryUtil.createJSONArray();
//
//		itemsJSONArray.put(
//			_getSampleItemJSONObject(pageIndex));
//
//		return itemsJSONArray;
//	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		int pageIndex = ParamUtil.getInteger(resourceRequest, "pageIndex");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

//		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
//		String treeId = ParamUtil.getString(httpServletRequest, "treeId");

		String treeId = "productMenuPagesTree";

		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout"); // TODO Probar con private pages
//		long parentLayoutId = ParamUtil.getLong(
//			httpServletRequest, "parentLayoutId");
		long parentLayoutId = 0;

		boolean incomplete = ParamUtil.getBoolean(
			httpServletRequest, "incomplete", true);

//		long plid = ParamUtil.getLong(
//			resourceRequest, "plid", LayoutConstants.DEFAULT_PLID);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		jsonObject.put(
			"items",
			JSONFactoryUtil.createJSONArray(
				LayoutsTreeUtil.getLayoutsJSON(
					httpServletRequest, groupId, privateLayout, parentLayoutId,
					incomplete, treeId))
//			_getSampleJSONArray(pageIndex)
		).put(
			"hasMoreElements", (pageIndex <= 4)
		);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			jsonObject);

//		ServletResponseUtil.write(
//			httpServletResponse, jsonObject.toString()
//			httpServletResponse, jsonObject.toJSONString()
//		);
	}

	private JSONArray _getLayoutPathJSONArray(Layout layout, Locale locale)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> ancestorLayouts = layout.getAncestors();

		Collections.reverse(ancestorLayouts);

		for (Layout ancestorLayout : ancestorLayouts) {
			jsonArray.put(HtmlUtil.escape(ancestorLayout.getName(locale)));
		}

		return jsonArray;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}