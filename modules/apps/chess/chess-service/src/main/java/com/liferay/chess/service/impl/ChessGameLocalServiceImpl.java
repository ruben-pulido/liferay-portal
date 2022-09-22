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

package com.liferay.chess.service.impl;

import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.base.ChessGameLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the chess game local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.chess.service.ChessGameLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Rubén Pulido
 * @see ChessGameLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.chess.model.ChessGame",
	service = AopService.class
)
public class ChessGameLocalServiceImpl extends ChessGameLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>com.liferay.chess.service.ChessGameLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.chess.service.ChessGameLocalServiceUtil</code>.
	 */
	@Override
	public ChessGame addChessGame(
			long userId, long groupId, long whiteBlackPlayerId,
			long blackPlayerId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		if (serviceContext != null) {
			companyId = serviceContext.getCompanyId();
		}
		else {
			serviceContext = new ServiceContext();
		}

		ChessGame chessGame = createChessGame(
			counterLocalService.increment(ChessGame.class.getName()));

		chessGame.setGroupId(groupId);
		chessGame.setCompanyId(companyId);
		chessGame.setUserId(user.getUserId());
		chessGame.setUserName(user.getFullName());
		chessGame.setCreateDate(serviceContext.getCreateDate(new Date()));
		chessGame.setWhitePlayerId(whiteBlackPlayerId);
		chessGame.setBlackPlayerId(blackPlayerId);
		chessGame.setMoves("[]");

		return chessGamePersistence.update(chessGame);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>com.liferay.chess.service.ChessGameLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.chess.service.ChessGameLocalServiceUtil</code>.
	 */
	@Override
	public ChessGame updateChessGame(long chessGameId, String move)
		throws PortalException {

		ChessGame chessGame = fetchChessGame(chessGameId);

		if (chessGame == null) {
			return null;
		}

		JSONArray movesJSONArray = _jsonFactory.createJSONArray(
			chessGame.getMoves());

		movesJSONArray.put(move);

		chessGame.setMoves(movesJSONArray.toJSONString());

		return chessGamePersistence.update(chessGame);
	}

	@Reference
	private JSONFactory _jsonFactory;

}