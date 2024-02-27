/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

type ClassName = {
	classNameId: string;
};

export class JSONWebServicesClassNameApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/classname';
	}

	async fetchClassName(value: string): Promise<ClassName> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('value', value);

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/fetch-class-name`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
