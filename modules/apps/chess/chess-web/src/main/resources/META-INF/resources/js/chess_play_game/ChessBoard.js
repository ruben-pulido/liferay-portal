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
import React, {useState} from 'react';

function PieceImg({piecePlacement, square}) {
	const piece = piecePlacement[square];

	if (!piece) {
		return null;
	}

	return (
		<img src={`/o/chess-web/images/chess_pieces/cburnett/${piece}.svg`} />
	);
}

export default function ChessBoard({handleAddChessMove, piecePlacement}) {
	const [previousSourceSquare, setPreviousSourceSquare] = useState(null);
	const [previousTargetSquare, setPreviousTargetSquare] = useState(null);
	const [sourceSquare, setSourceSquare] = useState(null);

	const getClassName = (squareId) => {
		const color = getSquareColor(squareId);

		if (sourceSquare === squareId) {
			return `square-${color}-selected`;
		}

		if (
			previousSourceSquare === squareId ||
			previousTargetSquare === squareId
		) {
			return `square-${color}-previously-selected`;
		}

		return `square-${color}`;
	};

	const getSquareColor = (squareId) => {
		var letter = squareId[0];
		var number = squareId[1];

		if (
			(letter === 'a' ||
				letter === 'c' ||
				letter === 'e' ||
				letter === 'g') &&
			number % 2 === 0
		) {
			return 'white';
		}

		if (
			(letter === 'b' ||
				letter === 'd' ||
				letter === 'f' ||
				letter === 'h') &&
			number % 2 === 1
		) {
			return 'white';
		}

		return 'black';
	};

	const updateSquares = (event) => {
		if (sourceSquare) {
			if (sourceSquare === event.currentTarget.id) {
				setSourceSquare(null);
			}
			else {
				handleAddChessMove(sourceSquare + '-' + event.currentTarget.id);
				setPreviousSourceSquare(sourceSquare);
				setPreviousTargetSquare(event.currentTarget.id);
				setSourceSquare(null);
			}
		}
		else {
			setSourceSquare(event.currentTarget.id);
		}
	};

	return (
		<div>
			Chess Board
			<div>
				<table>
					<tbody>
						<tr>
							<td
								className={getClassName('a8')}
								id="a8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a8'})}
							</td>
							<td
								className={getClassName('b8')}
								id="b8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b8'})}
							</td>
							<td
								className={getClassName('c8')}
								id="c8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c8'})}
							</td>
							<td
								className={getClassName('d8')}
								id="d8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd8'})}
							</td>
							<td
								className={getClassName('e8')}
								id="e8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e8'})}
							</td>
							<td
								className={getClassName('f8')}
								id="f8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f8'})}
							</td>
							<td
								className={getClassName('g8')}
								id="g8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g8'})}
							</td>
							<td
								className={getClassName('h8')}
								id="h8"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h8'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a7')}
								id="a7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a7'})}
							</td>
							<td
								className={getClassName('b7')}
								id="b7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b7'})}
							</td>
							<td
								className={getClassName('c7')}
								id="c7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c7'})}
							</td>
							<td
								className={getClassName('d7')}
								id="d7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd7'})}
							</td>
							<td
								className={getClassName('e7')}
								id="e7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e7'})}
							</td>
							<td
								className={getClassName('f7')}
								id="f7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f7'})}
							</td>
							<td
								className={getClassName('g7')}
								id="g7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g7'})}
							</td>
							<td
								className={getClassName('h7')}
								id="h7"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h7'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a6')}
								id="a6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a6'})}
							</td>
							<td
								className={getClassName('b6')}
								id="b6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b6'})}
							</td>
							<td
								className={getClassName('c6')}
								id="c6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c6'})}
							</td>
							<td
								className={getClassName('d6')}
								id="d6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd6'})}
							</td>
							<td
								className={getClassName('e6')}
								id="e6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e6'})}
							</td>
							<td
								className={getClassName('f6')}
								id="f6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f6'})}
							</td>
							<td
								className={getClassName('g6')}
								id="g6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g6'})}
							</td>
							<td
								className={getClassName('h6')}
								id="h6"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h6'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a5')}
								id="a5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a5'})}
							</td>
							<td
								className={getClassName('b5')}
								id="b5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b5'})}
							</td>
							<td
								className={getClassName('c5')}
								id="c5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c5'})}
							</td>
							<td
								className={getClassName('d5')}
								id="d5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd5'})}
							</td>
							<td
								className={getClassName('e5')}
								id="e5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e5'})}
							</td>
							<td
								className={getClassName('f5')}
								id="f5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f5'})}
							</td>
							<td
								className={getClassName('g5')}
								id="g5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g5'})}
							</td>
							<td
								className={getClassName('h5')}
								id="h5"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h5'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a4')}
								id="a4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a4'})}
							</td>
							<td
								className={getClassName('b4')}
								id="b4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b4'})}
							</td>
							<td
								className={getClassName('c4')}
								id="c4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c4'})}
							</td>
							<td
								className={getClassName('d4')}
								id="d4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd4'})}
							</td>
							<td
								className={getClassName('e4')}
								id="e4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e4'})}
							</td>
							<td
								className={getClassName('f4')}
								id="f4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f4'})}
							</td>
							<td
								className={getClassName('g4')}
								id="g4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g4'})}
							</td>
							<td
								className={getClassName('h4')}
								id="h4"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h4'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a3')}
								id="a3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a3'})}
							</td>
							<td
								className={getClassName('b3')}
								id="b3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b3'})}
							</td>
							<td
								className={getClassName('c3')}
								id="c3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c3'})}
							</td>
							<td
								className={getClassName('d3')}
								id="d3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd3'})}
							</td>
							<td
								className={getClassName('e3')}
								id="e3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e3'})}
							</td>
							<td
								className={getClassName('f3')}
								id="f3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f3'})}
							</td>
							<td
								className={getClassName('g3')}
								id="g3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g3'})}
							</td>
							<td
								className={getClassName('h3')}
								id="h3"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h3'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a2')}
								id="a2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a2'})}
							</td>
							<td
								className={getClassName('b2')}
								id="b2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b2'})}
							</td>
							<td
								className={getClassName('c2')}
								id="c2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c2'})}
							</td>
							<td
								className={getClassName('d2')}
								id="d2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd2'})}
							</td>
							<td
								className={getClassName('e2')}
								id="e2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e2'})}
							</td>
							<td
								className={getClassName('f2')}
								id="f2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f2'})}
							</td>
							<td
								className={getClassName('g2')}
								id="g2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g2'})}
							</td>
							<td
								className={getClassName('h2')}
								id="h2"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'h2'})}
							</td>
						</tr>
						<tr>
							<td
								className={getClassName('a1')}
								id="a1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'a1'})}
							</td>
							<td
								className={getClassName('b1')}
								id="b1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'b1'})}
							</td>
							<td
								className={getClassName('c1')}
								id="c1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'c1'})}
							</td>
							<td
								className={getClassName('d1')}
								id="d1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'd1'})}
							</td>
							<td
								className={getClassName('e1')}
								id="e1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'e1'})}
							</td>
							<td
								className={getClassName('f1')}
								id="f1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'f1'})}
							</td>
							<td
								className={getClassName('g1')}
								id="g1"
								onClick={updateSquares}
							>
								{PieceImg({piecePlacement, square: 'g1'})}
							</td>
							<td
								className={getClassName('h1')}
								id="h1"
								onClick={updateSquares}
							>
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
	handleAddChessMove: PropTypes.func.isRequired,
	piecePlacement: PropTypes.object.isRequired,
};
