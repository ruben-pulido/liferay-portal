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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const PLAYER_COLOR = {
	BLACK: 'Black',
	WHITE: 'White',
};

export default function ChessAddMove({
	actionUrl,
	chessGameId,
	initialChessMoves,
	portletNamespace,
}) {
	const [chessMoves, setChessMoves] = useState(initialChessMoves);
	const [currentTurn, setCurrentTurn] = useState(PLAYER_COLOR.WHITE);

	const handleAddMoveClick = () => {
		const moveTextInput = document.getElementById('moveTextInput');

		const chessMove = moveTextInput.value;

		const data = Liferay.Util.ns(portletNamespace, {
			chessGameId,
			chessMove,
		});

		const formData = objectToFormData(data);

		fetch(actionUrl, {
			body: formData,
			method: 'POST',
		});

		setChessMoves(chessMoves.concat(chessMove));

		if (currentTurn === PLAYER_COLOR.WHITE) {
			setCurrentTurn(PLAYER_COLOR.BLACK);
		}
		else {
			setCurrentTurn(PLAYER_COLOR.WHITE);
		}
	};

	return (
		<div className="chess-game__chess-add-move">
			Chess Add Move
			<div>Current turn: {currentTurn}</div>
			<div>
				Moves:
				<ul>
					{chessMoves.map((chessMove, i) => (
						<li key={i}>{chessMove}</li>
					))}
				</ul>
			</div>
			<ClayForm.Group>
				<label htmlFor="moveTextInput">Move</label>
				<ClayInput id="moveTextInput" placeholder="e2e4" type="text" />
			</ClayForm.Group>
			<ClayButton displayType="primary" onClick={handleAddMoveClick}>
				Add Move
			</ClayButton>
		</div>
	);
}

ChessAddMove.propTypes = {
	actionUrl: PropTypes.string.isRequired,
	chessGameId: PropTypes.number.isRequired,
	initialChessMoves: PropTypes.arrayOf(PropTypes.string.isRequired)
		.isRequired,
	portletNamespace: PropTypes.string,
};
