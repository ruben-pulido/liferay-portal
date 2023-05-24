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

export default function () {
	const triggers = document.querySelectorAll('.object-action-trigger');

	triggers.forEach((trigger) => {
		trigger.addEventListener('click', onTriggerClick);
	});

	return {
		dispose() {
			triggers.forEach((trigger) => {
				trigger.removeEventListener('click', onTriggerClick);
			});
		},
	};
}

function onTriggerClick(event) {
	const trigger = event.target;

	const {actionUrl, callbackType, redirectUrl} = trigger.dataset;

	if (!actionUrl) {
		return;
	}

	const loadingIndicator = getLoadingIndicator();

	trigger.classList.add('disabled');
	trigger.appendChild(loadingIndicator);

	setTimeout(() => {
		Liferay.Util.fetch(new URL(actionUrl), {
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'PUT',
		})
			.then(() => {
				trigger.classList.remove('disabled');
				trigger.removeChild(loadingIndicator);

				if (callbackType === 'toast') {
					openToast('success');
				}
				else if (callbackType === 'redirect') {
					Liferay.Util.navigate(redirectUrl);
				}
			})
			.catch(() => openToast('danger'));
	}, 2000);
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

function openToast(type) {
	Liferay.Util.openToast({
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
