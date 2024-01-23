/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {JournalPage} from '../pages/journal/journal.page';

const journalPagesTest = test.extend<{
	_journalPage: JournalPage;
}>({
	_journalPage: async ({page}, use) => {
		await use(new JournalPage(page));
	},
});

export {journalPagesTest};
