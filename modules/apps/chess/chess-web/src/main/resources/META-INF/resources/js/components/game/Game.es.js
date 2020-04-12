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

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import React from 'react';

import {config} from '../../config/index';

export default function Game() {
	return (
		<div className="game-root">
			<h1>Chess Game</h1>
			<table>
				<thead>
					<tr>
						<th>Player</th>
						<th>Move</th>
					</tr>
				</thead>
				<tbody>
					<tr className="row-white-player">
						<td>White</td>
						<td>e2e4</td>
					</tr>
					<tr className="row-black-player">
						<td>Black</td>
						<td>e7e5</td>
					</tr>
				</tbody>
			</table>

			<form action={config.addMoveURL} method="POST">
				<input name="move" type="text" />

				<ClayButton displayType="primary" small type="submit">
					{Liferay.Language.get('add-move')}
				</ClayButton>
			</form>
		</div>
	);
}
