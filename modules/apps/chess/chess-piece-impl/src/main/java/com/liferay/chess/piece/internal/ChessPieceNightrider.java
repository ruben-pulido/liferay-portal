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

package com.liferay.chess.piece.internal;

import com.liferay.chess.piece.ChessPiece;
import com.liferay.chess.quote.ChessQuoteProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"chess.piece.name=" + ChessPieceNightrider.NAME,
		"service.ranking:Integer=200"
	},
	service = ChessPiece.class
)
public class ChessPieceNightrider implements ChessPiece {

	public static final String NAME = "nightrider";

	@Override
	public String getCathphrase() {
		return _chessQuoteProvider.getQuote();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Reference
	private ChessQuoteProvider _chessQuoteProvider;

}