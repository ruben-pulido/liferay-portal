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

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
//import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.info.internal.request.struts.test.ContentLayoutTestUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.segments.service.SegmentsExperienceLocalService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

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
		_user = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(_user);

//		_setUpPrincipalThreadLocal();

		_group = GroupTestUtil.addGroup();

		_objectDefinition = _addObjectDefinition();

		_layout = _addLayout();
	}

	@After
	public void tearDown() throws PortalException {
//		PrincipalThreadLocal.setName(_name);
	}

	@Test
	public void testAddInfoItem() throws Exception {
		HttpServletRequest httpServletRequest =
			new MockMultipartHttpServletRequest();

		String classNameId = String.valueOf(
			_portal.getClassNameId(
				"com.liferay.object.model.ObjectDefinition#" +
					_objectDefinition.getObjectDefinitionId()));

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					httpServletRequest, null,
					HashMapBuilder.put(
						"classNameId", Collections.singletonList(classNameId)
					).put(
						"classTypeId", Collections.singletonList("0")
					).put(
						"formItemId", Collections.singletonList(_formItemId)
					).put(
						"groupId",
						Collections.singletonList(
							String.valueOf(_group.getGroupId()))
					).put(
						"myDecimal",
						Collections.singletonList("9999999999999998")
					).put(
						"myInteger", Collections.singletonList("999999999")
					).put(
						"myLongInteger",
						Collections.singletonList("9007199254740991")
					).put(
						"myPrecisionDecimal",
						Collections.singletonList(
							"99999999999999.9999999999999999")
					).put(
						"myText", Collections.singletonList("t1")
					).put(
						"plid",
						Collections.singletonList(
							String.valueOf(_layout.getPlid()))
					).put(
						"redirect",
						Collections.singletonList(
							"http://localhost:8080" + _layout.getFriendlyURL())
					).put(
						"segmentsExperienceId",
						Collections.singletonList(
							String.valueOf(_defaultSegmentsExperienceId))
					).build()),
				null, RandomTestUtil.randomString());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			mockHttpServletResponse, unsyncStringWriter);

		_processEvents(uploadPortletRequest, mockHttpServletResponse, _user);

		_addInfoItemStrutsAction.execute(
			uploadPortletRequest, pipingServletResponse);

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		ObjectEntry objectEntry = objectEntries.get(objectEntries.size() - 1);

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(
			Double.valueOf("9999999999999998"), values.get("myDecimal"));
		Assert.assertEquals(999999999, values.get("myInteger"));
		Assert.assertEquals(9007199254740991L, values.get("myLongInteger"));
		Assert.assertEquals(
			new BigDecimal("99999999999999.9999999999999999"),
			values.get("myPrecisionDecimal"));
		Assert.assertEquals("t1", values.get("myText"));
	}

	private Layout _addLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), RandomTestUtil.randomString());

		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0, nameMap, nameMap,
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), LayoutConstants.TYPE_CONTENT,
			UnicodePropertiesBuilder.put(
				"published", "true"
			).buildString(),
			false, false, Collections.emptyMap(), 0, serviceContext);

		_defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, _objectDefinition.getClassName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			_objectDefinition.getClassName(), _group.getGroupId());

		JSONObject jsonObject = ContentLayoutTestUtil.addFormToLayout(
			layout,
			String.valueOf(
				_portal.getClassNameId(_objectDefinition.getClassName())),
			"0", _defaultSegmentsExperienceId,
			infoForm.getInfoField("myDecimal"),
			infoForm.getInfoField("myInteger"),
			infoForm.getInfoField("myLongInteger"),
			infoForm.getInfoField("myPrecisionDecimal"));

		List<String> formStyledLayoutStructureItemsIds =
			(List<String>)jsonObject.get("formStyledLayoutStructureItemsIds");

		_formItemId = formStyledLayoutStructureItemsIds.get(0);

		return layout;
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		return _addObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
					ObjectFieldConstants.DB_TYPE_DOUBLE,
					RandomTestUtil.randomString(), "myDecimal", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER,
					RandomTestUtil.randomString(), "myInteger", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectFieldConstants.DB_TYPE_LONG,
					RandomTestUtil.randomString(), "myLongInteger", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL,
					RandomTestUtil.randomString(), "myPrecisionDecimal",
					false)));
	}

	private ObjectDefinition _addObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		objectDefinition.setTitleObjectFieldId(
			_getTitleObjectFieldId(objectDefinition.getObjectDefinitionId()));

		objectDefinition = _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private long _getTitleObjectFieldId(long objectDefinitionId)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0, objectDefinitionId,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, true, true, null,
			LocalizedMapUtil.getLocalizedMap("My Text"), "myText", false,
			Collections.emptyList());

		return objectField.getObjectFieldId();
	}

	private void _processEvents(
			UploadPortletRequest uploadPortletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		uploadPortletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/add_info_item");

		uploadPortletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, uploadPortletRequest,
			mockHttpServletResponse);
	}

	private void _setUpPrincipalThreadLocal() {
		_name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_user.getUserId());
	}

	@Inject(filter = "component.name=*.AddInfoItemStrutsAction")
	private StrutsAction _addInfoItemStrutsAction;

	private long _defaultSegmentsExperienceId;
	private String _formItemId;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private String _name;
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@DeleteAfterTestRun
	private User _user;

}