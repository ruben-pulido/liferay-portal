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

package com.liferay.chess.web.internal.display.context;

import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class ChessAdminGamesDisplayContext {

	public ChessAdminGamesDisplayContext(
		HttpServletRequest httpServletRequest, PortletRequest portletRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_portletRequest = portletRequest;
		_renderResponse = renderResponse;
	}

	public Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"config",
			() -> HashMapBuilder.<String, Object>put(
				"chessGames",
				() -> JSONFactoryUtil.createJSONArray(_getChessGames())
			).build()
		).build();
	}

	private List<ChessGame> _getChessGames() {
		if (_chessGames != null) {
			return _chessGames;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_chessGames = ChessGameLocalServiceUtil.getChessGames(
			themeDisplay.getSiteGroupId());

		return _chessGames;
	}

	private List<ChessGame> _chessGames;
	private final HttpServletRequest _httpServletRequest;
	private final PortletRequest _portletRequest;
	private final RenderResponse _renderResponse;

}