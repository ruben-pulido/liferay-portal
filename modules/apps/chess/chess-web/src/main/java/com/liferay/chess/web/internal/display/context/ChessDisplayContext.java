/*
 * *
 *  * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *  *
 *  * This library is free software; you can redistribute it and/or modify it under
 *  * the terms of the GNU Lesser General Public License as published by the Free
 *  * Software Foundation; either version 2.1 of the License, or (at your option)
 *  * any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful, but WITHOUT
 *  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  * details.
 *
 */

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

package com.liferay.chess.web.internal.display.context;

import com.liferay.portal.kernel.util.HashMapBuilder;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class ChessDisplayContext {

	public ChessDisplayContext(
		PortletRequest portletRequest,
		RenderResponse renderResponse) {

		_portletRequest = portletRequest;
		_renderResponse = renderResponse;
	}

	public Map<String, Object> getContext() {

		return HashMapBuilder.<String, Object>put(
			"config",
			HashMapBuilder.<String, Object>put(
				"addMoveURL",
				getAddMoveActionURL()
			).build()
		).put(
			"state",
			HashMapBuilder.<String, Object>put(
				"moves", _getMoves()
			).build()
		).build();
	}

	protected String getAddMoveActionURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, "/chess/add_move");

		actionURL.setParameter(
			"move", _portletRequest.getParameter("move"));

		return actionURL.toString();
	}

	private List<String> _getMoves() {

		if (_moves != null) {
			return _moves;
		}

		List<String> moves = new ArrayList<>();

		moves.add("d2d4");
		moves.add("e7e5");

		_moves = moves;

		return _moves;
	}

	private List<String> _moves;
	private final PortletRequest _portletRequest;
	private final RenderResponse _renderResponse;

}