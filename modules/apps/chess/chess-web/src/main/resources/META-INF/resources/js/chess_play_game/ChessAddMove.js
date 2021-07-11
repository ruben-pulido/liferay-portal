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
import React, {useContext, useEffect, useRef, useState} from 'react';

import ChessMoves from './ChessMoves';
import LoggedInUserContext from './LoggedInUserContext';

const GAME_RESULT = {
	BLACK_WINS: 'Black wins',
	DRAW: 'Draw',
	WHITE_WINS: 'White wins',
};

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
	const [gameResult, setGameResult] = useState(null);

	const loggedInUser = useContext(LoggedInUserContext);

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
		})
			.then((response) => response.json())
			.then((json) => {
				setGameResult(json?.chessGameResult ?? null);
			});

		setChessMoves(chessMoves.concat(chessMove));

		if (currentTurn === PLAYER_COLOR.WHITE) {
			setCurrentTurn(PLAYER_COLOR.BLACK);
		}
		else {
			setCurrentTurn(PLAYER_COLOR.WHITE);
		}

		moveTextInputRef.current.focus();
		moveTextInputRef.current.value = '';
	};

	useEffect(() => {
		document.title = `Current turn: ${currentTurn}`;
	}, [currentTurn]);

	const moveTextInputRef = useRef(null);

	return (
		<div className="chess-game__chess-add-move">
			Chess Add Move
			<div>Logged in user: {loggedInUser.emailAddress}</div>
			<div>Current turn: {currentTurn}</div>
			{gameResult && <div>Game result: {GAME_RESULT[gameResult]}</div>}
			<ChessMoves chessMoves={chessMoves} />
			<ClayForm.Group>
				<label htmlFor="moveTextInput">Move</label>
				<ClayInput
					id="moveTextInput"
					placeholder="e2e4"
					ref={moveTextInputRef}
					type="text"
				/>
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
