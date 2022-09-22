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

package com.liferay.chess.arbiter.internal;

import com.liferay.chess.arbiter.ChessArbiter;
import com.liferay.chess.arbiter.ChessGameResult;
import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = ChessArbiter.class)
public class ChessDefaultArbiterImpl implements ChessArbiter {

	@Override
	public ChessGameResult getChessGameResult(long chessGameId)
		throws Exception {

		ChessGame chessGame = _chessGameLocalService.fetchChessGame(
			chessGameId);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			chessGame.getMoves());

		if (jsonArray.length() < 4) {
			return null;
		}

		if (Objects.equals(jsonArray.get(0), "f2-f3") &&
			Objects.equals(jsonArray.get(1), "e7-e5") &&
			Objects.equals(jsonArray.get(2), "g2-g4") &&
			Objects.equals(jsonArray.get(3), "d8-h4")) {

			return ChessGameResult.BLACKS_WIN;
		}

		return null;
	}

	@Reference
	private ChessGameLocalService _chessGameLocalService;

}