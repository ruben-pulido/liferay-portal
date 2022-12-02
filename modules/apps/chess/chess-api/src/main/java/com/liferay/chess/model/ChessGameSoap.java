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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.chess.service.http.ChessGameServiceSoap}.
 *
 * @author Rubén Pulido
 * @generated
 */
public class ChessGameSoap implements Serializable {

	public static ChessGameSoap toSoapModel(ChessGame model) {
		ChessGameSoap soapModel = new ChessGameSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setChessGameId(model.getChessGameId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setWhitePlayerId(model.getWhitePlayerId());
		soapModel.setBlackPlayerId(model.getBlackPlayerId());
		soapModel.setMoves(model.getMoves());
		soapModel.setWinnerPlayerId(model.getWinnerPlayerId());

		return soapModel;
	}

	public static ChessGameSoap[] toSoapModels(ChessGame[] models) {
		ChessGameSoap[] soapModels = new ChessGameSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ChessGameSoap[][] toSoapModels(ChessGame[][] models) {
		ChessGameSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ChessGameSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ChessGameSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ChessGameSoap[] toSoapModels(List<ChessGame> models) {
		List<ChessGameSoap> soapModels = new ArrayList<ChessGameSoap>(
			models.size());

		for (ChessGame model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ChessGameSoap[soapModels.size()]);
	}

	public ChessGameSoap() {
	}

	public long getPrimaryKey() {
		return _chessGameId;
	}

	public void setPrimaryKey(long pk) {
		setChessGameId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getChessGameId() {
		return _chessGameId;
	}

	public void setChessGameId(long chessGameId) {
		_chessGameId = chessGameId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getWhitePlayerId() {
		return _whitePlayerId;
	}

	public void setWhitePlayerId(long whitePlayerId) {
		_whitePlayerId = whitePlayerId;
	}

	public long getBlackPlayerId() {
		return _blackPlayerId;
	}

	public void setBlackPlayerId(long blackPlayerId) {
		_blackPlayerId = blackPlayerId;
	}

	public String getMoves() {
		return _moves;
	}

	public void setMoves(String moves) {
		_moves = moves;
	}

	public long getWinnerPlayerId() {
		return _winnerPlayerId;
	}

	public void setWinnerPlayerId(long winnerPlayerId) {
		_winnerPlayerId = winnerPlayerId;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _chessGameId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _whitePlayerId;
	private long _blackPlayerId;
	private String _moves;
	private long _winnerPlayerId;

}