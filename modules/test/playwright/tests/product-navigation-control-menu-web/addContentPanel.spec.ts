/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomId from "../../utils/getRandomId";
import {widgetPagesTest} from "./fixtures/widgetPagesTest";

export const test = mergeTests(
	apiHelpersTest,
	loginTest,
	widgetPagesTest
);

test('LPD-15256 Scheduled web contents should be displayed in content tab of widget page', async ({
	apiHelpers,
	page,
	widgetPage,
}) => {

	// Create site

	const site = await apiHelpers.headlessSite.createSite(getRandomId());
	// const site: Site = {friendlyUrlPath: '/s1', id: "32964"};

	// Get liferay.com company id
	const company = await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
		'liferay.com'
	);

	// Get global site key
	const globalGroup = await apiHelpers.jsonWebServicesGroup.getCompanyGroup(
		company.companyId
	);

	const className = await apiHelpers.jsonWebServicesClassName.fetchClassName(
		'com.liferay.journal.model.JournalArticle'
	  );

	const ddmStructure = await apiHelpers.jsonWebServicesDDM.fetchStructure(
		globalGroup.groupId, className.classNameId, 'BASIC-WEB-CONTENT'
	  );

	// Create published web content

	const approvedWebContentTitle = 'Approved Web Content';

	await apiHelpers.headlessDelivery.postStructuredContent(site.id, Number(ddmStructure.structureId), null, approvedWebContentTitle)

	// Create scheduled web content

	const now = new Date();

	const oneYearFromNow = new Date(now.getFullYear() + 1, now.getMonth(), now.getDate());

	const scheduledWebContentTitle = 'Scheduled Web Content';

	await apiHelpers.headlessDelivery.postStructuredContent(site.id, Number(ddmStructure.structureId), oneYearFromNow.toISOString(), scheduledWebContentTitle)

	// Create draft web content

	const draftWebContentTitle = 'Draft Web Content';

	await apiHelpers.headlessAdminContent.postStructuredContentDraft(site.id, Number(ddmStructure.structureId), null, draftWebContentTitle)

	// Create expired web content

	const expiredWebContentTitle = 'Expired Web Content';

	const expiredWebContent = await apiHelpers.headlessDelivery.postStructuredContent(site.id, Number(ddmStructure.structureId), null,  expiredWebContentTitle)

	await apiHelpers.jsonWebServicesJournal.expireArticle(site.id, expiredWebContent.key);

	// Create in trash web content

	const inTrashWebContentTitle = 'Web Content in Thrash';

	const inTrashWebContent = await apiHelpers.headlessDelivery.postStructuredContent(site.id, Number(ddmStructure.structureId), null,  inTrashWebContentTitle)

	await apiHelpers.jsonWebServicesJournal.moveArticleToTrash(site.id, inTrashWebContent.key);

	// Create widget page

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout(
		site.id, getRandomId()
	);

	await expect(layout).toHaveProperty('friendlyURL',  expect.anything());

	// await expect(layout).toEqual({ friendlyURL: expect.anything() });

	// Navigate to widget page

	await widgetPage.goToSitePage(site, layout);

	/*

	//

	// Click on the add icon

	await widgetPage.clickControlMenuAddButton();

	// Navigate to the content tab

	await widgetPage.goToControlMenuAddPanelContentTab()

	// Verify that published and scheduled web contents are listed

	await expect( page.getByText(approvedWebContentTitle)).toBeVisible()
	await expect( page.getByText(scheduledWebContentTitle)).toBeVisible()

	// Verify that draft and expired web contents are not listed

	await expect( page.getByText(draftWebContentTitle)).not.toBeVisible()
	await expect( page.getByText(expiredWebContentTitle)).not.toBeVisible()

	// Change the number of items displayed

  	await page.getByLabel('Select Label').selectOption('8');

	// Verify that published and scheduled web contents are listed

	await expect( page.getByText(approvedWebContentTitle)).toBeVisible()
	await expect( page.getByText(scheduledWebContentTitle)).toBeVisible()

	// Verify that draft and expired web contents are not listed

	await expect( page.getByText(draftWebContentTitle)).not.toBeVisible()
	await expect( page.getByText(expiredWebContentTitle)).not.toBeVisible()

	// Toggle the display style of the list

	await page.getByRole('button', { name: 'Display Style' }).click();

	// Verify that published and scheduled web contents are listed

	await expect( page.getByText(approvedWebContentTitle)).toBeVisible()
	await expect( page.getByText(scheduledWebContentTitle)).toBeVisible()

	// Verify that draft, expired and in-thrash web contents are not listed

	await expect( page.getByText(draftWebContentTitle)).not.toBeVisible()
	await expect( page.getByText(expiredWebContentTitle)).not.toBeVisible()
	await expect( page.getByText(inTrashWebContentTitle)).not.toBeVisible()

	// Delete site
	// TODO
	// await apiHelpers.headlessSite.deleteSite(site.id);

	 */
});
