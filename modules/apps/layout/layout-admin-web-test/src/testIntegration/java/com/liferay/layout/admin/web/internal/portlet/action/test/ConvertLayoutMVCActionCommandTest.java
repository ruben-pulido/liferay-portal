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

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
@Sync
public class ConvertLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addLayout(_group);

		_serviceContext = _getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testDoProcessAction() throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		Layout originalLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		Map<String, String[]> sourcePortletIdsMap = new HashMap<String, String[]>() {
			{
				put(
					"column-1",
					new String[] {
						"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
					});
			}
		};

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(sourcePortletIdsMap);
				}
			};

		List<Map<String, List<String>>> encodedPortletIdsMaps =
			new ArrayList<>();

		int columnId = 0;

		for (Map<String, String[]> portletIdsMap : portletIdsMaps) {
			Set<Map.Entry<String, String[]>> entries = portletIdsMap.entrySet();

			Map<String, List<String>> encodedPortletIdsMap = new TreeMap<>();

			for (Map.Entry<String, String[]> entry : entries) {
				columnId++;

				encodedPortletIdsMap.put(entry.getKey(), new ArrayList<>());

				List<String> encodedPortletIds = encodedPortletIdsMap.get(
					entry.getKey());

				for (String portletId : entry.getValue()) {
					Portlet portlet = _portletLocalService.getPortletById(
						_group.getCompanyId(), portletId);

					String encodedPortletId = portletId;

					if (portlet.isInstanceable()) {
						encodedPortletId = PortletIdCodec.encode(portletId);
					}

					LayoutTestUtil.addPortletToLayout(
						TestPropsValues.getUserId(), originalLayout,
						encodedPortletId, "column-" + columnId,
						new HashMap<>());

					encodedPortletIds.add(encodedPortletId);
				}
			}

			encodedPortletIdsMaps.add(encodedPortletIdsMap);
		}

		ActionRequest actionRequest = _getMockActionRequest(
			originalLayout.getLayoutId());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_convertLayout",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Layout persistedLayout = _layoutService.getLayoutByUuidAndGroupId(
			originalLayout.getUuid(), originalLayout.getGroupId(),
			originalLayout.isPrivateLayout());

		Assert.assertNotNull(persistedLayout);

		Assert.assertEquals(
			originalLayout.getGroupId(), persistedLayout.getGroupId());
		Assert.assertEquals(
			originalLayout.isPrivateLayout(), persistedLayout.isPrivateLayout());
		Assert.assertEquals(
			originalLayout.getLayoutId(), persistedLayout.getLayoutId());
		Assert.assertEquals(
			originalLayout.getClassName(), persistedLayout.getClassName());
		Assert.assertEquals(
			originalLayout.getClassNameId(), persistedLayout.getClassNameId());
		Assert.assertEquals(
			originalLayout.getDescriptionMap(),
			persistedLayout.getDescriptionMap());
		Assert.assertEquals(
			originalLayout.getFriendlyURLMap(),
			persistedLayout.getFriendlyURLMap());
		Assert.assertEquals(
			originalLayout.getKeywordsMap(),
			persistedLayout.getKeywordsMap());
		Assert.assertEquals(
			originalLayout.getNameMap(), persistedLayout.getNameMap());
		Assert.assertEquals(
			originalLayout.getParentLayoutId(),
			persistedLayout.getParentLayoutId());
		Assert.assertEquals(
			originalLayout.getPlid(), persistedLayout.getPlid());
		Assert.assertEquals(
			originalLayout.getRobotsMap(),
			persistedLayout.getRobotsMap());
		Assert.assertEquals(
			originalLayout.getTitleMap(), persistedLayout.getTitleMap());
		Assert.assertEquals(
			"content",
			persistedLayout.getType());
		Assert.assertEquals(
			originalLayout.getTypeSettings(),
			persistedLayout.getTypeSettings());
		Assert.assertEquals(
			originalLayout.getUserId(), persistedLayout.getUserId());
		Assert.assertEquals(
			originalLayout.isSystem(),
			persistedLayout.isSystem());
	}

	private MockActionRequest _getMockActionRequest(long layoutId)
		throws PortalException {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		MockActionRequest mockActionRequest = new MockActionRequest(
			themeDisplay);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockActionRequest.addParameter(
			"selPlid", String.valueOf(layoutId));

		return mockActionRequest;
	}

	private ServiceContext _getServiceContext(Group group, long userId) {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockActionResponse());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, userId);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/layout/convert_layout"
	)
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		public MockActionRequest(ThemeDisplay themeDisplay) {
			_themeDisplay = themeDisplay;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			MockHttpServletRequest httpServletRequest =
				new MockHttpServletRequest();

			httpServletRequest.setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE, new MockActionResponse());
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			return httpServletRequest;
		}

		private final ThemeDisplay _themeDisplay;

	}

	@Inject
	private LayoutService _layoutService;

	@Inject
	private PortletLocalService _portletLocalService;

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

	}

}