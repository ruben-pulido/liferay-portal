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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentCollection;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.FragmentCollectionResource;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentCollectionSerDes;
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
public abstract class BaseFragmentCollectionResourceTestCase {

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

		_fragmentCollectionResource.setContextCompany(testCompany);

		FragmentCollectionResource.Builder builder =
			FragmentCollectionResource.builder();

		fragmentCollectionResource = builder.locale(
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

		FragmentCollection fragmentCollection1 = randomFragmentCollection();

		String json = objectMapper.writeValueAsString(fragmentCollection1);

		FragmentCollection fragmentCollection2 = FragmentCollectionSerDes.toDTO(
			json);

		Assert.assertTrue(equals(fragmentCollection1, fragmentCollection2));
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

		FragmentCollection fragmentCollection = randomFragmentCollection();

		String json1 = objectMapper.writeValueAsString(fragmentCollection);
		String json2 = FragmentCollectionSerDes.toJSON(fragmentCollection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		FragmentCollection fragmentCollection = randomFragmentCollection();

		fragmentCollection.setDescription(regex);
		fragmentCollection.setName(regex);

		String json = FragmentCollectionSerDes.toJSON(fragmentCollection);

		Assert.assertFalse(json.contains(regex));

		fragmentCollection = FragmentCollectionSerDes.toDTO(json);

		Assert.assertEquals(regex, fragmentCollection.getDescription());
		Assert.assertEquals(regex, fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollection() throws Exception {
		FragmentCollection postFragmentCollection =
			testGetFragmentCollection_addFragmentCollection();

		FragmentCollection getFragmentCollection =
			fragmentCollectionResource.getFragmentCollection(
				postFragmentCollection.getId());

		assertEquals(postFragmentCollection, getFragmentCollection);
		assertValid(getFragmentCollection);
	}

	protected FragmentCollection
			testGetFragmentCollection_addFragmentCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFragmentCollection() throws Exception {
		FragmentCollection fragmentCollection =
			testGraphQLFragmentCollection_addFragmentCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"fragmentCollection",
				new HashMap<String, Object>() {
					{
						put("fragmentCollectionId", fragmentCollection.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				fragmentCollection,
				dataJSONObject.getJSONObject("fragmentCollection")));
	}

	@Test
	public void testGetSiteFragmentCollectionsPage() throws Exception {
		Page<FragmentCollection> page =
			fragmentCollectionResource.getSiteFragmentCollectionsPage(
				testGetSiteFragmentCollectionsPage_getSiteId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteFragmentCollectionsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteFragmentCollectionsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			FragmentCollection irrelevantFragmentCollection =
				testGetSiteFragmentCollectionsPage_addFragmentCollection(
					irrelevantSiteId, randomIrrelevantFragmentCollection());

			page = fragmentCollectionResource.getSiteFragmentCollectionsPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFragmentCollection),
				(List<FragmentCollection>)page.getItems());
			assertValid(page);
		}

		FragmentCollection fragmentCollection1 =
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				siteId, randomFragmentCollection());

		FragmentCollection fragmentCollection2 =
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				siteId, randomFragmentCollection());

		page = fragmentCollectionResource.getSiteFragmentCollectionsPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(fragmentCollection1, fragmentCollection2),
			(List<FragmentCollection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteFragmentCollectionsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteFragmentCollectionsPage_getSiteId();

		FragmentCollection fragmentCollection1 =
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				siteId, randomFragmentCollection());

		FragmentCollection fragmentCollection2 =
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				siteId, randomFragmentCollection());

