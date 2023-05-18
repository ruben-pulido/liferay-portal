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

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {
	useDispatch,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValues from '../../../../../../app/selectors/selectEditableValues';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {updateIn} from '../../../../../../app/utils/updateIn';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../../../prop_types/index';

export default function EditableConfigurationPanel({item}) {
	const dispatch = useDispatch();

	const editableValues = useSelectorCallback(
		(state) => selectEditableValues(state, item.fragmentEntryLinkId),
		[item.fragmentEntryLinkId]
	);

	const onValueSelect = (name, value) => {
		dispatch(
			updateEditableValues({
				editableValues: updateIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						[item.editableId],
						'config',
						name,
					],
					() => value
				),
				fragmentEntryLinkId: item.fragmentEntryLinkId,
			})
		);
	};

	return (
		<>
			<MappingSelector
				fieldType={EDITABLE_TYPES.objectAction}
				mappedItem={editableValues.config?.mappedAction || {}}
				onMappingSelect={(action) => {
					onValueSelect('mappedAction', action);
				}}
			/>

			<SelectField
				field={{
					label: Liferay.Language.get('callback-type'),
					name: 'callbackType',
					typeOptions: {
						validValues: [
							{
								label: Liferay.Language.get('none'),
								value: '',
							},
							{
								label: Liferay.Language.get('toast'),
								value: 'toast',
							},
						],
					},
				}}
				onValueSelect={onValueSelect}
				value={editableValues.config?.callbackType}
			/>

			<LayoutSelector
				label={Liferay.Language.get('redirect-success-page')}
				mappedLayout={editableValues.config?.redirectSuccessLayout}
				onLayoutSelect={(layout) => {
					onValueSelect('redirectSuccessLayout', layout);
				}}
			/>

			<LayoutSelector
				label={Liferay.Language.get('redirect-error-page')}
				mappedLayout={editableValues.config?.redirectErrorLayout}
				onLayoutSelect={(layout) => {
					onValueSelect('redirectErrorLayout', layout);
				}}
			/>
		</>
	);
}

EditableConfigurationPanel.propTypes = {
	item: getEditableItemPropTypes(),
};
