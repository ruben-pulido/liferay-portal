/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {test as apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {test as journalPagesTest} from '../../fixtures/journalPages.fixture';
import {test as productMenuPageTest} from '../../fixtures/productMenuPages.fixture';

export const test = mergeTests(
	apiHelpersTest,
	journalPagesTest,
	productMenuPageTest,
);

test('filtering by mine displays only web content created by me', async ({
	_apiHelpers,
	_journalPage,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-194763', 'true');
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-196768', 'true');
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-197692', 'true');
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-202534', 'true');

	// const journalTitle = 'Australia';

	await _journalPage.goto();

	// await expect(
	// 	_journalPage.page
	// 		.locator('a')
	// 		.filter({hasText: journalTitle})
	// ).toBeVisible();

	// Clean up

	// await _apiHelpers.journalAdmin.deleteObjectFolder(journalFolder.id);
});