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

import {delegate} from 'frontend-js-web';

export default function ({portletNamespace}) {
	const parentElement = document.getElementById(`${portletNamespace}wrapper`);

	if (!parentElement) {
		return;
	}

	return delegate(parentElement, 'click', '.card-interactive', (event) => {
		const {name, previewUrl: previewURL} = event.delegateTarget.dataset;
		const opener = Liferay.Util.getOpener();

		opener.Liferay.fire(
			'_com_liferay_style_book_web_internal_portlet_StyleBookPortlet_selectPreviewItem',
			{value: JSON.stringify({name, previewURL})}
		);

		opener.Liferay.fire('closeModal');
	});
}
