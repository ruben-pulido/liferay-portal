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

import React from 'react';

import ChessBoard from './ChessBoard';
import ChessGameInfo from './ChessGameInfo';

const ChessGame = ({config}) => {
	return (
		<div>
			Chess Game Id: {config.chessGameId}
			<ChessBoard />
			<ChessGameInfo
				blackPlayer={config.blackPlayer}
				whitePlayer={config.whitePlayer}
			/>
		</div>
	);
};

export default function ({config}) {
	return <ChessGame config={config} />;
}
