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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.command.function=pieceSayCatchphrase", "osgi.command.scope=chess"
	},
	service = Object.class
)
public class ChessPieceSayCatchphraseCommand {

	public ChessPiece getChessPiece() {
		return _chessPiece;
	}

	public void pieceSayCatchphrase() {
		ChessPiece chessPiece = getChessPiece();

		System.out.println(
			chessPiece.getName() + " says: " + chessPiece.getCathphrase());
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ChessPiece _chessPiece;

}