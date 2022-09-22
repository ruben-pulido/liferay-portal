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

package com.liferay.chess.gogo;

import com.liferay.chess.piece.ChessPiece;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.command.function=piecesSayCatchphrase", "osgi.command.scope=chess"
	},
	service = Object.class
)
public class ChessPiecesSayCatchphraseCommand {

	public void piecesSayCatchphrase() {
		for (ChessPiece chessPiece : _chessPiecesMap.values()) {
			System.out.println(
				chessPiece.getName() + " says: " + chessPiece.getCathphrase());
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setChessPiece(
		ChessPiece chessPiece, Map<String, Object> properties) {

		String pieceName = (String)properties.get("chess.piece.name");

		if (Validator.isNull(pieceName)) {
			return;
		}

		chessPiece = _chessPiecesMap.put(pieceName, chessPiece);
	}

	protected void unsetChessPiece(
		ChessPiece chessPiece, Map<String, Object> properties) {

		String pieceName = (String)properties.get("chess.piece.name");

		_chessPiecesMap.remove(pieceName);
	}

	private final Map<String, ChessPiece> _chessPiecesMap =
		new ConcurrentHashMap<>();

}