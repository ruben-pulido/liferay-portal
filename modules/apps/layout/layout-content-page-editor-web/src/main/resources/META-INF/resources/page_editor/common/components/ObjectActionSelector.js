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

import {config} from '../../app/config/index';
import ItemSelector from './ItemSelector';

export function ObjectActionSelector({
	mappedAction: initialMappedAction,
	onActionSelect,
	label = Liferay.Language('action'),
}) {
	const [mappedAction, setMappedAction] = useState(initialMappedAction);

	return (
		<div className="mb-3">
			<ItemSelector
				eventName={`${config.portletNamespace}selectObjectAction`}
				itemSelectorURL={config.itemSelectorURL}
				label={label}
				onItemSelect={(action) => {
					setMappedAction(action);
					onActionSelect(action);
				}}
				selectedItem={mappedAction}
				showMappedItems={false}
			/>
		</div>
	);
}

ObjectActionSelector.propTypes = {
	label: PropTypes.string,
	mappedLayout: PropTypes.shape({
		layoutUuid: PropTypes.string,
		title: PropTypes.string.isRequired,
	}),
	onLayoutSelect: PropTypes.func.isRequired,
};
