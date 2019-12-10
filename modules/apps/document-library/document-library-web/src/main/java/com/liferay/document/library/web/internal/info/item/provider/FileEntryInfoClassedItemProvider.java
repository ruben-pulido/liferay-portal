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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.item.provider.InfoClassedItemProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = InfoClassedItemProvider.class)
public class FileEntryInfoClassedItemProvider
	implements InfoClassedItemProvider<FileEntry> {

	@Override
	public FileEntry getInfoItem(long classPK) throws PortalException {
		return _dlAppLocalService.getFileEntry(classPK);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

}