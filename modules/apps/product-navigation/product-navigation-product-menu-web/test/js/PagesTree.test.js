/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import PagesTree from '../../src/main/resources/META-INF/resources/js/PagesTree';

const renderComponent = ({items = []} = {}) => {
	return render(
		<PagesTree config={{}} isPrivateLayoutsTree={false} items={items} />
	);
};

describe('PagesTree', () => {
	it('renders Empty label when a page is of type empty', () => {
		const ITEMS_WITH_EMPTY_PAGE = [
			{
				id: '1',
				name: 'Home',
				plid: '10',
				type: 'empty',
			},
		];

		renderComponent({items: ITEMS_WITH_EMPTY_PAGE});

		expect(screen.getByText('empty')).toBeInTheDocument();
	});
});
