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

<%@ include file="/init.jsp" %>

<%
int fragmentCollectionContributorsCount = fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionContributorsCount();
int globalFragmentCollectionsCount = fragmentCollectionItemSelectorDisplayContext.getGlobalFragmentCollectionsCount();
int groupFragmentCollectionsCount = fragmentCollectionItemSelectorDisplayContext.getGroupFragmentCollectionsCount();
%>

<clay:container-fluid
	cssClass="container-view"
	id="<portlet:namespace />wrapper"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="mb-2 nav nav-stacked">
						<c:choose>
							<c:when test="<%= (groupFragmentCollectionsCount > 0) || (fragmentCollectionContributorsCount > 0) || (globalFragmentCollectionsCount > 0) %>">
								<c:if test="<%= fragmentCollectionContributorsCount > 0 %>">
									<clay:content-row
										cssClass="mb-4"
										verticalAlign="center"
									>
										<clay:content-col
											expand="<%= true %>"
										>
											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollectionItemSelectorDisplayContext.getGroupId() == CompanyConstants.SYSTEM) ? "active" : StringPool.BLANK %>"
													href="<%=
														fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionItemSelectorURL(CompanyConstants.SYSTEM)
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollectionItemSelectorDisplayContext.getGroupName(CompanyConstants.SYSTEM)) %></span>
												</a>
											</li>
										</clay:content-col>
									</clay:content-row>
								</c:if>

								<c:if test="<%= globalFragmentCollectionsCount > 0 %>">
									<clay:content-row
										cssClass="mb-4"
										verticalAlign="center"
									>
										<clay:content-col
											expand="<%= true %>"
										>
											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollectionItemSelectorDisplayContext.getGroupId() == themeDisplay.getCompanyGroupId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionItemSelectorURL(themeDisplay.getCompanyGroupId())
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollectionItemSelectorDisplayContext.getGroupName(themeDisplay.getCompanyGroupId())) %></span>
												</a>
											</li>
										</clay:content-col>
									</clay:content-row>
								</c:if>

								<c:if test="<%= groupFragmentCollectionsCount > 0 %>">
									<clay:content-row
										cssClass="mb-4"
										verticalAlign="center"
									>
										<clay:content-col
											expand="<%= true %>"
										>
											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollectionItemSelectorDisplayContext.getGroupId() == scopeGroupId) ? "active" : StringPool.BLANK %>"
													href="<%=
														fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionItemSelectorURL(scopeGroupId)
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollectionItemSelectorDisplayContext.getGroupName(scopeGroupId)) %></span>
												</a>
											</li>
										</clay:content-col>
									</clay:content-row>
								</c:if>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>

				<clay:sheet
					size="full"
				>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= HtmlUtil.escape(fragmentCollectionItemSelectorDisplayContext.getGroupName()) %>
							</clay:content-col>
						</clay:content-row>
					</h2>

					<c:if test="<%= fragmentCollectionItemSelectorDisplayContext.getGroupId() != CompanyConstants.SYSTEM %>">
						<clay:container-fluid>
								<liferay-ui:search-container
									searchContainer="<%= fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionSearchContainer() %>"
								>
									<liferay-ui:search-container-row
										className="com.liferay.fragment.model.FragmentCollection"
										keyProperty="fragmentCollectionId"
										modelVar="fragmentCollection"
									>

										<liferay-ui:search-container-column-text>
												<clay:navigation-card
													navigationCard="<%= new FragmentCollectionNavigationCard(fragmentCollection, request, liferayPortletResponse) %>"
												/>
										</liferay-ui:search-container-column-text> </liferay-ui:search-container-row>
									<liferay-ui:search-iterator
										displayStyle="icon"
										markupView="lexicon"
									/>
								</liferay-ui:search-container>
						</clay:container-fluid>
					</c:if>

					<c:if test="<%= fragmentCollectionItemSelectorDisplayContext.getGroupId() == CompanyConstants.SYSTEM %>">
						<clay:container-fluid>
								<liferay-ui:search-container
									searchContainer="<%= fragmentCollectionItemSelectorDisplayContext.getFragmentCollectionContributorSearchContainer() %>"
								>
									<liferay-ui:search-container-row
										className="com.liferay.fragment.contributor.FragmentCollectionContributor"
										keyProperty="fragmentCollectionKey"
										modelVar="fragmentCollectionContributor"
									>
										<liferay-ui:search-container-column-text>
											<clay:navigation-card
												navigationCard="<%= new FragmentCollectionContributorNavigationCard(fragmentCollectionContributor, request, liferayPortletResponse) %>"
											/>
										</liferay-ui:search-container-column-text>
									</liferay-ui:search-container-row>

									<liferay-ui:search-iterator
										displayStyle="icon"
										markupView="lexicon"
									/>
								</liferay-ui:search-container>
						</clay:container-fluid>
					</c:if>
				</clay:sheet>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"portletNamespace", "<portlet:namespace />"
		).build()
	%>'
	module="js/index"
/>