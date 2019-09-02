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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
//import com.liferay.dynamic.data.mapping.kernel.DDMForm;
//import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.content.page.editor.web.internal.portlet.action.test.util.MockLiferayPortletRequest;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;

import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AddStructuredContentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

//	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/add_structured_content")
	private MVCActionCommand _mvcActionCommand;

	private DDMTemplate _ddmTemplate;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_ddmStructure = _addDDMStructure();

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			"<div>${Text.getData()}</div>", LocaleUtil.US);
	}

	private DDMStructure _addDDMStructure() throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Text", "Text Label", "text", "string", false, false, false));

//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"Boolean", "Boolean Label", "checkbox", "boolean", false, false,
//				false));
//
//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"Documents_and_Media", "Documents_and_Media Label",
//				"document-library", "string", false, false, false));
//
//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"Geolocation", "Geolocation Label", "geolocation", "string",
//				false, false, false));
//
//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"HTML", "HTML Label", "html", "string", false, false, false));
//
//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"Image", "Image Label", "image", "string", false, false, false));
//
//		ddmForm.addDDMFormField(
//			DDMFormTestUtil.createDDMFormField(
//				"Link_to_Page", "Link to Page Label", "link_to_page", "string",
//				false, false, false));
//

////		ddmForm.addDDMFormField(
////			DDMFormTestUtil.createDDMFormField(
////				"Select", "Select Label", "select", "string", false, false,
////				false));
////
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);
	}

	@Test
	public void testAddStructuredContent() throws Exception {
		List<JournalArticle> originalJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		MockLiferayPortletRequest mockLiferayPortletRequest =
			_getMockLiferayPortletRequest();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			_ddmStructure.getDDMForm());

		String fieldName = "Text";
		String fieldValue = "My Text";

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				fieldName, fieldValue));

		mockLiferayPortletRequest.addParameter(
			"ddmFormValues", _ddm.getDDMFormValuesJSONString(ddmFormValues));

		mockLiferayPortletRequest.addParameter(
			"ddmStructureId", String.valueOf(_ddmStructure.getStructureId()));

		String title = StringUtil.randomString(10);

		mockLiferayPortletRequest.addParameter("title", title);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addJournalArticle",
			new Class<?>[]{ActionRequest.class}, mockLiferayPortletRequest);

		List<JournalArticle> actualJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			actualJournalArticles.toString(),
			originalJournalArticles.size() + 1,
			actualJournalArticles.size());

		long classNameId = jsonObject.getLong("classNameId");
		long classPK = jsonObject.getLong("classPK");
		String actualTitle = jsonObject.getString("title");

		Assert.assertEquals(title, actualTitle);

		JournalArticle actualJournalArticle =
			_journalArticleLocalService.getLatestArticle(classPK);

		Assert.assertEquals(title, actualJournalArticle.getTitle());

		Document document = SAXReaderUtil.read(
			actualJournalArticle.getContent());

		Node node = document.selectSingleNode(
			String.format(
				"/root/dynamic-element[@name='%s']/dynamic-content", fieldName));

		Assert.assertEquals(fieldValue, node.getText());

		Assert.assertEquals(
			JournalArticle.class.getName(), _portal.getClassName(classNameId));
	}

	private MockLiferayPortletRequest _getMockLiferayPortletRequest()
		throws PortalException {

		MockLiferayPortletRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.addParameter(
			"ddmStructureId",
			String.valueOf(_ddmStructure.getStructureId()));
		mockActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private DDM _ddm;

	@Inject
	private CompanyLocalService _companyLocalService;

	private DDMStructure _ddmStructure;

	private static class MockActionRequest extends MockLiferayPortletRequest {

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

}