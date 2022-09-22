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

package com.liferay.chess.web.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class ChessDataPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		for (UserData userData : _getUsersData()) {
			_addUser(company, userData);
		}
	}

	private static final List<UserData> _getUsersData() {
		List<UserData> userData = new ArrayList<>();

		userData.add(
			new UserData("carlos", "carlos@chess.com", "Carlos", "Valerio"));
		userData.add(
			new UserData("dani", "dani@chess.com", "Daniel", "Balbontín"));
		userData.add(
			new UserData("richi", "richi@chess.com", "Ricardo", "Fernández"));
		userData.add(
			new UserData("ruben", "ruben@chess.com", "Rubén", "Pulido"));

		return userData;
	}

	private void _addUser(Company company, UserData userData) throws Exception {
		User existingUser = _userLocalService.fetchUserByEmailAddress(
			company.getCompanyId(), userData.emailAddress);

		if (existingUser != null) {
			return;
		}

		User defaultUser = company.getDefaultUser();

		_userLocalService.addDefaultAdminUser(
			company.getCompanyId(), userData.screenName, userData.emailAddress,
			defaultUser.getLocale(), userData.firstName, null,
			userData.lastName);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private static class UserData {

		public UserData(
			String screenName, String emailAddress, String firstName,
			String lastName) {

			this.screenName = screenName;
			this.emailAddress = emailAddress;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String emailAddress;
		public String firstName;
		public String lastName;
		public String screenName;

	}

}