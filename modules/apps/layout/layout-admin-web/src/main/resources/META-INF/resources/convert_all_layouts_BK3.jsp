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
<%-- TODO Show title "Convert to Content Page" in top menu --%>
<%-- TODO Localize titles: convertible-pages, non-convertible-pages --%>
<%-- TODO Add localized info on why pages cannot be converted? --%>
<%-- TODO Adjust delta? --%>
<%-- TODO Test with global --%>
<%-- TODO Test with private pages --%>
<%-- TODO Localized empty result message --%>
<%-- TODO Show only widget pages --%>
<%-- TODO SF --%>

<%@ include file="/init.jsp" %>

<%
//	String backURL = ParamUtil.getString(request, "redirect");
	PortletURL backURL = renderResponse.createRenderURL();
	backURL.setParameter("mvcPath", "/view.jsp");
	backURL.setParameter("portletMode", "view");
	System.out.println("backURL: " + backURL);


	System.out.println("Hello3");
	portletDisplay.setShowBackIcon(true);
//	portletDisplay.setURLBack(redirect);
	portletDisplay.setURLBack(backURL.toString());
	System.out.println("Hello4");


	System.out.println("Hello1");
	PortletURL myURL = renderResponse.createRenderURL();
	myURL.setParameter("mvcRenderCommandName", "/layout/get_convertible_layouts");

	String redirect = ParamUtil.getString(request, "redirect", String.valueOf(myURL));




	System.out.println("Hello2");


//	String redirect = ParamUtil.getString(request, "redirect");

//if (Validator.isNotNull(redirect)) {
//	System.out.println("Hello3");
//	portletDisplay.setShowBackIcon(true);
//	portletDisplay.setURLBack(redirect);
//	System.out.println("Hello4");
//}
%>

<%-- TODO Remove / Reword style --%>
<liferay-ui:message key="convert-all-to-content-page" />

<%
	System.out.println("Hello5");
//	PortletURL iteratorURL = renderResponse.createRenderURL();
//	iteratorURL.setParameter("mvcRenderCommandName", "/layout/get_convertible_layouts");

	PortletURL iteratorURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		iteratorURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getSiteGroupId()));
		iteratorURL.setParameter(
			"mvcRenderCommandName", "/layout/get_convertible_layouts");


	List<Layout> convertibleLayouts = (List<Layout>)request.getAttribute(LayoutAdminWebKeys.CONVERTIBLE_LAYOUTS);
	List<Layout> notConvertibleLayouts = (List<Layout>)request.getAttribute(LayoutAdminWebKeys.NON_CONVERTIBLE_LAYOUTS);
	System.out.println("Hello6");
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
		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

<aui:form
	action="<%= layoutsAdminDisplayContext.getConvertAllLayoutsURL() %>"
	cssClass="container-fluid-1280" name="fm"
>
<%--	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />--%>
<%--	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuId %>" />--%>
<%--	<aui:input name="type" type="hidden" value="<%= type %>" />--%>

	<aui:button-row>
		<aui:button name="convertToContentPageButton" type="submit" value="convert-all-to-content-page" />
<%--		<aui:button href="<%= redirect %>" type="cancel" />--%>
		<aui:button href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>


<%--http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet--%>
<%--
&p_p_lifecycle=0--%>
<%--
&p_p_state=maximized--%>
<%--
&p_p_mode=view--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout%2Fget_convertible_layouts--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_deltaconvertibleLayoutsCur=2--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_resetCur=false--%>
<%--&p_p_auth=HcshO5oR--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_convertibleLayoutsCur=2--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet--%>
<%--&p_p_lifecycle=0--%>
<%--&p_p_state=maximized--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_groupId=20118--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout%2Fget_convertible_layouts--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_backURL=%2Fgroup%2Fguest%2F~%2Fcontrol_panel%2Fmanage%3Fp_p_id%3Dcom_liferay_layout_admin_web_portlet_GroupPagesPortlet%26p_p_lifecycle%3D0%26p_p_state%3Dmaximized%26p_v_l_s_g_id%3D20118%26p_p_auth%3DHcshO5oR--%>
<%--&p_p_auth=HcshO5oR--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet--%>
<%--&p_p_lifecycle=0--%>
<%--&p_p_state=maximized--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_groupId=20118--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout%2Fget_convertible_layouts--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_deltaconvertibleLayoutsCur=2--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_resetCur=false--%>
<%--&p_p_auth=HcshO5oR--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_convertibleLayoutsCur=12--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage
?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet--%>
<%--&p_p_lifecycle=0--%>
<%--&p_p_state=maximized--%>
<%--&p_p_mode=view--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcPath=%2Fview.jsp--%>
<%--&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_portletMode=view--%>
<%--&p_p_auth=Y0cKpZkp--%>

<%--http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet
&p_p_lifecycle=0
&p_p_state=maximized
&p_p_mode=view
&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcPath=%2Fview.jsp
&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_portletMode=view
&p_p_auth=Y0cKpZkp--%>