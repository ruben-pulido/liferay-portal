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

package com.liferay.chess.web.internal.portlet.action;

import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.chess.arbiter.ChessArbiter;
import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalService;
import com.liferay.chess.web.internal.constants.ChessPortletKeys;
import com.liferay.chess.web.internal.handler.ChessGameExceptionRequestHandler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChessPortletKeys.CHESS_PLAY_GAME,
		"mvc.command.name=/chess_play_game/add_move"
	},
	service = MVCActionCommand.class
)
public class ChessPlayGameAddMoveMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long chessGameId = ParamUtil.getLong(actionRequest, "chessGameId");

		String chessMove = ParamUtil.getString(actionRequest, "chessMove");

		try {
			ChessGame chessGame = _chessGameLocalService.fetchChessGame(
				chessGameId);

			if (chessGame == null) {
				throw new NoSuchEntryException();
			}

			ChessGame updatedChessGame = _chessGameLocalService.updateChessGame(
				chessGameId, chessMove);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"chessGameResult",
					_chessArbiter.getChessGameResult(chessGameId)
				).put(
					"position",
					JSONFactoryUtil.createJSONObject(
						updatedChessGame.getPosition())
				));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			_chessGameExceptionRequestHandler.handleException(
				actionRequest, actionResponse, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChessPlayGameAddMoveMVCActionCommand.class);

	@Reference
	private ChessArbiter _chessArbiter;

	@Reference
	private ChessGameExceptionRequestHandler _chessGameExceptionRequestHandler;

	@Reference
	private ChessGameLocalService _chessGameLocalService;

}