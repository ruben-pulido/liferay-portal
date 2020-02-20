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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.portlet.ResourceRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ExportLayoutPageTemplateEntriesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutPageTemplateCollection = addLayoutPageTemplateCollection(
			_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFile() throws Exception {
		_layoutPageTemplateEntry = addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Page Template One");

		_addLayoutPageTemplateStructure(_read("layout_data.json"));

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile",
			new Class<?>[] {ResourceRequest.class}, _getMockResourceRequest());

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				_validateZipEntry(zipEntry, zipFile);
			}

			//			if (MapUtil.isEmpty(pageTemplateCollectionEntryMap)) {
			//				if (_log.isDebugEnabled()) {
			//					_log.debug(
			//						"No valid layout page template entries found in " +
			//							zipFile.getName());
			//				}
			//			}

		}
	}

	private void _addLayoutPageTemplateStructure(String layoutData)
		throws Exception {

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			_layoutPageTemplateEntry.getPlid(), layoutData, _serviceContext);
	}

	private MockResourceRequest _getMockResourceRequest() {
		MockResourceRequest mockResourceRequest = new MockResourceRequest();

		mockResourceRequest.addParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return mockResourceRequest;
	}

	private boolean _isPageDefinitionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[3], "page-definition.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateCollectionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 3) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[2], "page-template-collection.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 4) &&
			Objects.equals(pathParts[0], "page-templates") &&
			Objects.equals(pathParts[3], "page-template.json")) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateThumbnailFile(String fileName) {
		String[] path = StringUtil.split(fileName, CharPool.SLASH);

		if ((path.length == 4) && Objects.equals(path[0], "page-templates") &&
			path[3].startsWith("thumbnail.")) {

			return true;
		}

		return false;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateContent(String content, String expectedFileName)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			_read(expectedFileName));

		Assert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString());
	}

	private void _validateZipEntry(ZipEntry zipEntry, ZipFile zipFile)
		throws Exception {

		if (_isPageTemplateCollectionFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_template_collection.json");
		}

		if (_isPageTemplateFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_template.json");
		}

		if (_isPageDefinitionFile(zipEntry.getName())) {
			_validateContent(
				StringUtil.read(zipFile.getInputStream(zipEntry)),
				"expected_page_definition.json");
		}
	}

	private LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return LayoutPageTemplateCollectionLocalServiceUtil.
			addLayoutPageTemplateCollection(
				TestPropsValues.getUserId(), groupId,
				"Page Template Collection One", StringPool.BLANK,
				serviceContext);
	}

	private LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionLocalServiceUtil.
				getLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				layoutPageTemplateCollection.getGroupId(),
				TestPropsValues.getUserId());

		return LayoutPageTemplateEntryLocalServiceUtil.
			addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(),
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollectionId, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
				WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template/export_layout_page_template_entry"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	private static class MockResourceRequest
		extends MockLiferayResourceRequest {

		public MockResourceRequest() {
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

	private static class MockResourceResponse
		extends MockLiferayResourceResponse {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

	}

}