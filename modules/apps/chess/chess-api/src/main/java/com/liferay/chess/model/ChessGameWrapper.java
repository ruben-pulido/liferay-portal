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

package com.liferay.chess.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ChessGame}.
 * </p>
 *
 * @author Rubén Pulido
 * @see ChessGame
 * @generated
 */
public class ChessGameWrapper
	extends BaseModelWrapper<ChessGame>
	implements ChessGame, ModelWrapper<ChessGame> {

	public ChessGameWrapper(ChessGame chessGame) {
		super(chessGame);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("chessGameId", getChessGameId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("whitePlayerId", getWhitePlayerId());
		attributes.put("blackPlayerId", getBlackPlayerId());
		attributes.put("moves", getMoves());
		attributes.put("winnerPlayerId", getWinnerPlayerId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long chessGameId = (Long)attributes.get("chessGameId");

		if (chessGameId != null) {
			setChessGameId(chessGameId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long whitePlayerId = (Long)attributes.get("whitePlayerId");

		if (whitePlayerId != null) {
			setWhitePlayerId(whitePlayerId);
		}

		Long blackPlayerId = (Long)attributes.get("blackPlayerId");

		if (blackPlayerId != null) {
			setBlackPlayerId(blackPlayerId);
		}

		String moves = (String)attributes.get("moves");

		if (moves != null) {
			setMoves(moves);
		}

		Long winnerPlayerId = (Long)attributes.get("winnerPlayerId");

		if (winnerPlayerId != null) {
			setWinnerPlayerId(winnerPlayerId);
		}
	}

	/**
	 * Returns the black player ID of this chess game.
	 *
	 * @return the black player ID of this chess game
	 */
	@Override
	public long getBlackPlayerId() {
		return model.getBlackPlayerId();
	}

	/**
	 * Returns the chess game ID of this chess game.
	 *
	 * @return the chess game ID of this chess game
	 */
	@Override
	public long getChessGameId() {
		return model.getChessGameId();
	}

	/**
	 * Returns the company ID of this chess game.
	 *
	 * @return the company ID of this chess game
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this chess game.
	 *
	 * @return the create date of this chess game
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this chess game.
	 *
	 * @return the group ID of this chess game
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this chess game.
	 *
	 * @return the modified date of this chess game
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the moves of this chess game.
	 *
	 * @return the moves of this chess game
	 */
	@Override
	public String getMoves() {
		return model.getMoves();
	}

	/**
	 * Returns the mvcc version of this chess game.
	 *
	 * @return the mvcc version of this chess game
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this chess game.
	 *
	 * @return the primary key of this chess game
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this chess game.
	 *
	 * @return the user ID of this chess game
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this chess game.
	 *
	 * @return the user name of this chess game
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this chess game.
	 *
	 * @return the user uuid of this chess game
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this chess game.
	 *
	 * @return the uuid of this chess game
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the white player ID of this chess game.
	 *
	 * @return the white player ID of this chess game
	 */
	@Override
	public long getWhitePlayerId() {
		return model.getWhitePlayerId();
	}

	/**
	 * Returns the winner player ID of this chess game.
	 *
	 * @return the winner player ID of this chess game
	 */
	@Override
	public long getWinnerPlayerId() {
		return model.getWinnerPlayerId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the black player ID of this chess game.
	 *
	 * @param blackPlayerId the black player ID of this chess game
	 */
	@Override
	public void setBlackPlayerId(long blackPlayerId) {
		model.setBlackPlayerId(blackPlayerId);
	}

	/**
	 * Sets the chess game ID of this chess game.
	 *
	 * @param chessGameId the chess game ID of this chess game
	 */
	@Override
	public void setChessGameId(long chessGameId) {
		model.setChessGameId(chessGameId);
	}

	/**
	 * Sets the company ID of this chess game.
	 *
	 * @param companyId the company ID of this chess game
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this chess game.
	 *
	 * @param createDate the create date of this chess game
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this chess game.
	 *
	 * @param groupId the group ID of this chess game
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this chess game.
	 *
	 * @param modifiedDate the modified date of this chess game
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the moves of this chess game.
	 *
	 * @param moves the moves of this chess game
	 */
	@Override
	public void setMoves(String moves) {
		model.setMoves(moves);
	}

	/**
	 * Sets the mvcc version of this chess game.
	 *
	 * @param mvccVersion the mvcc version of this chess game
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this chess game.
	 *
	 * @param primaryKey the primary key of this chess game
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this chess game.
	 *
	 * @param userId the user ID of this chess game
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this chess game.
	 *
	 * @param userName the user name of this chess game
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this chess game.
	 *
	 * @param userUuid the user uuid of this chess game
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this chess game.
	 *
	 * @param uuid the uuid of this chess game
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the white player ID of this chess game.
	 *
	 * @param whitePlayerId the white player ID of this chess game
	 */
	@Override
	public void setWhitePlayerId(long whitePlayerId) {
		model.setWhitePlayerId(whitePlayerId);
	}

	/**
	 * Sets the winner player ID of this chess game.
	 *
	 * @param winnerPlayerId the winner player ID of this chess game
	 */
	@Override
	public void setWinnerPlayerId(long winnerPlayerId) {
		model.setWinnerPlayerId(winnerPlayerId);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ChessGameWrapper wrap(ChessGame chessGame) {
		return new ChessGameWrapper(chessGame);
	}

}