/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import serviceFetch from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch';
import ImageSourcePanel from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/item_configuration_panels/ImageSourcePanel';

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			defaultLanguageId: 'en_US',
			getAvailableImageConfigurationsURL:
				'getAvailableImageConfigurationsURL',
			getInfoItemFieldValueURL: 'getInfoItemFieldValueURL',
		},
	})
);

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn()
);

function renderImageSourcePanel(editableValue) {
	const state = {
		fragmentEntryLinks: {
			0: {
				editableValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						'editable-id-0': editableValue,
					},
				},
			},
		},
		languageId: 'en_US',
		selectedViewportSize: VIEWPORT_SIZES.tablet,
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<ImageSourcePanel
				item={{
					editableId: 'editable-id-0',
					editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					fragmentEntryLinkId: '0',
					itemId: '',
					type: EDITABLE_TYPES.image,
				}}
			/>
		</StoreAPIContextProvider>
	);
}

describe('ImageSourcePanel', () => {
	beforeEach(() => {
		serviceFetch.mockImplementation((url) => {
			if (url === 'getInfoItemFieldValueURL') {
				return Promise.resolve({
					fieldValue: {
						fileEntryId: '1234',
						url: 'http://localhost/image.png',
					},
				});
			}

			return Promise.resolve([
				{label: 'Auto', size: '300', value: 'auto', width: 2560},
				{
					label: 'Thumbnail-300x300',
					size: '20',
					value: 'Thumbnail-300x300',
					width: 300,
				},
			]);
		});
	});

	afterEach(() => {
		serviceFetch.mockReset();
	});

	it('shows the resolutions of the mapped image when the editable has a default value', async () => {
		renderImageSourcePanel({
			classNameId: '1',
			classPK: '2',
			config: {},
			defaultValue: 'data:image/png;base64,AAAA',
			fieldId: 'fileURL',
		});

		const select = await screen.findByLabelText('resolution');

		expect(
			Array.from(select.options).map((option) => option.value)
		).toEqual(['Thumbnail-300x300', 'auto']);
	});

	it('does not show the resolution selector when the mapped image has no file entry', async () => {
		serviceFetch.mockImplementation(() =>
			Promise.resolve({
				fieldValue: {url: 'http://localhost/user_portrait'},
			})
		);

		renderImageSourcePanel({
			classNameId: '1',
			classPK: '2',
			config: {},
			defaultValue: 'data:image/png;base64,AAAA',
			fieldId: 'userProfileImage',
		});

		await waitFor(() => expect(serviceFetch).toHaveBeenCalled());

		expect(screen.queryByLabelText('resolution')).not.toBeInTheDocument();
	});
});
