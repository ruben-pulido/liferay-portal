/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';
import {getComparator} from 'playwright-core/lib/utils';

import * as fs from 'fs';
import * as path from 'path';

import {getTempDir} from './temp';

export function compareScreenshots(screenshotA: Buffer, screenshotB: Buffer) {
	const comparator = getComparator('image/png');

// 	const buffer = comparator(screenshotA, screenshotB, {maxDiffPixels: 100});
	const buffer = comparator(screenshotA, screenshotB);

	if (buffer) {
		fs.writeFileSync(path.join(__dirname, `pepito.png`), buffer.diff);
	}
	

	try {
		expect(buffer).toBe(null);
	}
	catch (error) {
		throw new Error('The screenshots are not the same');
	}
}
