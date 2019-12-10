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

package com.liferay.layout.content.page.editor.web.internal.permission.util;

import com.liferay.layout.util.permission.LayoutClassedModelUsagePermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = {})
public class ClassedModelUsagePermissionUtil {

	public static boolean contains(
		PermissionChecker permissionChecker, long classNameId, long classPK,
		String actionId) {

		return _layoutClassedModelUsagePermission.contains(
			permissionChecker, classNameId, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setsLayoutClassedModelUsagePermission(
		LayoutClassedModelUsagePermission layoutClassedModelUsagePermission) {

		_layoutClassedModelUsagePermission = layoutClassedModelUsagePermission;
	}

	private static LayoutClassedModelUsagePermission
		_layoutClassedModelUsagePermission;

}