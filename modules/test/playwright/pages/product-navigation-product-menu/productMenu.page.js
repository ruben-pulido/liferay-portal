/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export class ProductMenuPage {
	constructor(page) {
		this.productMenuButton = page.getByLabel('Open Product Menu');

		// this.contentAndDataLink = page.getById(
		// 	'panel-manage-site_administration_content-link');

		this.contentAndDataLink = page.getByText('Content & Data');
		this.webContentLink = page.getByText('Content & Data');
		this.page = page;
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToContentAndData() {
		await this.goToProductMenu();
		await this.contentAndDataLink.click();
	}

	async goToProductMenu() {
		await this.goto();
		await this.productMenuButton.click();
	}
}
