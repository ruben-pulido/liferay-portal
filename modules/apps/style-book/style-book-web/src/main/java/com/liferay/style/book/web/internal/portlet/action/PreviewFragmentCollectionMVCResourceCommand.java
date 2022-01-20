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

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/preview_fragment_collection"
	},
	service = MVCResourceCommand.class
)
public class PreviewFragmentCollectionMVCResourceCommand
	extends BaseMVCResourceCommand {

	private String getRenderFragmentEntryLinkURL(
		ResourceResponse resourceResponse, long groupId) {

		return ResourceURLBuilder.createResourceURL(
			resourceResponse
		).setParameter(
			"groupId", groupId
		).setResourceID(
			"/style_book/render_fragment_entry_link"
		).buildString();
	}


	@Override
	protected void doServeResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		httpServletRequest.setAttribute(
			"RENDER_FRAGMENT_ENTRY_LINK_URL",
			getRenderFragmentEntryLinkURL(resourceResponse, groupId));

		httpServletRequest.setAttribute(
			StyleBookWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
			_fragmentCollectionContributorTracker);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

//		_portletResourcePermission.check(
//			themeDisplay.getPermissionChecker(), groupId,
//			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

//		httpServletRequest.setAttribute(
//			FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER,
//			_fragmentRendererController);
//		httpServletRequest.setAttribute(
//			FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
//			_fragmentCollectionContributorTracker);

		LayoutSet originalLayoutSet = themeDisplay.getLayoutSet();

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			themeDisplay.getScopeGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/preview_fragment_collection.jsp");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		requestDispatcher.include(httpServletRequest, pipingServletResponse);

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		Document document = Jsoup.parse(
			ThemeUtil.include(
				servletContext,
//				httpServletRequest.getServletContext(),
				httpServletRequest,
				httpServletResponse, "portal_normal.ftl", layoutSet.getTheme(),
				false));

		Element bodyElement = document.body();

		bodyElement.html(unsyncStringWriter.toString());

		themeDisplay.setLayoutSet(originalLayoutSet);
		themeDisplay.setLookAndFeel(
			originalLayoutSet.getTheme(), originalLayoutSet.getColorScheme());


		ServletResponseUtil.write(httpServletResponse, document.html());
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.style.book.web)")
	private ServletContext _servletContext;

	@Reference
	private Portal _portal;

}