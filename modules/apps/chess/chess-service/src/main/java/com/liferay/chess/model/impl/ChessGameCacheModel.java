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

package com.liferay.chess.model.impl;

import com.liferay.chess.model.ChessGame;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChessGame in entity cache.
 *
 * @author Rubén Pulido
 * @generated
 */
public class ChessGameCacheModel
	implements CacheModel<ChessGame>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChessGameCacheModel)) {
			return false;
		}

		ChessGameCacheModel chessGameCacheModel = (ChessGameCacheModel)object;

		if ((chessGameId == chessGameCacheModel.chessGameId) &&
			(mvccVersion == chessGameCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, chessGameId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", chessGameId=");
		sb.append(chessGameId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", whitePlayerId=");
		sb.append(whitePlayerId);
		sb.append(", blackPlayerId=");
		sb.append(blackPlayerId);
		sb.append(", moves=");
		sb.append(moves);
		sb.append(", winnerPlayerId=");
		sb.append(winnerPlayerId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChessGame toEntityModel() {
		ChessGameImpl chessGameImpl = new ChessGameImpl();

		chessGameImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			chessGameImpl.setUuid("");
		}
		else {
			chessGameImpl.setUuid(uuid);
		}

		chessGameImpl.setChessGameId(chessGameId);
		chessGameImpl.setGroupId(groupId);
		chessGameImpl.setCompanyId(companyId);
		chessGameImpl.setUserId(userId);

		if (userName == null) {
			chessGameImpl.setUserName("");
		}
		else {
			chessGameImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			chessGameImpl.setCreateDate(null);
		}
		else {
			chessGameImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			chessGameImpl.setModifiedDate(null);
		}
		else {
			chessGameImpl.setModifiedDate(new Date(modifiedDate));
		}

		chessGameImpl.setWhitePlayerId(whitePlayerId);
		chessGameImpl.setBlackPlayerId(blackPlayerId);

		if (moves == null) {
			chessGameImpl.setMoves("");
		}
		else {
			chessGameImpl.setMoves(moves);
		}

		chessGameImpl.setWinnerPlayerId(winnerPlayerId);

		chessGameImpl.resetOriginalValues();

		return chessGameImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		chessGameId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		whitePlayerId = objectInput.readLong();

		blackPlayerId = objectInput.readLong();
		moves = objectInput.readUTF();

		winnerPlayerId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(chessGameId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(whitePlayerId);

		objectOutput.writeLong(blackPlayerId);

		if (moves == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(moves);
		}

		objectOutput.writeLong(winnerPlayerId);
	}

	public long mvccVersion;
	public String uuid;
	public long chessGameId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long whitePlayerId;
	public long blackPlayerId;
	public String moves;
	public long winnerPlayerId;

}