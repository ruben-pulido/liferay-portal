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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class ChessPlayGameDisplayContext {

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

	private ChessGame _getChessGame() {
		if (_chessGame != null) {
			return _chessGame;
		}

		_chessGame = ChessGameLocalServiceUtil.fetchChessGame(1);

		return _chessGame;
	}

	private ChessGame _chessGame;

}