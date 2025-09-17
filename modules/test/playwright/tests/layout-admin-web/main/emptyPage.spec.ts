/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {serverAdministrationPageTest} from '../../../fixtures/serverAdministrationPageTest';
import getRandomString from '../../../utils/getRandomString';
import {openProductMenu} from '../../../utils/productMenu';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest,
	serverAdministrationPageTest
);

const getGroovyScript = (
	companyId: string,
	pageName: string,
	siteId: string,
	userId: string
) => `
    import com.liferay.portal.kernel.model.Group
    import com.liferay.portal.kernel.model.Layout
    import com.liferay.portal.kernel.service.GroupLocalServiceUtil
    import com.liferay.portal.kernel.service.LayoutLocalServiceUtil
    import com.liferay.portal.kernel.model.LayoutConstants
    import com.liferay.portal.kernel.service.ServiceContext
    import com.liferay.portal.kernel.service.ServiceContextThreadLocal
    import com.liferay.portal.kernel.util.PortalUtil
    
    def userId = ${userId}
    
    def serviceContext = new ServiceContext()
    def companyGroupId = ${siteId}
    serviceContext.setScopeGroupId(companyGroupId)
    serviceContext.setCompanyId(${companyId})
    serviceContext.setAttribute("layout.instanceable.allowed", Boolean.TRUE);
    
    try {
        out.println(
            LayoutLocalServiceUtil.addLayout(
                null, userId, companyGroupId, false,
                LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
                "${pageName}", "${pageName}", "", LayoutConstants.TYPE_EMPTY, true, "/my-empty-page", serviceContext));
    } catch (Exception e) {
        out.println("An error occurred: " + e.getMessage())
        e.printStackTrace()
    }
`;

test('Empty pages show correct label in UI and correct alert in view mode', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
	pageTreePage,
	pagesAdminPage,
	serverAdministrationPage,
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

	await applicationsMenuPage.goToServerAdministration();
	await serverAdministrationPage.executeScript(
		getGroovyScript(companyId, layoutTitle, site.id, user.id)
	);

	await page.goto(`/web/${site.name}`);

	// Assert label is in Control Menu Bar

	await expect(
		page.locator('.control-menu-nav-item').getByText('Empty')
	).toBeVisible();

	// Assert label is in Product Menu's Page Tree

	await openProductMenu(page);

	await pageTreePage.open();

	await expect(page.getByRole('link', {name: layoutTitle})).toBeVisible();

	await expect(
		page.locator('.treeview-item').getByText('Empty').nth(0)
	).toBeVisible();

	// Assert label is in Group Pages Portlet Miller Columns

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await expect(
		page.locator('.miller-columns-item').getByText('Empty').nth(0)
	).toBeVisible();

	// Check it's a dummy page with an alert in view mode

	await page.goto(`/web/${site.name}/my-empty-page`);

	await expect(
		page.getByText(
			'This page was automatically generated during the import process to ensure the correct hierarchy of imported elements. Edit the page to configure.'
		)
	).toBeVisible();
});
