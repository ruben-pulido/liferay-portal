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

package com.liferay.chess.internal.search.spi.model.index.contributor;

import com.liferay.chess.model.ChessGame;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.chess.model.ChessGame",
	service = ModelDocumentContributor.class
)
public class ChessGameModelDocumentContributor
	implements ModelDocumentContributor<ChessGame> {

	@Override
	public void contribute(Document document, ChessGame chessGame) {
		document.addKeyword("blackPlayerId", chessGame.getBlackPlayerId());
		document.addKeyword("chessGameId", chessGame.getChessGameId());
		document.addKeyword("whitePlayerId", chessGame.getWhitePlayerId());
		document.addKeyword("winnerPlayerId", chessGame.getWinnerPlayerId());
		document.addText("moves", chessGame.getMoves());
	}

}