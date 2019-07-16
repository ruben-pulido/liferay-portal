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

package com.liferay.fragment.entry.processor.freemarker.test;

import static com.liferay.portal.atom.AtomUtil.getCompany;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsConstants;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
public class FragmentEntryProcessorFreemarkerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	public HttpServletRequest createHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(getCompany());
		themeDisplay.setLayout(getLayout());

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			TestPropsValues.getGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(getTheme(layoutSet), null);

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_processedHTML = _getProcessedHTML();
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfiguration()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration.html", "configuration.json");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString("fragment_entry_link_editable_values.json"));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					createHttpServletRequest(), new MockHttpServletResponse(),
					FragmentEntryLinkConstants.EDIT, LocaleUtil.US);

		defaultFragmentEntryProcessorContext.setPreviewClassPK(0);
		defaultFragmentEntryProcessorContext.setPreviewType(
			AssetRendererFactory.TYPE_LATEST_APPROVED);

		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));
	}

	protected Layout getLayout() throws PortalException {
		List<Layout> layouts = _layoutLocalService.getLayouts(
			TestPropsValues.getGroupId(), false, 0, 1, null);

		return layouts.get(0);
	}

	protected Theme getTheme(LayoutSet layoutSet) throws PortalException {
		return _themeLocalService.getTheme(
			TestPropsValues.getCompanyId(), layoutSet.getThemeId());
	}

	private FragmentEntry _addFragmentEntry(
			String htmlFile, String configurationFile)
		throws IOException, PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		return _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"fragment-entry", "Fragment Entry", null,
			_getFileAsString(htmlFile), null,
			_getFileAsString(configurationFile), 0, 0,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private String _getFileAsString(String fileName) throws IOException {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/freemarker/test/" +
				"dependencies/" + fileName);
	}

	private String _getJsonFileAsString(String jsonFileName)
		throws IOException, JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getFileAsString(jsonFileName));

		return jsonObject.toString();
	}

	private String _getProcessedHTML() throws IOException {
		Document document = Jsoup.parseBodyFragment(
			_getFileAsString(
				"processed_fragment_entry_with_configuration.html"));

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	private String _processedHTML;

	@Inject
	private ThemeLocalService _themeLocalService;

}