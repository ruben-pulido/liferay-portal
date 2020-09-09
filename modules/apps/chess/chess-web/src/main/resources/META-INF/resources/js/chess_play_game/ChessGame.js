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
import React, {useEffect, useState} from 'react';

import ChessAddMove from './ChessAddMove';
import ChessBoard from './ChessBoard';
import ChessGameInfo from './ChessGameInfo';
import ChessMoves from './ChessMoves';
import LoggedInUserContext from './LoggedInUserContext';
import {PLAYER_COLOR} from './constants';

const GAME_RESULT = {
	BLACK_WINS: 'Black wins',
	DRAW: 'Draw',
	WHITE_WINS: 'White wins',
};

const ChessGame = ({config}) => {
	const [chessMoves, setChessMoves] = useState(config.initialChessMoves);
	const [currentTurn, setCurrentTurn] = useState(PLAYER_COLOR.WHITE);
	const [gameResult, setGameResult] = useState(null);

	function addChessMove(chessMove) {
		setChessMoves(chessMoves.concat(chessMove));
	}

	function updateCurrentTurn() {
		if (currentTurn === PLAYER_COLOR.WHITE) {
			setCurrentTurn(PLAYER_COLOR.BLACK);
		}
		else {
			setCurrentTurn(PLAYER_COLOR.WHITE);
		}
	}

	useEffect(() => {
		document.title = `Current turn: ${currentTurn}`;
	}, [currentTurn]);

	return (
		<div>
			Chess Game Id: {config.chessGameId}
			<LoggedInUserContext.Provider value={config.loggedInUser}>
				<ChessBoard />
				<ChessGameInfo
					blackPlayer={config.blackPlayer}
					whitePlayer={config.whitePlayer}
				/>
				<div>Current turn: {currentTurn}</div>
				{gameResult && (
					<div>Game result: {GAME_RESULT[gameResult]}</div>
				)}
				<ChessMoves chessMoves={chessMoves} />
				<ChessAddMove
					actionUrl={config.urls.addMoveURL}
					addChessMove={addChessMove}
					chessGameId={config.chessGameId}
					currentTurn={currentTurn}
					portletNamespace={config.portletNamespace}
					setCurrentTurn={setCurrentTurn}
					setGameResult={setGameResult}
					updateCurrentTurn={updateCurrentTurn}
				/>
			</LoggedInUserContext.Provider>
		</div>
	);
};

export default function ({config}) {
	return <ChessGame config={config} />;
}

ChessGame.propTypes = {
	config: PropTypes.shape({
		blackPlayer: PropTypes.shape({
			emailAddress: PropTypes.string.isRequired,
		}),
		chessGameId: PropTypes.number.isRequired,
		initialChessMoves: PropTypes.arrayOf(PropTypes.string.isRequired)
			.isRequired,
		loggedInUser: PropTypes.shape({
			emailAddress: PropTypes.string.isRequired,
		}),
		portletNamespace: PropTypes.string.isRequired,
		urls: PropTypes.shape({
			addMoveURL: PropTypes.string.isRequired,
		}),
		whitePlayer: PropTypes.shape({
			emailAddress: PropTypes.string.isRequired,
		}),
	}),
};
