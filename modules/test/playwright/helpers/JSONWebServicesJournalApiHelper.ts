/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';
import {liferayConfig} from "../liferay.config";

export class JSONWebServicesJournalApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/journal.journalarticle';
	}

	async expireArticle(siteId: string, articleId: string): Promise<void> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('articleId', articleId);
		urlSearchParams.append('articleURL', '');

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/expire-article`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}

	async moveArticleToTrash(siteId: string, articleId: string): Promise<void> {

		const urlSearchParams = new URLSearchParams();

		// TODO Review warnings
		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('articleId', articleId);

		const credentials = Buffer.from('test@liferay.com:test').toString('base64');

		return this.apiHelpers.postWithHeaders(
			`${liferayConfig.environment.baseUrl}${this.basePath}/move-article-to-trash`,
			urlSearchParams.toString(),
			{
				'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': `Basic ${credentials}`,
			}
		);
	}
}
