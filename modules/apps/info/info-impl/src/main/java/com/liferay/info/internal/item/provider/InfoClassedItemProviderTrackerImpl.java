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

package com.liferay.info.internal.item.provider;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.provider.InfoClassedItemProvider;
import com.liferay.info.item.provider.InfoClassedItemProviderTracker;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = InfoClassedItemProviderTracker.class)
public class InfoClassedItemProviderTrackerImpl
	implements InfoClassedItemProviderTracker {

	@Override
	public InfoClassedItemProvider getInfoClassedItemProvider(
		String className) {

		return _infoClassedItemProviders.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoClassedItemProviders(
		InfoClassedItemProvider infoClassedItemProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoClassedItemProvider);

		_infoClassedItemProviders.put(className, infoClassedItemProvider);
	}

	protected void unsetInfoClassedItemProviders(
		InfoClassedItemProvider infoClassedItemProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoClassedItemProvider);

		_infoClassedItemProviders.remove(className);
	}

	private final Map<String, InfoClassedItemProvider>
		_infoClassedItemProviders = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

}