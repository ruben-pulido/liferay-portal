/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {serverAdministrationPageTest} from '../../../fixtures/serverAdministrationPageTest';
import getRandomString from '../../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	serverAdministrationPageTest
);

test('Empty pages show correct label in UI and correct alert in view mode', async ({
	apiHelpers,
	page,
	pageTreePage,
	pagesAdminPage,
	site,
}) => {

	// Create a page of type Empty

	const layoutTitle = getRandomString();

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'test@liferay.com'
		);

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		externalReferenceCode: null,
		groupId: companyId,
		hidden: 'true',
		options: {
			type: 'empty',
		},
		serviceContext: {
			companyId,
			'layout.instanceable.allowed': true,
			'scopeGroupId': companyId,
			'userId': user.id,
		},
		title: layoutTitle,
		userId: user.id,
	});

	/* await page.goto(`/web/${site.name}`);

	// Assert label is in Control Menu Bar

	await expect(
		page.locator(
			"//div[@class='control-menu-nav-item']/span[contains(@class, 'label-warning')]/span[text()='Empty']"
		)
	).toBeVisible();

	// Assert label is in Product Menu's Page Tree

	await openProductMenu(page);

	await pageTreePage.open();

	await expect(page.getByRole('link', {name: layoutTitle})).toBeVisible();

	await expect(
		page.locator(
			`//span[text()='${layoutTitle}']/span[contains(@class, 'label-warning')]/span[text()='Empty']`
		)
	).toBeVisible();

	// Assert label is in Group Pages Portlet Miller Columns

	await pagesAdminPage.goto(site.friendlyUrlPath);

	const emptyLayoutLocator = page.locator(
		`//a[@aria-label='${layoutTitle} Empty']/parent::li`
	);

	await expect(emptyLayoutLocator).toBeVisible();

	await expect(
		emptyLayoutLocator.locator(
			"//span[contains(@class, 'label-warning')]/span[text()='Empty']"
		)
	).toBeVisible();

	// Check it's a dummy page with an alert in view mode

	await page.goto(`/web/${site.name}${layout.friendlyURL}`);

	await expect(
		page.getByText(
			'This page was automatically generated during the import process to ensure the correct hierarchy of imported elements. Edit the page to configure.'
		)
	).toBeVisible();*/
});
