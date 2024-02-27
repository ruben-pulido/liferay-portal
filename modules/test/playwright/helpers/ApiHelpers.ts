/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessAdminContentApiHelper} from './HeadlessAdminContentApiHelper';
import {HeadlessAdminUserApiHelper} from './HeadlessAdminUserApiHelper';
import {HeadlessCommerceAdminCatalogApiHelper} from './HeadlessCommerceAdminCatalogApiHelper';
import {HeadlessCommerceAdminChannelApiHelper} from './HeadlessCommerceAdminChannelApiHelper';
import {HeadlessCommerceDeliveryCartApiHelper} from './HeadlessCommerceDeliveryCartApiHelper';
import {HeadlessCommerceDeliveryCatalogApiHelper} from './HeadlessCommerceDeliveryCatalogApiHelper';
import {HeadlessDeliveryApiHelper} from './HeadlessDeliveryApiHelper';
import {HeadlessSiteApiHelper} from './HeadlessSiteApiHelper';
import {JSONWebServicesClassNameApiHelper} from './JSONWebServicesClassNameApiHelper';
import {JSONWebServicesCompanyApiHelper} from './JSONWebServicesCompanyApiHelper';
import {JSONWebServicesDDMApiHelper} from './JSONWebServicesDDMApiHelper';
import {JSONWebServicesGroupApiHelper} from './JSONWebServicesGroupApiHelper';
import {JSONWebServicesJournalApiHelper} from './JSONWebServicesJournalApiHelper';
import {JSONWebServicesLayoutApiHelper} from './JSONWebServicesLayoutApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';
import {ObjectApiHelper} from './ObjectApiHelper';

export class ApiHelpers {
	readonly baseUrl: string;
	readonly featureFlag: FeatureFlagApiHelper;
	readonly headlessAdminContent: HeadlessAdminContentApiHelper;
	readonly headlessAdminUser: HeadlessAdminUserApiHelper;
	readonly headlessCommerceAdminCatalog: HeadlessCommerceAdminCatalogApiHelper;
	readonly headlessCommerceAdminChannel: HeadlessCommerceAdminChannelApiHelper;
	readonly headlessCommerceDeliveryCatalog: HeadlessCommerceDeliveryCatalogApiHelper;
	readonly headlessCommerceDeliveryCart: HeadlessCommerceDeliveryCartApiHelper;
	readonly headlessDelivery: HeadlessDeliveryApiHelper;
	readonly headlessSite: HeadlessSiteApiHelper;
	readonly jsonWebServicesClassName: JSONWebServicesClassNameApiHelper;
	readonly jsonWebServicesCompany: JSONWebServicesCompanyApiHelper;
	readonly jsonWebServicesDDM: JSONWebServicesDDMApiHelper;
	readonly jsonWebServicesGroup: JSONWebServicesGroupApiHelper;
	readonly jsonWebServicesJournal: JSONWebServicesJournalApiHelper;
	readonly jsonWebServicesLayout: JSONWebServicesLayoutApiHelper;
	readonly object: ObjectApiHelper;
	readonly objectAdmin: ObjectAdminApiHelper;
	readonly page: Page;

	private static readonly _authorization = `Basic ${Buffer.from(
		`${liferayConfig.user.login}:${liferayConfig.user.password}`
	).toString('base64')}`;

	constructor(page: Page) {
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.headlessAdminContent = new HeadlessAdminContentApiHelper(this);
		this.headlessAdminUser = new HeadlessAdminUserApiHelper(this);
		this.headlessCommerceAdminCatalog =
			new HeadlessCommerceAdminCatalogApiHelper(this);
		this.headlessCommerceAdminChannel =
			new HeadlessCommerceAdminChannelApiHelper(this);
		this.headlessCommerceDeliveryCatalog =
			new HeadlessCommerceDeliveryCatalogApiHelper(this);
		this.headlessCommerceDeliveryCart =
			new HeadlessCommerceDeliveryCartApiHelper(this);
		this.headlessDelivery = new HeadlessDeliveryApiHelper(this);
		this.headlessSite = new HeadlessSiteApiHelper(this);
		this.jsonWebServicesClassName = new JSONWebServicesClassNameApiHelper(
			this
		);
		this.jsonWebServicesCompany = new JSONWebServicesCompanyApiHelper(this);
		this.jsonWebServicesDDM = new JSONWebServicesDDMApiHelper(this);
		this.jsonWebServicesGroup = new JSONWebServicesGroupApiHelper(this);
		this.jsonWebServicesJournal = new JSONWebServicesJournalApiHelper(this);
		this.jsonWebServicesLayout = new JSONWebServicesLayoutApiHelper(this);
		this.object = new ObjectApiHelper(this);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.page = page;
	}

	async delete(url: string) {
		return this.page.request.delete(url, {
			headers: await this.getHeader(),
		});
	}

	async get(url: string) {
		const response = await this.page.request.get(url, {
			headers: await this.getHeader(),
		});

		return response.json();
	}

	async patch(url: string, data: DataObject) {
		const response = await this.page.request.patch(url, {
			data,
			headers: await this.getHeader(),
		});

		const text = await response.text();

		if (!text) {
			return response;
		}

		return response.json();
	}

	async post(
		url: string,
		data: DataObject | any[] | string,
		headers?: {[key: string]: string}
	) {
		const response = await this.page.request.post(url, {
			data,
			failOnStatusCode: true,
			headers: headers || (await this.getHeader()),
		});

		return response.json();
	}

	getBasicAuthFormHeaders() {
		return {
			'Authorization': ApiHelpers._authorization,
			'Content-Type': 'application/x-www-form-urlencoded',
		};
	}

	async getHeader() {
		const authToken = await this.page.evaluate(() => Liferay.authToken);

		return {
			'Content-Type': 'application/json',
			'x-csrf-token': authToken,
		};
	}
}
