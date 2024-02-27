/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

type Group = {
	groupId: string;
};

export class JSONWebServicesGroupApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/group';
	}

	async getCompanyGroup(companyId: string): Promise<Group> {

		const userName = 'test@liferay.com:test';
		const password = 'test';

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('companyId', companyId);

		const credentials = Buffer.from(`${userName}:${password}`).toString('base64');

		return this.apiHelpers.postForm
		(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-company-group`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
