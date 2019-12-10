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

package com.liferay.layout.internal.security.permission;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.permission.LayoutClassedModelUsagePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = LayoutClassedModelUsagePermission.class)
public class LayoutClassedModelUsagePermissionImpl
	implements LayoutClassedModelUsagePermission {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetModelResourcePermission"
	)
	public void addModelResourcePermission(
		ModelResourcePermission modelResourcePermission,
		Map<String, Object> properties) {

		String modelClassName = GetterUtil.getString(
			properties.get("model.class.name"));

		if (Validator.isNull(modelClassName)) {
			return;
		}

		_modelResourcePermissions.put(modelClassName, modelResourcePermission);
	}

	public boolean contains(
		PermissionChecker permissionChecker, long classNameId, long classPK,
		String actionId) {

		try {
			ModelResourcePermission modelResourcePermission =
				_modelResourcePermissions.get(
					_portal.getClassName(classNameId));

			if (modelResourcePermission.contains(
					permissionChecker, classPK, actionId)) {

				return true;
			}
		}
		catch (PortalException pe) {
			_log.error("An error occurred while checking permissions", pe);
		}

		return false;
	}

	public boolean contains(
		PermissionChecker permissionChecker, long plid, String actionId) {

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(plid);

		try {
			for (LayoutClassedModelUsage layoutClassedModelUsage :
					layoutClassedModelUsages) {

				if (contains(
						permissionChecker,
						layoutClassedModelUsage.getClassNameId(),
						layoutClassedModelUsage.getClassPK(), actionId)) {

					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error("An error occurred while getting mapped contents", e);
		}

		return false;
	}

	public void unsetModelResourcePermission(
		ModelResourcePermission modelResourcePermission,
		Map<String, Object> properties) {

		String modelClassName = (String)properties.get("model.class.name");

		if (modelClassName == null) {
			throw new IllegalArgumentException(
				"The property \"model.class.name\" is null");
		}

		_modelResourcePermissions.remove(modelClassName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsagePermissionImpl.class);

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	private final Map<String, ModelResourcePermission>
		_modelResourcePermissions = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

}