		FragmentCollection fragmentCollection3 =
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				siteId, randomFragmentCollection());

		Page<FragmentCollection> page1 =
			fragmentCollectionResource.getSiteFragmentCollectionsPage(
				siteId, Pagination.of(1, 2));

		List<FragmentCollection> fragmentCollections1 =
			(List<FragmentCollection>)page1.getItems();

		Assert.assertEquals(
			fragmentCollections1.toString(), 2, fragmentCollections1.size());

		Page<FragmentCollection> page2 =
			fragmentCollectionResource.getSiteFragmentCollectionsPage(
				siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<FragmentCollection> fragmentCollections2 =
			(List<FragmentCollection>)page2.getItems();

		Assert.assertEquals(
			fragmentCollections2.toString(), 1, fragmentCollections2.size());

		Page<FragmentCollection> page3 =
			fragmentCollectionResource.getSiteFragmentCollectionsPage(
				siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				fragmentCollection1, fragmentCollection2, fragmentCollection3),
			(List<FragmentCollection>)page3.getItems());
	}

	protected FragmentCollection
			testGetSiteFragmentCollectionsPage_addFragmentCollection(
				Long siteId, FragmentCollection fragmentCollection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteFragmentCollectionsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteFragmentCollectionsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteFragmentCollectionsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"fragmentCollections",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject fragmentCollectionsJSONObject = dataJSONObject.getJSONObject(
			"fragmentCollections");

		Assert.assertEquals(0, fragmentCollectionsJSONObject.get("totalCount"));

		FragmentCollection fragmentCollection1 =
			testGraphQLFragmentCollection_addFragmentCollection();
		FragmentCollection fragmentCollection2 =
			testGraphQLFragmentCollection_addFragmentCollection();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		fragmentCollectionsJSONObject = dataJSONObject.getJSONObject(
			"fragmentCollections");

		Assert.assertEquals(2, fragmentCollectionsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(fragmentCollection1, fragmentCollection2),
			fragmentCollectionsJSONObject.getJSONArray("items"));
	}

	protected FragmentCollection
			testGraphQLFragmentCollection_addFragmentCollection()
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
		FragmentCollection fragmentCollection1,
		FragmentCollection fragmentCollection2) {

		Assert.assertTrue(
			fragmentCollection1 + " does not equal " + fragmentCollection2,
			equals(fragmentCollection1, fragmentCollection2));
	}

	protected void assertEquals(
		List<FragmentCollection> fragmentCollections1,
		List<FragmentCollection> fragmentCollections2) {

		Assert.assertEquals(
			fragmentCollections1.size(), fragmentCollections2.size());

		for (int i = 0; i < fragmentCollections1.size(); i++) {
			FragmentCollection fragmentCollection1 = fragmentCollections1.get(
				i);
			FragmentCollection fragmentCollection2 = fragmentCollections2.get(
				i);

			assertEquals(fragmentCollection1, fragmentCollection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FragmentCollection> fragmentCollections1,
		List<FragmentCollection> fragmentCollections2) {

		Assert.assertEquals(
			fragmentCollections1.size(), fragmentCollections2.size());

		for (FragmentCollection fragmentCollection1 : fragmentCollections1) {
			boolean contains = false;

			for (FragmentCollection fragmentCollection2 :
					fragmentCollections2) {

				if (equals(fragmentCollection1, fragmentCollection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				fragmentCollections2 + " does not contain " +
					fragmentCollection1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<FragmentCollection> fragmentCollections, JSONArray jsonArray) {

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(fragmentCollection, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + fragmentCollection,
				contains);
		}
	}

	protected void assertValid(FragmentCollection fragmentCollection) {
		boolean valid = true;

		if (fragmentCollection.getDateCreated() == null) {
			valid = false;
		}

		if (fragmentCollection.getDateModified() == null) {
			valid = false;
		}

		if (fragmentCollection.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				fragmentCollection.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (fragmentCollection.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (fragmentCollection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (fragmentCollection.getName() == null) {
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

	protected void assertValid(Page<FragmentCollection> page) {
		boolean valid = false;

		java.util.Collection<FragmentCollection> fragmentCollections =
			page.getItems();

		int size = fragmentCollections.size();

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
		FragmentCollection fragmentCollection1,
		FragmentCollection fragmentCollection2) {

		if (fragmentCollection1 == fragmentCollection2) {
			return true;
		}

		if (!Objects.equals(
				fragmentCollection1.getSiteId(),
				fragmentCollection2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getCreator(),
						fragmentCollection2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getDateCreated(),
						fragmentCollection2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getDateModified(),
						fragmentCollection2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getDescription(),
						fragmentCollection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getId(),
						fragmentCollection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection1.getName(),
						fragmentCollection2.getName())) {

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
		FragmentCollection fragmentCollection, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						fragmentCollection.getName(),
						jsonObject.getString("name"))) {

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

		if (!(_fragmentCollectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_fragmentCollectionResource;

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
		EntityField entityField, String operator,
		FragmentCollection fragmentCollection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
							fragmentCollection.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentCollection.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(fragmentCollection.getDateCreated()));
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
							fragmentCollection.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							fragmentCollection.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(fragmentCollection.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentCollection.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(fragmentCollection.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
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

	protected FragmentCollection randomFragmentCollection() throws Exception {
		return new FragmentCollection() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected FragmentCollection randomIrrelevantFragmentCollection()
		throws Exception {

		FragmentCollection randomIrrelevantFragmentCollection =
			randomFragmentCollection();

		randomIrrelevantFragmentCollection.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantFragmentCollection;
	}

	protected FragmentCollection randomPatchFragmentCollection()
		throws Exception {

		return randomFragmentCollection();
	}

	protected FragmentCollectionResource fragmentCollectionResource;
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
		BaseFragmentCollectionResourceTestCase.class);

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
	private
		com.liferay.headless.delivery.resource.v1_0.FragmentCollectionResource
			_fragmentCollectionResource;

}