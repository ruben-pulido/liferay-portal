<%@ page
	import="com.liferay.layout.admin.web.internal.display.context.ConvertAllLayoutsAdminManagementToolbarDisplayContext" %><%--
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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNotNull(redirect)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
}
%>

<%-- TODO Remove / Reword & style --%>
<liferay-ui:message key="convert-all-to-content-page" />

<%--<%--%>
<%--ConvertAllLayoutsAdminManagementToolbarDisplayContext--%>
<%--	convertAllLayoutsManagementToolbarDisplayContext = new ConvertAllLayoutsAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutsAdminDisplayContext);--%>
<%--%>--%>

<clay:management-toolbar
	displayContext="<%= convertAllLayoutsManagementToolbarDisplayContext %>"
/>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>
</liferay-ui:error>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="pages"
		searchContainer="<%= convertAllLayoutsManagementToolbarDisplayContext.getLayoutsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Layout"
			keyProperty="plid"
			modelVar="layout"
		>

			<%
			PortletURL portletURL = convertAllLayoutsManagementToolbarDisplayContext.getPortletURL();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= portletURL %>"
				name="title"
				value="<%= layout.getName(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="relative-path"
			>

			</liferay-ui:search-container-column-text>

			<%
			LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(layout.getType());

			ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-ws-nowrap"
				name="type"
				value='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + layout.getType()) %>'
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-ws-nowrap"
				name="create-date"
				property="createDate"
			/>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= convertAllLayoutsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/ConvertAllLayoutsManagementToolbarDefaultEventHandler.es"
/>
