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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.content.page.editor.web.internal.portlet.action.test.util.MockLiferayPortletRequest;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.net.URL;

import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testAddStructuredContentStructureWithFieldImageBase64Png()
		throws Exception {

		String fieldValue = _read("image_base_64_png.txt");

		_validateAddStructuredContentStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentStructureWithFieldImageURLJpg()
		throws Exception {

		String fieldValue =
			"https://pbs.twimg.com/profile_images/1016933890274025472/hnd_Al" +
				"2U_400x400.jpg";

		URL url = new URL(fieldValue);

		_validateAddStructuredContentStructureWithFieldImage(
			fieldValue, Base64.encode(FileUtil.getBytes(url.openStream())));
	}

	@Test
	public void testAddStructuredContentStructureWithFieldText()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_validateAddStructuredContentStructureWithField(
			DDMFormFieldType.TEXT, StringUtil.randomString(10), fieldValue,
			StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	@Test
	public void testAddStructuredContentStructureWithFieldTextArea()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_validateAddStructuredContentStructureWithField(
			DDMFormFieldType.TEXT_AREA, StringUtil.randomString(10), fieldValue,
			StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	@Test
	public void testAddStructuredContentStructureWithFieldTextHTML()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_validateAddStructuredContentStructureWithField(
			DDMFormFieldType.TEXT_HTML, StringUtil.randomString(10),
			StringUtil.randomString(10), StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	private DDMStructure _addDDMStructure(DDMFormField... ddmFormFields)
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);
	}

	private DDMFormValues _getDDMFormValues(
		DDMStructure ddmStructure, String fieldName, String fieldValue) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmStructure.getDDMForm());

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				fieldName, fieldValue));

		return ddmFormValues;
	}

	private String _getFieldValue(
			JournalArticle journalArticle, String fieldName)
		throws DocumentException {

		Document document = SAXReaderUtil.read(journalArticle.getContent());

		Node node = document.selectSingleNode(
			String.format(
				"/root/dynamic-element[@name='%s']/dynamic-content",
				fieldName));

		return node.getText();
	}

	private MockLiferayPortletRequest _getMockLiferayPortletRequest(
			DDMFormValues ddmFormValues, long ddmStructureId, String title)
		throws PortalException {

		MockLiferayPortletRequest mockLiferayPortletRequest =
			new MockActionRequest();

		mockLiferayPortletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletRequest.addParameter(
			"ddmFormValues", _ddm.getDDMFormValuesJSONString(ddmFormValues));

		mockLiferayPortletRequest.addParameter(
			"ddmStructureId", String.valueOf(ddmStructureId));

		mockLiferayPortletRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		mockLiferayPortletRequest.addParameter("title", title);

		return mockLiferayPortletRequest;
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

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _validateAddStructuredContentStructureWithField(
			String fieldType, String fieldName, String fieldValue, String title,
			UnsafeConsumer<String, Exception> fieldValueValidator)
		throws Exception {

		DDMStructure ddmStructure = _addDDMStructure(
			DDMFormTestUtil.createDDMFormField(
				fieldName, StringUtil.randomString(10), fieldType, "string",
				false, false, false));

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmStructure, fieldName, fieldValue);

		MockLiferayPortletRequest mockLiferayPortletRequest =
			_getMockLiferayPortletRequest(
				ddmFormValues, ddmStructure.getStructureId(), title);

		List<JournalArticle> originalJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addJournalArticle",
			new Class<?>[] {ActionRequest.class}, mockLiferayPortletRequest);

		List<JournalArticle> actualJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			actualJournalArticles.toString(),
			originalJournalArticles.size() + 1, actualJournalArticles.size());

		Assert.assertEquals(
			JournalArticle.class.getName(),
			_portal.getClassName(jsonObject.getLong("classNameId")));

		JournalArticle actualJournalArticle =
			_journalArticleLocalService.getLatestArticle(
				jsonObject.getLong("classPK"));

		Assert.assertEquals(title, actualJournalArticle.getTitle());

		fieldValueValidator.accept(
			_getFieldValue(actualJournalArticle, fieldName));

		Assert.assertEquals(title, jsonObject.getString("title"));
	}

	private void _validateAddStructuredContentStructureWithFieldImage(
			String fieldValue, String expectedFieldValue)
		throws Exception {

		String fieldName = StringUtil.randomString(10);
		String title = StringUtil.randomString(10);

		_validateAddStructuredContentStructureWithField(
			DDMFormFieldType.IMAGE, fieldName, fieldValue, title,
			actualFieldValue -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					actualFieldValue);

				Assert.assertEquals(
					_group.getGroupId(), jsonObject.getLong("groupId"));

				String expectedTitle = title + " - " + fieldName;

				Assert.assertEquals(
					expectedTitle, jsonObject.getString("title"));

				Assert.assertEquals(
					_group.getGroupId(), jsonObject.getLong("groupId"));

				FileEntry fileEntry =
					_dlAppLocalService.getFileEntryByUuidAndGroupId(
						jsonObject.getString("uuid"),
						jsonObject.getLong("groupId"));

				Assert.assertEquals(expectedTitle, fileEntry.getTitle());

				Assert.assertEquals(
					expectedFieldValue,
					Base64.encode(
						FileUtil.getBytes(fileEntry.getContentStream())));
			});
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDM _ddm;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/add_structured_content")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private static class MockActionRequest extends MockLiferayPortletRequest {

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

}