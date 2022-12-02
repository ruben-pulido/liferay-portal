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

package com.liferay.chess.internal.info.item;

import com.liferay.chess.model.ChessGame;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;

/**
 * @author Rubén Pulido
 */
public interface ChessGameInfoItemFields {

	public static final InfoField<NumberInfoFieldType> blackPlayerId =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"blackPlayerId"
		).localizable(
			false
		).build();
	public static final InfoField<TextInfoFieldType> blackPlayerName =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"blackPlayerName"
		).localizable(
			false
		).build();
	public static final InfoField<NumberInfoFieldType> chessGameIdField =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"chessGameId"
		).localizable(
			false
		).build();
	public static final InfoField<TextInfoFieldType> movesInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"moves"
		).localizable(
			false
		).build();
	public static final InfoField<NumberInfoFieldType> whitePlayerId =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"whitePlayerId"
		).localizable(
			false
		).build();
	public static final InfoField<TextInfoFieldType> whitePlayerName =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"whitePlayerName"
		).localizable(
			false
		).build();
	public static final InfoField<NumberInfoFieldType> winnerPlayerId =
		InfoField.builder(
		).infoFieldType(
			NumberInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"winnerPlayerId"
		).localizable(
			false
		).build();
	public static final InfoField<TextInfoFieldType> winnerPlayerName =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			ChessGame.class.getSimpleName()
		).name(
			"winnerPlayerName"
		).localizable(
			false
		).build();

}