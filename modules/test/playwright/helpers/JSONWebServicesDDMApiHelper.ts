/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

type DDMStructure = {
	structureId: string;
};

export class JSONWebServicesDDMApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/ddm.ddmstructure';
	}

	async fetchStructure(groupId: string, classNameId: string, structureKey: string): Promise<DDMStructure> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('classNameId', classNameId);
		urlSearchParams.append('structureKey', structureKey);

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/fetch-structure`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
