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

package com.liferay.structured.content.apio.internal.architect.resource.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ruben Pulido
 */
@RunWith(Arquillian.class)
public class StructuredContentNestedCollectionResourceLanguageTest
	extends BaseStructuredContentNestedCollectionResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		User adminUser = UserTestUtil.getAdminUser(
			TestPropsValues.getCompanyId());

		String siteName = RandomTestUtil.randomString(10);

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), siteName);

		_group = GroupLocalServiceUtil.addGroup(
			adminUser.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null,
			0, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(siteName),
			true, true, ServiceContextTestUtil.getServiceContext());

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), _siteAvailableLocales, Locale.GERMANY);

		_userDefaultLocaleES =
			UserTestUtil.addUser(_group.getGroupId(), LocaleUtil.SPAIN);

		_userDefaultLocaleEN =
			UserTestUtil.addUser(_group.getGroupId(), Locale.US);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);
	}


	@Test
	public void testGetStructuredContentWithAcceptLanguage() throws Exception {
		AcceptLanguage acceptLanguage = () -> Locale.FRANCE;

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		JournalArticle journalArticle = journalArticles.get(0);

//		Assert.assertEquals("a", journalArticle.getTitle());
//		Assert.assertEquals("a", journalArticle.getContent());
//		Assert.assertEquals("a", journalArticle.getContent());
//		Assert.assertEquals("a", journalArticle.getContent());
	}

	@Test
	public void testGetLocaleWithNullPreferredLocale() throws Exception {
		AcceptLanguage acceptLanguage = () -> null;

		Locale locale = getLocale(acceptLanguage, _group.getGroupId());

		Assert.assertEquals(
			PortalUtil.getSiteDefaultLocale(_group.getGroupId()), locale);
	}

	private static final Collection<Locale> _siteAvailableLocales =
		Arrays.asList(Locale.FRANCE, Locale.GERMANY);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@DeleteAfterTestRun
	private JournalArticle _journalArticle;

	@DeleteAfterTestRun
	private User _userDefaultLocaleES;

	@DeleteAfterTestRun
	private User _userDefaultLocaleEN;

}