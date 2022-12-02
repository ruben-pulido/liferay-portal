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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Rubén Pulido
 */
public class ChessPlayGameDisplayContext {

	public ChessPlayGameDisplayContext(
		PortletRequest portletRequest, RenderResponse renderResponse) {

		_portletRequest = portletRequest;
		_renderResponse = renderResponse;
	}

	public Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"config",
			() -> {
				ChessGame chessGame = _getChessGame();

				return HashMapBuilder.<String, Object>put(
					"blackPlayer",
					() -> HashMapBuilder.<String, Object>put(
						"emailAddress",
						() -> {
							User blackPlayer = UserLocalServiceUtil.fetchUser(
								chessGame.getBlackPlayerId());

							return blackPlayer.getEmailAddress();
						}
					).build()
				).put(
					"chessGameId", chessGame.getChessGameId()
				).put(
					"initialChessMoves",
					() -> JSONFactoryUtil.createJSONArray(chessGame.getMoves())
				).put(
					"portletNamespace", _renderResponse.getNamespace()
				).put(
					"urls",
					() -> HashMapBuilder.<String, Object>put(
						"addMoveURL", () -> getAddMoveActionURL()
					).build()
				).put(
					"loggedInUser",
					() -> HashMapBuilder.<String, Object>put(
						"emailAddress",
						() -> {
							ThemeDisplay themeDisplay =
								(ThemeDisplay)_portletRequest.getAttribute(
									WebKeys.THEME_DISPLAY);

							User user = themeDisplay.getUser();

							return user.getDisplayEmailAddress();
						}
					).build()
				).put(
					"whitePlayer",
					() -> HashMapBuilder.<String, Object>put(
						"emailAddress",
						() -> {
							User whitePlayer = UserLocalServiceUtil.fetchUser(
								chessGame.getWhitePlayerId());

							return whitePlayer.getEmailAddress();
						}
					).build()
				).build();
			}
		).build();
	}

	protected String getAddMoveActionURL() {
		PortletURL actionURL = PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/chess_play_game/add_move"
		).build();

		return actionURL.toString();
	}

	private ChessGame _getChessGame() {
		if (_chessGame != null) {
			return _chessGame;
		}

		_chessGame = ChessGameLocalServiceUtil.fetchChessGame(1);

		return _chessGame;
	}

	private ChessGame _chessGame;
	private final PortletRequest _portletRequest;
	private final RenderResponse _renderResponse;

}