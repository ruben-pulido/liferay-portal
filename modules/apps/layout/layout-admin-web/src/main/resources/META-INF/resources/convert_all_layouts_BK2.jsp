<%@ page
	import="com.liferay.layout.admin.web.internal.display.context.ConvertAllLayoutsAdminManagementToolbarDisplayContext" %>
<%@ page import="com.liferay.taglib.servlet.PipingServletResponse" %>
<%@ page import="javax.portlet.ActionRequest" %><%--
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

<%--http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&
p_p_lifecycle=0&
p_p_state=maximized&
p_p_mode=view&
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_deltaconvertibleLayoutsCur=2&
p_p_auth=qgIQlYtm&
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_resetCur=false&
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_convertibleLayoutsCur=2--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage
?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet
p_p_lifecycle=0
p_p_state=maximized
p_p_mode=view
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_deltaconvertibleLayoutsCur=2
p_p_auth=e4oLjYkQ
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_resetCur=false
_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_convertibleLayoutsCur=2--%>

<%-- TODO Remove / Reword style --%>
<liferay-ui:message key="convert-all-to-content-page" />

<%--<%--%>
<%--ConvertAllLayoutsAdminManagementToolbarDisplayContext--%>
<%--	convertAllLayoutsManagementToolbarDisplayContext = new ConvertAllLayoutsAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutsAdminDisplayContext);--%>
<%--%>--%>

<%--<clay:management-toolbar--%>
<%--	displayContext="<%= convertAllLayoutsManagementToolbarDisplayContext %>"--%>
<%--/>--%>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>
</liferay-ui:error>

<%
	PortletURL iteratorURL = renderResponse.createRenderURL();
	List<Layout> convertibleLayouts = (List<Layout>)request.getAttribute(LayoutAdminWebKeys.CONVERTIBLE_LAYOUTS);
	List<Layout> notConvertibleLayouts = (List<Layout>)request.getAttribute(LayoutAdminWebKeys.NON_CONVERTIBLE_LAYOUTS);
%>

	<p>
		<b><liferay-ui:message key="convertible-pages"/></b>
	</p>

	<%-- TODO Localized empty result message --%>
	<liferay-ui:search-container
		curParam="convertibleLayoutsCur"
		delta="2"
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
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<br>
	<br>
	<p>
    	<b><liferay-ui:message key="not-convertible-pages"/></b>
	</p>

	<%-- TODO Localized empty result message --%>
	<liferay-ui:search-container
		curParam="notConvertibleLayoutsCur"
		delta="2"
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
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

<aui:form
	action="<%= layoutsAdminDisplayContext.getConvertAllLayoutsURL() %>"
	cssClass="container-fluid-1280" name="fm"
>
<%--	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />--%>
<%--	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuId %>" />--%>
<%--	<aui:input name="type" type="hidden" value="<%= type %>" />--%>

	<aui:button-row>
		<aui:button name="convertToContentPageButton" type="submit" value="convert-to-content-page" />
		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>



<%--<aui:form cssClass="container-fluid-1280" name="fm">--%>
<%----%>
<%--</aui:form>--%>

<%--<aui:button-row>--%>
<%--
&
lt;%
&ndash;	<liferay-portlet:actionURL name="deauthorize" var="deauthorizeURL" />
&ndash;%
&gt;--%>

<%--	<%--%>
<%--		PortletURL convertAllLayoutsActionURL = renderResponse.createActionURL();--%>
<%--	%>--%>

<%--	<aui:button onClick="<%= deauthorizeURL %>" primary="<%= true %>" value="sign-out" />--%>

<%--	<aui:button onClick='<%= renderResponse.getNamespace() + "addDomains();" %>' primary="<%= true %>" value="save" />--%>
<%--	<aui:button type="cancel" />--%>
<%--</aui:button-row>--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage
?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet
&p_p_lifecycle=1
&p_p_state=maximized
&p_p_mode=view
&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_javax.portlet.action=%2Flayout%2Fconvert_all_layouts
&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_redirect=%2Fgroup%2Fguest%2F%7E%2Fcontrol_panel%2Fmanage%3Fp_p_id%3Dcom_liferay_layout_admin_web_portlet_GroupPagesPortlet%26p_p_lifecycle%3D0%26p_p_state%3Dmaximized%26_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_groupId%3D20118%26_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName%3D%252Flayout%252Fget_convertible_layouts%26_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_backURL%3D%252Fgroup%252Fguest%252F%7E%252Fcontrol_panel%252Fmanage%253Fp_p_id%253Dcom_liferay_layout_admin_web_portlet_GroupPagesPortlet%2526p_p_lifecycle%253D0%2526p_p_state%253Dmaximized%2526p_v_l_s_g_id%253D20118%2526p_p_auth%253DU0xjDNGs%26p_p_auth%3DU0xjDNGs
&p_auth=QCnx3y3M
&p_p_auth=U0xjDNGs--%>


<liferay-frontend:component
	componentId="<%= convertAllLayoutsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/ConvertAllLayoutsManagementToolbarDefaultEventHandler.es"
/>
