package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

public class PreviewFragmentCollectionDisplayContext {

	public PreviewFragmentCollectionDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String  getFragmentCollectionKey() {
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