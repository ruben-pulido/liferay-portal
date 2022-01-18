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
		const editableValues = {
		"com.liferay.fragment.entry.processor.background.image.BackgroundImageFragmentEntryProcessor": {},
		"com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor": {},
		"com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor": {
			"buttonType": "outline-primary",
			"buttonSize": "lg"
			}
		};

		// const data = new URLSearchParams();
		// data.append(`${namespace}editableValues`, JSON.stringify(editableValues));

		fetch(previewURL, {
			body: objectToFormData({
				[`${namespace}editableValues`]: JSON.stringify(editableValues),
			}),
			// body: data,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((data) => {
				setHTML(data.html);
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
