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

import {objectToFormData, openToast} from 'frontend-js-web';

export default function ({namespace, triggerActionURL}) {
	const triggers = document.querySelectorAll(
		'[data-lfr-editable-type="action"]'
	);

	const onClick = (event) => {
		triggerAction(namespace, event.target, triggerActionURL);
	};

	triggers.forEach((trigger) => {
		trigger.addEventListener('click', onClick);
	});

	return {
		dispose() {
			triggers.forEach((trigger) => {
				trigger.removeEventListener('click', onClick);
			});
		},
	};
}

function triggerAction(namespace, trigger, triggerActionURL) {
	const {actionName, classNameId, classPK} = trigger.dataset;

	if (!actionName) {
		return;
	}

	const loadingIndicator = getLoadingIndicator();

	trigger.classList.add('disabled');
	trigger.appendChild(loadingIndicator);

	Liferay.Util.fetch(new URL(triggerActionURL), {
		body: objectToFormData({
			[`${namespace}actionName`]: actionName,
			[`${namespace}classNameId`]: classNameId,
			[`${namespace}classPK`]: classPK,
		}),
		method: 'POST',
	})
		.then(() => {
			trigger.classList.remove('disabled');
			trigger.removeChild(loadingIndicator);

			openResultToast('success');
		})
		.catch(() => {
			trigger.classList.remove('disabled');
			trigger.removeChild(loadingIndicator);

			openResultToast('danger');
		});
}

function getLoadingIndicator() {
	const element = document.createElement('span');

	element.classList.add(
		'd-inline-block',
		'loading-animation',
		'loading-animation-light',
		'loading-animation-sm',
		'ml-2',
		'my-0'
	);

	return element;
}

function openResultToast(type) {
	openToast({
		message:
			type === 'success'
				? Liferay.Language.get('your-request-completed-successfully')
				: Liferay.Language.get('your-request-failed-to-complete'),
		title:
			type === 'success'
				? Liferay.Language.get('success')
				: Liferay.Language.get('error'),
		type,
	});
}
