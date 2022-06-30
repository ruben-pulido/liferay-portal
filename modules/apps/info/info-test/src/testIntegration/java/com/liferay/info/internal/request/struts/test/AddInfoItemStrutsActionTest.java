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

package com.liferay.info.internal.request.struts.test;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import com.liferay.petra.string.StringPool;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.sharepoint.methods.Method;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AddInfoItemStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
//		_group = GroupTestUtil.addGroup();
		_group = _groupLocalService.fetchGroup(56319);

		createObjectDefinition();
	}

	private void createObjectDefinition() throws PortalException {
		_objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(56626);
//			_objectDefinitionLocalService.addCustomObjectDefinition(
//				TestPropsValues.getUserId(),
//				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
//				"Test", null, null,
//				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
//				ObjectDefinitionConstants.SCOPE_COMPANY,
//				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
//				Arrays.asList(
//					ObjectFieldUtil.createObjectField(
//						"Text", "String", "Able", "able", false),
//					ObjectFieldUtil.createObjectField(
//						"Text", "String", "Baker", "baker", false)));

	}

	private HttpServletRequest _getMultipartHttpServletRequest(
		byte[] bytes, String fileNameParameter) {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.addFile(
			new MockMultipartFile(fileNameParameter, bytes));
		mockMultipartHttpServletRequest.setContent(bytes);
		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());
		mockMultipartHttpServletRequest.setCharacterEncoding("UTF-8");

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute(ProgressTracker.PERCENT, new Object());

		mockMultipartHttpServletRequest.setSession(mockHttpSession);

		return mockMultipartHttpServletRequest;
	}

	@Test
	public void testAddInfoItem() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(_user);

//		byte[] bytes = "A".getBytes();
		byte[] bytes = null;

		HttpServletRequest httpServletRequest = _getMultipartHttpServletRequest(
			bytes, "file");

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		long plid = 47;

		Layout layout = _layoutLocalService.fetchLayout(plid);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					httpServletRequest, fileParameters,
					HashMapBuilder.put(
							"groupId", Collections.singletonList(String.valueOf(_group.getGroupId()))
						).put(
							"classNameId", Collections.singletonList("56634")
						).put(
							"classTypeId", Collections.singletonList("0")
						).put(
							"formItemId", Collections.singletonList("95f1249a-7ddc-23ca-8b21-94bd9c6432d9")
						).put(
							"groupId", Collections.singletonList(String.valueOf(_group.getGroupId()))
						).put(
							"plid", Collections.singletonList(String.valueOf(plid))
						).put(
							"segmentsExperienceId", Collections.singletonList("0")
						).put(
							"myText", Collections.singletonList("t1")
						).put(
							"redirect", Collections.singletonList(layout.getFriendlyURL())
						).build()),
				null, RandomTestUtil.randomString());


		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			mockHttpServletResponse, unsyncStringWriter);

		_processEvents(uploadPortletRequest, mockHttpServletResponse, _user);

		_addInfoItemStrutsAction.execute(uploadPortletRequest, pipingServletResponse);

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

//		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		ObjectEntry objectEntry = objectEntries.get(objectEntries.size() - 1);

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals("t1", values.get("myText"));
	}

	private void _processEvents(
//			MockHttpServletRequest mockHttpServletRequest,
			UploadPortletRequest mockHttpServletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/add_info_item");

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, mockHttpServletRequest,
			mockHttpServletResponse);
	}

//	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "component.name=*.AddInfoItemStrutsAction")
	private StrutsAction _addInfoItemStrutsAction;

	private ObjectDefinition _objectDefinition;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;
	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@DeleteAfterTestRun
	private User _user;

}