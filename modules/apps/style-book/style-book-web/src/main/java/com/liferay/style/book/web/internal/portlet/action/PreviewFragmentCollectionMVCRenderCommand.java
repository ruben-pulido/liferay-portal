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

import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/preview_fragment_collection_NOT_USED"
	},
	service = MVCRenderCommand.class
)
public class PreviewFragmentCollectionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)renderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			themeDisplay.getScopeGroupId(), false);

		if (layoutSet != null) {
			themeDisplay.setLayoutSet(layoutSet);
			themeDisplay.setLookAndFeel(
				layoutSet.getTheme(), layoutSet.getColorScheme());
		}

//		RequestDispatcher requestDispatcher =
//			_servletContext.getRequestDispatcher("/preview_fragment_collection.jsp");
//
//		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();
//
//		PipingServletResponse pipingServletResponse = new PipingServletResponse(
//			httpServletResponse, unsyncStringWriter);
//
//		requestDispatcher.include(httpServletRequest, pipingServletResponse);
//
//		Document document = Jsoup.parse(
//			ThemeUtil.include(
//				httpServletRequest.getServletContext(), httpServletRequest,
//				httpServletResponse, "portal_normal.ftl", layoutSet.getTheme(),
//				false));
//
//		Element bodyElement = document.body();
//
//		bodyElement.html(unsyncStringWriter.toString());
//
//		ServletResponseUtil.write(httpServletResponse, document.html());
//
//		return null;

		return "/preview_fragment_collection.jsp";
	}

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}