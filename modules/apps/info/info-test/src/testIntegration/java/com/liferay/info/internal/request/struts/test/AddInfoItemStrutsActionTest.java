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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.sharepoint.methods.Method;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

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

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();
	}

//	private void createObjectDefinition() {
//		_objectDefinition =
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
//
//		_objectFieldLocalService.addCustomObjectField(
//			TestPropsValues.getUserId(), 0,
//			objectDefinition.getObjectDefinitionId(), "Text", "String", null,
//			false, false, null, LocalizedMapUtil.getLocalizedMap("Charlie"),
//			"charlie", true, Collections.emptyList());
//
//	}

	@Test
	public void testAddInfoItem() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(_user);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				Method.POST, "/portal/add_info_item");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			mockHttpServletResponse, unsyncStringWriter);

		mockHttpServletRequest.setParameter(
			"classNameId", String.valueOf(1));
		mockHttpServletRequest.setParameter(
			"formItemId", String.valueOf(1));
		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockHttpServletRequest.setParameter(
			"redirect", "");

		_processEvents(mockHttpServletRequest, mockHttpServletResponse, _user);

		_addInfoItemStrutsAction.execute(
			mockHttpServletRequest, pipingServletResponse);

		URL renderedURL = _bundle.getEntry(
			_RESOURCES_PATH + "simple.html");

		String actualHTML = _getHTML(unsyncStringWriter.toString());

		String expectedHTML = _getHTML(
			StringUtil.read(renderedURL.openStream()));

		Assert.assertEquals(expectedHTML, actualHTML);
	}

	private String _getHTML(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.indentAmount(0);
		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		Element bodyElement = document.body();

		Elements elements = bodyElement.getElementsByTag("title");

		elements.remove();

		elements = bodyElement.getElementsByTag("link");

		elements.remove();

		elements = bodyElement.getElementsByTag("script");

		elements.remove();

		return _removeSpacingCharactersBetweenTags(bodyElement);
	}

	private void _processEvents(
			MockHttpServletRequest mockHttpServletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/fragment/render_fragment_entry");

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, mockHttpServletRequest,
			mockHttpServletResponse);
	}

	private String _removeSpacingCharactersBetweenTags(Element bodyElement) {
		String htmlString = bodyElement.html();

		htmlString = htmlString.replaceAll(">\\s+", ">");

		return htmlString.replaceAll("\\s+<", "<");
	}

	private static final String _RESOURCES_PATH =
		"com/liferay/info/internal/request/struts/test/dependencies/";

	private Bundle _bundle;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "component.name=*.AddInfoItemStrutsAction")
	private StrutsAction _addInfoItemStrutsAction;

//	private ObjectDefinition _objectDefinition;
//
//	@Inject
//	private ObjectDefinitionLocalService _objectDefinitionLocalService;
//
//	@Inject
//	private ObjectFieldLocalService _objectFieldLocalService;

	@DeleteAfterTestRun
	private User _user;

}