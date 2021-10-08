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

import {fetch, objectToFormData} from 'frontend-js-web';
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

const showNotification = (message, error) => {
	const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

	const openToastParams = {
		message,
	};

	if (error) {
		openToastParams.title = Liferay.Language.get('error');
		openToastParams.type = 'danger';
	}

	parentOpenToast(openToastParams);
};

const getInitialCurrentTurn = (initialChessMoves) => {
	return initialChessMoves.length % 2 === 0
		? PLAYER_COLOR.WHITE
		: PLAYER_COLOR.BLACK;
};

const ChessGame = ({config}) => {
	const [chessMoves, setChessMoves] = useState(config.initialChessMoves);
	const [currentTurn, setCurrentTurn] = useState(
		getInitialCurrentTurn(config.initialChessMoves)
	);
	const [gameResult, setGameResult] = useState(null);
	const [piecePlacement, setPiecePlacement] = useState(
		config.initialPiecePlacement
	);

	const addChessMove = (
		addMoveURL,
		chessMove,
		chessGameId,
		portletNamespace
	) => {
		const data = Liferay.Util.ns(portletNamespace, {
			chessGameId,
			chessMove,
		});

		const formData = objectToFormData(data);

		fetch(addMoveURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((json) => {
				return json.error === undefined
					? json
					: Promise.reject(new Error(json.error));
			})
			.then((json) => {
				setPiecePlacement(json?.position?.piecePlacement ?? null);
				setGameResult(json?.chessGameResult ?? null);
			})
			.then(() => {
				setChessMoves(chessMoves.concat(chessMove));
			})
			.then(() => {
				updateCurrentTurn();
			})
			.then(() => {
				showNotification(`${currentTurn} moved ${chessMove}`, false);
			})
			.catch((error) => {
				showNotification(error.message, true);
			});
	};

	const handleAddChessMove = (chessMove) => {
		addChessMove(
			config.urls.addMoveURL,
			chessMove,
			config.chessGameId,
			config.portletNamespace
		);
	};

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
				<ChessBoard piecePlacement={piecePlacement} />
				<ChessGameInfo
					blackPlayer={config.blackPlayer}
					whitePlayer={config.whitePlayer}
				/>
				<div>Current turn: {currentTurn}</div>
				{gameResult && (
					<div>Game result: {GAME_RESULT[gameResult]}</div>
				)}
				<ChessMoves chessMoves={chessMoves} />
				<ChessAddMove handleAddChessMove={handleAddChessMove} />
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
		initialPiecePlacement: PropTypes.object.isRequired,
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
