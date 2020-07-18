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

package com.liferay.chess.web.internal.portlet;

import com.liferay.chess.web.internal.constants.ChessPortletKeys;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChessPortletKeys.MANAGE_FRIENDS,
		"mvc.command.name=/chess/update_friend"
	},
	service = {MVCActionCommand.class}
)
public class UpdateFriendMVCActionCommand
	extends BaseMVCActionCommand implements MVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String friendId = ParamUtil.getString(actionRequest, "friendId");
		String isFavorite = ParamUtil.getString(actionRequest, "isFavorite");

		User friendUser = _userService.getUserById(Long.parseLong(friendId));

		String updatedLastName = friendUser.getLastName() + " (F)";

		friendUser.setLastName(updatedLastName);

		_userLocalService.updateUser(friendUser);

		JSONObject jsonObject = JSONUtil.put("updatedLastName", updatedLastName);

		MultiSessionMessages.add(
			actionRequest, "friendId: " + friendId + "; isFavorite: " + isFavorite);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}