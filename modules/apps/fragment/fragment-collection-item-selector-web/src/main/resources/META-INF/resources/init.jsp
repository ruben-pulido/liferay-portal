<%--
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.fragment.collection.item.selector.web.internal.constants.FragmentCollectionItemSelectorWebKeys" %><%@
page import="com.liferay.fragment.collection.item.selector.web.internal.display.context.FragmentCollectionItemSelectorDisplayContext" %><%@
page import="com.liferay.fragment.collection.item.selector.web.internal.servlet.taglib.clay.FragmentCollectionContributorVerticalCard" %><%@
page import="com.liferay.fragment.collection.item.selector.web.internal.servlet.taglib.clay.FragmentCollectionVerticalCard" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.model.CompanyConstants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %>

<liferay-frontend:defineObjects />

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<liferay-theme:defineObjects />

<%
FragmentCollectionItemSelectorDisplayContext fragmentCollectionItemSelectorDisplayContext = (FragmentCollectionItemSelectorDisplayContext)request.getAttribute(FragmentCollectionItemSelectorWebKeys.FRAGMENT_COLLECTION_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<%@ include file="/init-ext.jsp" %>