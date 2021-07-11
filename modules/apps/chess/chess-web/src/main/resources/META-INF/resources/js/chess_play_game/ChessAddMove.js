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
import React from 'react';

export default function ChessAddMove() {
	const handleAddMoveClick = () => {
		const moveTextInput = document.getElementById('moveTextInput');

		alert('Add Move button clicked with move: ' + moveTextInput.value);
	};

	return (
		<div className="chess-game__chess-add-move">
			Chess Add Move
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
