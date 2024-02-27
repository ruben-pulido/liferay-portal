/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

type Layout = {
	friendlyURL: string;
};

export class JSONWebServicesLayoutApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/layout';
	}

	async addLayout(siteId: string, title: string): Promise<Layout> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('privateLayout', 'false');
		urlSearchParams.append('parentLayoutId', '0');
		urlSearchParams.append('name', title);
		urlSearchParams.append('title', title);
		urlSearchParams.append('description', '');
		urlSearchParams.append('type', 'portlet');
		urlSearchParams.append('hidden', 'false');
		urlSearchParams.append('friendlyURL', `/${title}`);

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-layout`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
