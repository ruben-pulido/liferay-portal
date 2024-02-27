/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

type Company = {
	companyId: string;
};

export class JSONWebServicesCompanyApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/company';
	}

	async getCompanyByWebId(webId: string): Promise<Company> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('webId', webId);

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-company-by-web-id`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
