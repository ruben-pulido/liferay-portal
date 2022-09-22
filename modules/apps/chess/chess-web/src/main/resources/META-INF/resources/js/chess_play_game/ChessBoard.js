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

import PropTypes from 'prop-types';
import React from 'react';

function PieceImg({piecePlacement, square}) {
	const piece = piecePlacement[square];

	if (!piece) {
		return null;
	}

	return (
		<img src={`/o/chess-web/images/chess_pieces/cburnett/${piece}.svg`} />
	);
}

export default function ChessBoard({piecePlacement}) {
	return (
		<div>
			Chess Board
			<div>
				<table>
					<tbody>
						<tr>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'a8'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'b8'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'c8'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'd8'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'e8'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'f8'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'g8'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'h8'})}
							</td>
						</tr>
						<tr>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'a7'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'b7'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'c7'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'd7'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'e7'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'f7'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'g7'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'h7'})}
							</td>
						</tr>
						<tr>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'a6'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'b6'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'c6'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'd6'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'e6'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'f6'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'g6'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'h6'})}
							</td>
						</tr>
						<tr>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'a5'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'b5'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'c5'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'd5'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'e5'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'f5'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'g5'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'h5'})}
							</td>
						</tr>
						<tr>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'a4'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'b4'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'c4'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'd4'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'e4'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'f4'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'g4'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'h4'})}
							</td>
						</tr>
						<tr>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'a3'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'b3'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'c3'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'd3'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'e3'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'f3'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'g3'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'h3'})}
							</td>
						</tr>
						<tr>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'a2'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'b2'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'c2'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'd2'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'e2'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'f2'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'g2'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'h2'})}
							</td>
						</tr>
						<tr>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'a1'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'b1'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'c1'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'd1'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'e1'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'f1'})}
							</td>
							<td className="square-black">
								{PieceImg({piecePlacement, square: 'g1'})}
							</td>
							<td className="square-white">
								{PieceImg({piecePlacement, square: 'h1'})}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	);
}

ChessBoard.propTypes = {
	piecePlacement: PropTypes.object.isRequired,
};
