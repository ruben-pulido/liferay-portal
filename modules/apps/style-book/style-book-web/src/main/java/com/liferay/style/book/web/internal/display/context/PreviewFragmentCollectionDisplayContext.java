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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class PreviewFragmentCollectionDisplayContext {

	public PreviewFragmentCollectionDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getFragmentCollectionKey() {
		if (_fragmentCollectionKey != null) {
			return _fragmentCollectionKey;
		}

		_fragmentCollectionKey = ParamUtil.getString(
			_renderRequest, "fragmentCollectionKey");

		return _fragmentCollectionKey;
	}

	public void setFragmentCollectionKey(String fragmentCollectionKey) {
		_fragmentCollectionKey = fragmentCollectionKey;
	}

	private String _fragmentCollectionKey;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}