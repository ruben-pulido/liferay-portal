/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProductMenuPage} from '../product-navigation-product-menu/productMenu.page';

export class JournalPage {
	constructor(page) {
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);

		// this.uncategorizedObjectFolderLink = page
		// 	.locator('li')
		// 	.filter({hasText: 'Uncategorized'});
	}

	async goto() {
		await this.productMenuPage.goToContentAndData();
	}

	// async clickUncategorizedObjectFolder() {
	// 	await this.uncategorizedObjectFolderLink.click();
	// }

}
