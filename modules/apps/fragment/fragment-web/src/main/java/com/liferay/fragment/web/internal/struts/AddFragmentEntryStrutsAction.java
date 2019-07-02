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

package com.liferay.fragment.web.internal.struts;

import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "path=/portal/fragment/validate_fragment_entry",
	service = StrutsAction.class
)
public class AddFragmentEntryStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String html = ParamUtil.getString(httpServletRequest, "html");
		String configuration = ParamUtil.getString(
			httpServletRequest, "configuration");

		try {
			_fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				html, configuration);

			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
		catch (PortalException pe) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		return null;
	}

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

}