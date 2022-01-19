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

import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export function VariationPreview({
	fragmentEntryKey,
	label,
	namespace,
	previewURL,
	variation,
}) {
	const [html, setHTML] = useState('');

	useEffect(() => {

		console.log({variation});

		const configurationValues = Object.assign(...variation.map((x) => {
			const {name, value} = x;
			return {[name]: value};
		}))

		console.log({configurationValues});

		fetch(previewURL, {
			body: objectToFormData({
				[`_${namespace}_configurationValues`]: JSON.stringify(configurationValues)
			}),
			method: 'POST',
		})
		.then((response) => response.text())
		.then((data) => {
			setHTML(data);
		})
		.catch((error) => {
			console.error(error);
			setHTML('ERROR');
		});
	}, [fragmentEntryKey, namespace, previewURL, variation]);

	return (
		<article>
			<h5>{label}</h5>

			<div dangerouslySetInnerHTML={{__html: html}} />
		</article>
	);
}
