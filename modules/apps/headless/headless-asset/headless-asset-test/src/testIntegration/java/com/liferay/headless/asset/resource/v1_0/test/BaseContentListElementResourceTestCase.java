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

package com.liferay.headless.asset.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.liferay.headless.asset.dto.v1_0.StructuredContent;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
/**
 * @author Javier Gamarra
 * @generated
 */
public abstract class BaseContentListElementResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-asset/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentListElementsOfTypeStructuredContentPage() throws Exception {
		Long contentSpaceId =
			testGetContentListElementsOfTypeStructuredContentPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetContentListElementsPage(
				contentSpaceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentListElementsOfTypeStructuredContentPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentListElementsOfTypeStructuredContentPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent3 =
			testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page1 =
			invokeGetContentListElementsPage(
				contentSpaceId, Pagination.of(1, 2));

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetContentListElementsPage(
				contentSpaceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	protected StructuredContent
		testGetContentListElementsOfTypeStructuredContentPage_addStructuredContent(
				Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentListElementsOfTypeStructuredContentPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Page<StructuredContent> invokeGetContentListElementsPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/asset-lists/{asset-list-id}/assets", contentSpaceId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response invokeGetContentListElementsPageResponse(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/asset-lists/{asset-list-id}/assets", contentSpaceId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (StructuredContent structuredContent1 : structuredContents1) {
			boolean contains = false;

			for (StructuredContent structuredContent2 : structuredContents2) {
				if (equals(structuredContent1, structuredContent2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContents2 + " does not contain " + structuredContent1,
				contains);
		}
	}

	protected void assertValid(StructuredContent structuredContent) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		Collection<StructuredContent> structuredContents = page.getItems();

		int size = structuredContents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		return false;
	}

	protected StructuredContent randomStructuredContent() {
		return new StructuredContent() {
			{
				contentSpace = RandomTestUtil.randomLong();
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				hasComments = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				lastReviewed = RandomTestUtil.nextDate();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	private URL _resourceURL;

}