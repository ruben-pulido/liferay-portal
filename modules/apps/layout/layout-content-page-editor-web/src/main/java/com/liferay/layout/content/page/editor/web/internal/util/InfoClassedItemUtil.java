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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.item.provider.InfoClassedItemProvider;
import com.liferay.info.item.provider.InfoClassedItemProviderTracker;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = {})
public class InfoClassedItemUtil {

	public static Object getItem(String className, long classPK)
		throws PortalException {

		InfoClassedItemProvider infoClassedItemProvider =
			_infoClassedItemProviderTracker.getInfoClassedItemProvider(
				className);

		if (infoClassedItemProvider == null) {
			return null;
		}

		return infoClassedItemProvider.getInfoItem(classPK);
	}

	@Reference(unbind = "-")
	protected void setsInfoClassedItemProviderTracker(
		InfoClassedItemProviderTracker infoClassedItemProviderTracker) {

		_infoClassedItemProviderTracker = infoClassedItemProviderTracker;
	}

	private static InfoClassedItemProviderTracker
		_infoClassedItemProviderTracker;

}