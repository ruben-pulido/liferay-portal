/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';

export class HeadlessDeliveryApiHelper {
	constructor(apiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-delivery/v1.0';
	}

	async postRandomStructuredContent(siteId) {
		const title = 'StructuredContent' + getRandomInt();

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/structured-contents`,
			{
				contentFields: [],
				contentStructureId: 32805,
				title: `${title}`,
				viewableBy: 'Anyone'
			}
		);
	}

}
