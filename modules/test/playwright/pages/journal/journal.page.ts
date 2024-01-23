/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Page} from "@playwright/test";

export class JournalPage {
	readonly page: Page;

	constructor(page) {
		this.page = page;
	}

	async goto() {
		await this.page.goto(
			'/group/guest/~/control_panel/manage?p_p_id=com_liferay_journal_web_portlet_JournalPortlet'
		);
	}

}
