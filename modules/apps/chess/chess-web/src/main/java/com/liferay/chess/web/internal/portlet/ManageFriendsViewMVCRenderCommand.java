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
import com.liferay.chess.web.internal.constants.ChessWebKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChessPortletKeys.MANAGE_FRIENDS,
		"mvc.command.name=/", "mvc.command.name=/chess/manage_friends"
	},
	service = MVCRenderCommand.class
)
public class ManageFriendsViewMVCRenderCommand
	implements MVCRenderCommand {

	private static final int _MAX_USERS = 100;

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			ChessWebKeys.CHESS_REACT_DATA,
			HashMapBuilder.<String, Object>put(
				"actionUrl", _getUpdateFriendActionURL(renderResponse)
			).put(
				"friends",
				_getFriendsJSONArray()
			).put(
				"portletNamespace", renderResponse.getNamespace()
			).build());

		return "/manage_friends/view.jsp";
	}

	private JSONArray _getFriendsJSONArray() {

		JSONArray friendsJSONArray =
			JSONFactoryUtil.createJSONArray();

		List<User> users = _userLocalService.getUsers(
			0, Math.min(_userLocalService.getUsersCount(), _MAX_USERS));

		for (User user : users) {

			JSONObject collaboratorJSONObject = JSONUtil.put(
				"fullName", user.getFullName()
			).put(
				"isFavorite", user.getFirstName().equals("Test")
			).put(
				"userId", Long.valueOf(user.getUserId())
			);

			friendsJSONArray.put(collaboratorJSONObject);
		}

		return friendsJSONArray;
	}

	private String _getUpdateFriendActionURL(RenderResponse renderResponse) {

		PortletURL updateFriendURL = renderResponse.createActionURL();

		updateFriendURL.setParameter(
			ActionRequest.ACTION_NAME, "/chess/update_friend");

		return updateFriendURL.toString();
	}

	@Reference
	private UserLocalService _userLocalService;

}