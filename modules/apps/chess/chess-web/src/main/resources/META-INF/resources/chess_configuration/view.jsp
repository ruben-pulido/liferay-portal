<%@ page import="java.util.Map" %>
<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%--
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
--%>

<%@ include file="/init.jsp" %>

	<%
	String segmentEditRootElementId = "a" + "-segment-edit-root";

	Map<String, Object> _data = HashMapBuilder.<String, Object>put(
			"context", null
		).put(
			"props", null
		).build();

	%>

	<div id="<%= segmentEditRootElementId %>">
		<div class="inline-item my-5 p-5 w-100">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<react:component
			data="<%= _data %>"
			module="js/ChessApp.es"
		/>
	</div>


