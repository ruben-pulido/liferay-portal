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

package com.liferay.layout.util.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class LayoutConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testConvertOneColumnMultiplePortlets() throws Exception {
		Map portletIdsMap = new HashMap();

		portletIdsMap.put(
			"column-1",
			new String[]{
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"hello_soy_portlet"
			});

		_testConvert("1_column", portletIdsMap);
	}

	@Test
	public void testConvertOneColumnNoPortlets() throws Exception {
		_testConvert("1_column", new HashMap());
	}

	@Test
	public void testConvertTwoColumnsINoPortlets() throws Exception {
		_testConvert("2_columns_i", new HashMap());
	}

	@Test
	public void testConvertTwoColumnsIMultiplePortlets() throws Exception {
		Map portletIdsMap = new HashMap();

		portletIdsMap.put(
			"column-1",
			new String[]{
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			});

		portletIdsMap.put(
			"column-2",
			new String[]{
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"hello_soy_portlet"
			});

		_testConvert("2_columns_i", portletIdsMap);
	}

	@Test
	public void testConvertTwoColumnsISinglePortlet() throws Exception {
		Map portletIdsMap = new HashMap();

		portletIdsMap.put(
			"column-1",
			new String[]{
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			});

		portletIdsMap.put(
			"column-2",
			new String[]{
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			});

		_testConvert("2_columns_i", portletIdsMap);
	}

	@Test
	public void testConvertOneColumnSinglePortlet() throws Exception {
		Map portletIdsMap = new HashMap();

		portletIdsMap.put(
			"column-1",
			new String[]{
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			});

		_testConvert("1_column", portletIdsMap);
	}

	@Test
	public void testIsConvertibleFalseWidgetPageCustomizable()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutConstants.CUSTOMIZABLE_LAYOUT, Boolean.TRUE.toString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(false, layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleFalseWidgetPageWithNestedApplicationsWidget()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS,
			StringUtil.randomString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(false, layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleTrue() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(true, layoutConverter.isConvertible(layout));
	}

	private String _getLayoutTemplateId(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getLayoutTemplateId();
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _testConvert(
		String layoutTemplateId, Map<String, String[]> portletIdsMap)
		throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		Set<Map.Entry<String, String[]>> entries = portletIdsMap.entrySet();

		for(Map.Entry<String, String[]> entry : entries) {

			for (String portletId : entry.getValue()) {
				LayoutTestUtil.addPortletToLayout(
					TestPropsValues.getUserId(), layout,
					PortletIdCodec.encode(
						portletId), entry.getKey(), new HashMap<>());
			}
		}

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		LayoutData layoutData = layoutConverter.convert(layout);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		String expectedLayoutData = _read(
			String.format("expected_layout_data_%s.json", layoutTemplateId));

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				_group.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());

		int fromIndex = 0;

		for(Map.Entry<String, String[]> entry : entries) {

			String[] portletIds = entry.getValue();

			int numberOfPortletsInColumn = portletIds.length;

			List<FragmentEntryLink> fragmentEntryLinksInColumn =
				fragmentEntryLinks.subList(
					fromIndex, fromIndex + numberOfPortletsInColumn);

			fromIndex = fromIndex + numberOfPortletsInColumn;

			Stream<FragmentEntryLink> stream =
				fragmentEntryLinksInColumn.stream();

			String fragmentEntryLinkIdsJoined = stream.map(
				fragmentEntryLink -> String.format(
					"\"%s\"", fragmentEntryLink.getFragmentEntryLinkId())
			).collect(
				Collectors.joining(StringPool.COMMA_AND_SPACE)
			);

			expectedLayoutData = StringUtil.replaceFirst(
				expectedLayoutData, "[]",
				String.format("[%s]", fragmentEntryLinkIdsJoined));
		}

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		Assert.assertEquals(
			expectedLayoutDataJSONObject.toJSONString(),
			layoutDataJSONObject.toJSONString());
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Inject
	private Portal _portal;

}