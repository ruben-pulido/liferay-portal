<%@ page
	import="com.liferay.layout.admin.web.internal.display.context.ConvertAllLayoutsAdminManagementToolbarDisplayContext" %>
<%@ page import="com.liferay.taglib.servlet.PipingServletResponse" %>
<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>
<%@ page import="com.liferay.layout.admin.constants.LayoutAdminPortletKeys" %><%--
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
<%-- DONE When paginating results it takes back to the pages view --%>

<%-- TODO Fix back button --%>
<%-- TODO Cancel button should take you back? --%>

<%-- TODO Fix css --%>
<%-- TODO Makup, br... --%>
<%-- TODO Show title "Convert to Content Page" in top menu --%>
<%-- TODO Localize titles: convertible-pages, non-convertible-pages --%>
<%-- TODO Add localized info on why pages cannot be converted? --%>
<%-- TODO Localized empty result message --%>
<%-- TODO Adjust delta? --%>
<%-- TODO Test with global --%>
<%-- TODO Test with private pages --%>
<%-- TODO Localized empty result message --%>
<%-- TODO Show only widget pages --%>
<%-- TODO SF --%>

<%@ include file="/init.jsp" %>

<%
	PortletURL backURL = renderResponse.createRenderURL();

	backURL.setParameter("mvcPath", "/view.jsp");
	backURL.setParameter("portletMode", "view");

	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL.toString());

	renderResponse.setTitle(
		LanguageUtil.get(request, "convert-to-content-page"));
%>

<%-- TODO Remove / Reword style --%>
<liferay-ui:message key="convert-all-to-content-page" />

<%
	PortletURL iteratorURL = PortalUtil.getControlPanelPortletURL(
		liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
		PortletRequest.RENDER_PHASE);

	iteratorURL.setParameter(
		"groupId", String.valueOf(themeDisplay.getSiteGroupId()));
	iteratorURL.setParameter(
		"mvcRenderCommandName", "/layout/get_convertible_layouts");

	List<Layout> convertibleLayouts =
		(List<Layout>)request.getAttribute(
			LayoutAdminWebKeys.CONVERTIBLE_LAYOUTS);

	List<Layout> notConvertibleLayouts =
		(List<Layout>)request.getAttribute(
			LayoutAdminWebKeys.NON_CONVERTIBLE_LAYOUTS);
%>

	<p>
		<b><liferay-ui:message key="convertible-pages"/></b>
	</p>

	<liferay-ui:search-container
		curParam="convertibleLayoutsCur"
		delta="5"
		emptyResultsMessage="No convertible pages found"
		iteratorURL="<%=iteratorURL%>"
		total="<%= convertibleLayouts.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(convertibleLayouts, searchContainer.getStart(), searchContainer.getEnd()) %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Layout"
			keyProperty="plid"
			modelVar="layout"
		>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="title"
				value="<%= layout.getName(locale) %>"
			/>

		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<br>
	<br>
	<p>
    	<b><liferay-ui:message key="not-convertible-pages"/></b>
	</p>

	<liferay-ui:search-container
		curParam="notConvertibleLayoutsCur"
		delta="5"
		emptyResultsMessage="No non-convertible pages found"
		iteratorURL="<%=iteratorURL%>"
		total="<%= notConvertibleLayouts.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(notConvertibleLayouts, searchContainer.getStart(), searchContainer.getEnd()) %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Layout"
			keyProperty="plid"
			modelVar="layout"
		>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="title"
				value="<%= layout.getName(locale) %>"
			/>

		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

<aui:form
	action="<%= layoutsAdminDisplayContext.getConvertAllLayoutsURL() %>"
	cssClass="container-fluid-1280" name="fm"
>
	<aui:button-row>
		<aui:button name="convertToContentPageButton" type="submit" value="convert-all-to-content-page" />
		<aui:button href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>