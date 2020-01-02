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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentEntry;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.FragmentEntryResource;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentEntrySerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFragmentEntryResourceTestCase {

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
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_fragmentEntryResource.setContextCompany(testCompany);

		FragmentEntryResource.Builder builder = FragmentEntryResource.builder();

		fragmentEntryResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		FragmentEntry fragmentEntry1 = randomFragmentEntry();

		String json = objectMapper.writeValueAsString(fragmentEntry1);

		FragmentEntry fragmentEntry2 = FragmentEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(fragmentEntry1, fragmentEntry2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		FragmentEntry fragmentEntry = randomFragmentEntry();

		String json1 = objectMapper.writeValueAsString(fragmentEntry);
		String json2 = FragmentEntrySerDes.toJSON(fragmentEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		FragmentEntry fragmentEntry = randomFragmentEntry();

		fragmentEntry.setContent(regex);
		fragmentEntry.setCss(regex);
		fragmentEntry.setDescription(regex);
		fragmentEntry.setHtml(regex);
		fragmentEntry.setJs(regex);
		fragmentEntry.setName(regex);

		String json = FragmentEntrySerDes.toJSON(fragmentEntry);

		Assert.assertFalse(json.contains(regex));

		fragmentEntry = FragmentEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, fragmentEntry.getContent());
		Assert.assertEquals(regex, fragmentEntry.getCss());
		Assert.assertEquals(regex, fragmentEntry.getDescription());
		Assert.assertEquals(regex, fragmentEntry.getHtml());
		Assert.assertEquals(regex, fragmentEntry.getJs());
		Assert.assertEquals(regex, fragmentEntry.getName());
	}

	@Test
	public void testGetFragmentCollectionFragmentEntriesPage()
		throws Exception {

		Page<FragmentEntry> page =
			fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
				testGetFragmentCollectionFragmentEntriesPage_getFragmentCollectionId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long fragmentCollectionId =
			testGetFragmentCollectionFragmentEntriesPage_getFragmentCollectionId();
		Long irrelevantFragmentCollectionId =
			testGetFragmentCollectionFragmentEntriesPage_getIrrelevantFragmentCollectionId();

		if ((irrelevantFragmentCollectionId != null)) {
			FragmentEntry irrelevantFragmentEntry =
				testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
					irrelevantFragmentCollectionId,
					randomIrrelevantFragmentEntry());

			page =
				fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
					irrelevantFragmentCollectionId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFragmentEntry),
				(List<FragmentEntry>)page.getItems());
			assertValid(page);
		}

		FragmentEntry fragmentEntry1 =
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				fragmentCollectionId, randomFragmentEntry());

		FragmentEntry fragmentEntry2 =
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				fragmentCollectionId, randomFragmentEntry());

		page = fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
			fragmentCollectionId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(fragmentEntry1, fragmentEntry2),
			(List<FragmentEntry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFragmentCollectionFragmentEntriesPageWithPagination()
		throws Exception {

		Long fragmentCollectionId =
			testGetFragmentCollectionFragmentEntriesPage_getFragmentCollectionId();

		FragmentEntry fragmentEntry1 =
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				fragmentCollectionId, randomFragmentEntry());

		FragmentEntry fragmentEntry2 =
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				fragmentCollectionId, randomFragmentEntry());

		FragmentEntry fragmentEntry3 =
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				fragmentCollectionId, randomFragmentEntry());

		Page<FragmentEntry> page1 =
			fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
				fragmentCollectionId, Pagination.of(1, 2));

		List<FragmentEntry> fragmentEntries1 =
			(List<FragmentEntry>)page1.getItems();

		Assert.assertEquals(
			fragmentEntries1.toString(), 2, fragmentEntries1.size());

		Page<FragmentEntry> page2 =
			fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
				fragmentCollectionId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<FragmentEntry> fragmentEntries2 =
			(List<FragmentEntry>)page2.getItems();

		Assert.assertEquals(
			fragmentEntries2.toString(), 1, fragmentEntries2.size());

		Page<FragmentEntry> page3 =
			fragmentEntryResource.getFragmentCollectionFragmentEntriesPage(
				fragmentCollectionId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(fragmentEntry1, fragmentEntry2, fragmentEntry3),
			(List<FragmentEntry>)page3.getItems());
	}

	protected FragmentEntry
			testGetFragmentCollectionFragmentEntriesPage_addFragmentEntry(
				Long fragmentCollectionId, FragmentEntry fragmentEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetFragmentCollectionFragmentEntriesPage_getFragmentCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetFragmentCollectionFragmentEntriesPage_getIrrelevantFragmentCollectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetFragmentEntry() throws Exception {
		FragmentEntry postFragmentEntry =
			testGetFragmentEntry_addFragmentEntry();

		FragmentEntry getFragmentEntry = fragmentEntryResource.getFragmentEntry(
			postFragmentEntry.getId());

		assertEquals(postFragmentEntry, getFragmentEntry);
		assertValid(getFragmentEntry);
	}

	protected FragmentEntry testGetFragmentEntry_addFragmentEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry =
			testGraphQLFragmentEntry_addFragmentEntry();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"fragmentEntry",
				new HashMap<String, Object>() {
					{
						put("fragmentEntryId", fragmentEntry.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				fragmentEntry, dataJSONObject.getJSONObject("fragmentEntry")));
	}

	protected FragmentEntry testGraphQLFragmentEntry_addFragmentEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		FragmentEntry fragmentEntry1, FragmentEntry fragmentEntry2) {

		Assert.assertTrue(
			fragmentEntry1 + " does not equal " + fragmentEntry2,
			equals(fragmentEntry1, fragmentEntry2));
	}

	protected void assertEquals(
		List<FragmentEntry> fragmentEntries1,
		List<FragmentEntry> fragmentEntries2) {

		Assert.assertEquals(fragmentEntries1.size(), fragmentEntries2.size());

		for (int i = 0; i < fragmentEntries1.size(); i++) {
			FragmentEntry fragmentEntry1 = fragmentEntries1.get(i);
			FragmentEntry fragmentEntry2 = fragmentEntries2.get(i);

			assertEquals(fragmentEntry1, fragmentEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FragmentEntry> fragmentEntries1,
		List<FragmentEntry> fragmentEntries2) {

		Assert.assertEquals(fragmentEntries1.size(), fragmentEntries2.size());

		for (FragmentEntry fragmentEntry1 : fragmentEntries1) {
			boolean contains = false;

			for (FragmentEntry fragmentEntry2 : fragmentEntries2) {
				if (equals(fragmentEntry1, fragmentEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				fragmentEntries2 + " does not contain " + fragmentEntry1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<FragmentEntry> fragmentEntries, JSONArray jsonArray) {

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(fragmentEntry, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + fragmentEntry, contains);
		}
	}

	protected void assertValid(FragmentEntry fragmentEntry) {
		boolean valid = true;

		if (fragmentEntry.getDateCreated() == null) {
			valid = false;
		}

		if (fragmentEntry.getDateModified() == null) {
			valid = false;
		}

		if (fragmentEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (fragmentEntry.getConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (fragmentEntry.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (fragmentEntry.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("css", additionalAssertFieldName)) {
				if (fragmentEntry.getCss() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (fragmentEntry.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("html", additionalAssertFieldName)) {
				if (fragmentEntry.getHtml() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("js", additionalAssertFieldName)) {
				if (fragmentEntry.getJs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (fragmentEntry.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usageCount", additionalAssertFieldName)) {
				if (fragmentEntry.getUsageCount() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<FragmentEntry> page) {
		boolean valid = false;

		java.util.Collection<FragmentEntry> fragmentEntries = page.getItems();

		int size = fragmentEntries.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		FragmentEntry fragmentEntry1, FragmentEntry fragmentEntry2) {

		if (fragmentEntry1 == fragmentEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getConfiguration(),
						fragmentEntry2.getConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getContent(),
						fragmentEntry2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getCreator(),
						fragmentEntry2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("css", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getCss(), fragmentEntry2.getCss())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getDateCreated(),
						fragmentEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getDateModified(),
						fragmentEntry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getDescription(),
						fragmentEntry2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("html", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getHtml(), fragmentEntry2.getHtml())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getId(), fragmentEntry2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("js", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getJs(), fragmentEntry2.getJs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getName(), fragmentEntry2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usageCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry1.getUsageCount(),
						fragmentEntry2.getUsageCount())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equalsJSONObject(
		FragmentEntry fragmentEntry, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("content", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getContent(),
						jsonObject.getString("content"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("css", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getCss(), jsonObject.getString("css"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("html", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getHtml(),
						jsonObject.getString("html"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("js", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getJs(), jsonObject.getString("js"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getName(),
						jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usageCount", fieldName)) {
				if (!Objects.deepEquals(
						fragmentEntry.getUsageCount(),
						jsonObject.getInt("usageCount"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_fragmentEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_fragmentEntryResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, FragmentEntry fragmentEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("configuration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getContent()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("css")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getCss()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentEntry.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentEntry.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(fragmentEntry.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentEntry.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentEntry.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(fragmentEntry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("html")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getHtml()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("js")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getJs()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentEntry.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("usageCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected FragmentEntry randomFragmentEntry() throws Exception {
		return new FragmentEntry() {
			{
				content = RandomTestUtil.randomString();
				css = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				html = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				js = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				usageCount = RandomTestUtil.randomInt();
			}
		};
	}

	protected FragmentEntry randomIrrelevantFragmentEntry() throws Exception {
		FragmentEntry randomIrrelevantFragmentEntry = randomFragmentEntry();

		return randomIrrelevantFragmentEntry;
	}

	protected FragmentEntry randomPatchFragmentEntry() throws Exception {
		return randomFragmentEntry();
	}

	protected FragmentEntryResource fragmentEntryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFragmentEntryResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.delivery.resource.v1_0.FragmentEntryResource
		_fragmentEntryResource;

}