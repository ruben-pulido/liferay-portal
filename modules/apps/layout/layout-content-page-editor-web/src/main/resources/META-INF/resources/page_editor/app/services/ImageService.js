/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get available image configurations
	 * @param {object} options
	 * @param {string} options.className class name
	 * @param {string} options.classPK class PK
	 * @param {string} options.fieldId Field ID
	 * @param {string} options.fileEntryId FileEntry ID
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableImageConfigurations({
		className,
		classPK,
		fieldId,
		fileEntryId,
		onNetworkStatus,
	}) {
		return serviceFetch(
			config.getAvailableImageConfigurationsURL,
			{
				body: {
					className,
					classPK,
					fieldId,
					fileEntryId,
				},
			},
			onNetworkStatus
		);
	},
};
