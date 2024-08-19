/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0_0;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.odata.sort.SortParserProvider;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
import com.liferay.portal.tools.rest.builder.test.dto.v1_0_0.TestEntity;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.TestEntityResource;
========
import com.liferay.portal.tools.rest.builder.test.dto.v1_0_0.TestObject;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.TestObjectResource;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@javax.ws.rs.Path("/1.0.0")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
public abstract class BaseTestEntityResourceImpl
	implements EntityModelResource, TestEntityResource,
			   VulcanBatchEngineTaskItemDelegate<TestEntity> {
========
public abstract class BaseTestObjectResourceImpl
	implements EntityModelResource, TestObjectResource,
			   VulcanBatchEngineTaskItemDelegate<TestObject> {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/reserved-word'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.tags.Tags(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
========
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/reserved-word")
	@javax.ws.rs.POST
	@Override
	public Response postReservedWord(Boolean booleanValue) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-entities'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-entities")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<TestEntity> getTestEntitiesPage() throws Exception {
========
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-objects'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-objects")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<TestObject> getTestObjectsPage() throws Exception {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/test-entities/export-batch'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "contentType"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fieldNames"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/test-entities/export-batch")
========
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/test-objects' -d $'{"dateCreated": ___, "dateModified": ___, "description": ___, "documentId": ___, "jsonProperty": ___, "name": ___, "nestedTestObject": ___, "self": ___, "testObjects": ___, "type": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/test-objects")
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	public Response postTestEntitiesPageExportBatch(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("callbackURL")
			String callbackURL,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.DefaultValue("JSON")
			@javax.ws.rs.QueryParam("contentType")
			String contentType,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("fieldNames")
			String fieldNames)
		throws Exception {

		vulcanBatchEngineExportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineExportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineExportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineExportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineExportTaskResource.setContextUser(contextUser);
		vulcanBatchEngineExportTaskResource.setGroupLocalService(
			groupLocalService);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineExportTaskResource.postExportTask(
				TestEntity.class.getName(), callbackURL, contentType,
				fieldNames)
		).build();
========
	public TestObject postTestObject(TestObject testObject) throws Exception {
		return new TestObject();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	}

	/**
	 * Invoke this method with the command line:
	 *
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/test-entities' -d $'{"dateCreated": ___, "dateModified": ___, "description": ___, "documentId": ___, "jsonProperty": ___, "name": ___, "nestedTestEntity": ___, "self": ___, "testEntities": ___, "type": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/test-entities")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public TestEntity postTestEntity(TestEntity testEntity) throws Exception {
		return null;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/test-entities/batch'  -u 'test@liferay.com:test'
========
	 * curl -X 'POST' 'http://localhost:8080/o/test/1.0.0/test-objects/batch'  -u 'test@liferay.com:test'
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "callbackURL"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/test-entities/batch")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postTestEntityBatch(
========
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
	)
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Path("/test-objects/batch")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces("application/json")
	@Override
	public Response postTestObjectBatch(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("callbackURL")
			String callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.postImportTask(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
				TestEntity.class.getName(), callbackURL, null, object)
========
				TestObject.class.getName(), callbackURL, null, object)
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-entities/count'  -u 'test@liferay.com:test'
========
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-objects/count'  -u 'test@liferay.com:test'
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves the count."
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-entities/count")
	@javax.ws.rs.Produces("text/plain")
	@Override
	public Integer getTestEntityCount() throws Exception {
========
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-objects/count")
	@javax.ws.rs.Produces("text/plain")
	@Override
	public Integer getTestObjectCount() throws Exception {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
		return 0;
	}

	/**
	 * Invoke this method with the command line:
	 *
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-entities/{testEntityId}'  -u 'test@liferay.com:test'
========
	 * curl -X 'GET' 'http://localhost:8080/o/test/1.0.0/test-objects/{testObjectId}'  -u 'test@liferay.com:test'
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	 */
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
				name = "testEntityId"
========
				name = "testObjectId"
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestEntity")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-entities/{testEntityId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public TestEntity getTestEntity(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("testEntityId")
			Long testEntityId)
		throws Exception {

		return null;
========
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "TestObject")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/test-objects/{testObjectId}")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public TestObject getTestObject(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("testObjectId")
			Long testObjectId)
		throws Exception {

		return new TestObject();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			Collection<TestEntity> testEntities,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeFunction<TestEntity, TestEntity, Exception>
			testEntityUnsafeFunction = null;
========
			Collection<TestObject> testObjects,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeFunction<TestObject, TestObject, Exception>
			testObjectUnsafeFunction = null;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java

		String createStrategy = (String)parameters.getOrDefault(
			"createStrategy", "INSERT");

		if (StringUtil.equalsIgnoreCase(createStrategy, "INSERT")) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			testEntityUnsafeFunction = testEntity -> postTestEntity(testEntity);
		}

		if (testEntityUnsafeFunction == null) {
			throw new NotSupportedException(
				"Create strategy \"" + createStrategy +
					"\" is not supported for TestEntity");
========
			testObjectUnsafeFunction = testObject -> postTestObject(testObject);
		}

		if (testObjectUnsafeFunction == null) {
			throw new NotSupportedException(
				"Create strategy \"" + createStrategy +
					"\" is not supported for TestObject");
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
		}

		if (contextBatchUnsafeBiConsumer != null) {
			contextBatchUnsafeBiConsumer.accept(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
				testEntities, testEntityUnsafeFunction);
		}
		else if (contextBatchUnsafeConsumer != null) {
			contextBatchUnsafeConsumer.accept(
				testEntities, testEntityUnsafeFunction::apply);
		}
		else {
			for (TestEntity testEntity : testEntities) {
				testEntityUnsafeFunction.apply(testEntity);
========
				testObjects, testObjectUnsafeFunction);
		}
		else if (contextBatchUnsafeConsumer != null) {
			contextBatchUnsafeConsumer.accept(
				testObjects, testObjectUnsafeFunction::apply);
		}
		else {
			for (TestObject testObject : testObjects) {
				testObjectUnsafeFunction.apply(testObject);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			}
		}
	}

	@Override
	public void delete(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			Collection<TestEntity> testEntities,
========
			Collection<TestObject> testObjects,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			Map<String, Serializable> parameters)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	public Set<String> getAvailableCreateStrategies() {
		return SetUtil.fromArray("INSERT");
	}

	public Set<String> getAvailableUpdateStrategies() {
		return SetUtil.fromArray();
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	public String getResourceName() {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		return "TestEntity";
========
		return "TestObject";
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	}

	public String getVersion() {
		return "1.0.0";
	}

	@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
	public Page<TestEntity> read(
========
	public Page<TestObject> read(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		return getTestEntitiesPage();
========
		return getTestObjectsPage();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			Collection<TestEntity> testEntities,
========
			Collection<TestObject> testObjects,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			Map<String, Serializable> parameters)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextBatchUnsafeBiConsumer(
		UnsafeBiConsumer
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			<Collection<TestEntity>,
			 UnsafeFunction<TestEntity, TestEntity, Exception>, Exception>
========
			<Collection<TestObject>,
			 UnsafeFunction<TestObject, TestObject, Exception>, Exception>
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
				contextBatchUnsafeBiConsumer) {

		this.contextBatchUnsafeBiConsumer = contextBatchUnsafeBiConsumer;
	}

	public void setContextBatchUnsafeConsumer(
		UnsafeBiConsumer
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
			<Collection<TestEntity>, UnsafeConsumer<TestEntity, Exception>,
========
			<Collection<TestObject>, UnsafeConsumer<TestObject, Exception>,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
			 Exception> contextBatchUnsafeConsumer) {

		this.contextBatchUnsafeConsumer = contextBatchUnsafeConsumer;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setExpressionConvert(
		ExpressionConvert<Filter> expressionConvert) {

		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	public void setSortParserProvider(SortParserProvider sortParserProvider) {
		this.sortParserProvider = sortParserProvider;
	}

	public void setVulcanBatchEngineExportTaskResource(
		VulcanBatchEngineExportTaskResource
			vulcanBatchEngineExportTaskResource) {

		this.vulcanBatchEngineExportTaskResource =
			vulcanBatchEngineExportTaskResource;
	}

	public void setVulcanBatchEngineImportTaskResource(
		VulcanBatchEngineImportTaskResource
			vulcanBatchEngineImportTaskResource) {

		this.vulcanBatchEngineImportTaskResource =
			vulcanBatchEngineImportTaskResource;
	}

	@Override
	public Filter toFilter(
		String filterString, Map<String, List<String>> multivaluedMap) {

		try {
			EntityModel entityModel = getEntityModel(multivaluedMap);

			FilterParser filterParser = filterParserProvider.provide(
				entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			return expressionConvert.convert(
				oDataFilter.getExpression(),
				contextAcceptLanguage.getPreferredLocale(), entityModel);
		}
		catch (Exception exception) {
			_log.error("Invalid filter " + filterString, exception);

			return null;
		}
	}

	@Override
	public Sort[] toSorts(String sortString) {
		if (Validator.isNull(sortString)) {
			return null;
		}

		try {
			SortParser sortParser = sortParserProvider.provide(
				getEntityModel(Collections.emptyMap()));

			if (sortParser == null) {
				return null;
			}

			com.liferay.portal.odata.sort.Sort oDataSort =
				new com.liferay.portal.odata.sort.Sort(
					sortParser.parse(sortString));

			List<SortField> sortFields = oDataSort.getSortFields();

			Sort[] sorts = new Sort[sortFields.size()];

			for (int i = 0; i < sortFields.size(); i++) {
				SortField sortField = sortFields.get(i);

				sorts[i] = new Sort(
					sortField.getSortableFieldName(
						contextAcceptLanguage.getPreferredLocale()),
					!sortField.isAscending());
			}

			return sorts;
		}
		catch (Exception exception) {
			_log.error("Invalid sort " + sortString, exception);

			return new Sort[0];
		}
	}

	protected Map<String, String> addAction(
		String actionName,
		com.liferay.portal.kernel.model.GroupedModel groupedModel,
		String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected <T, R, E extends Throwable> List<R> transform(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] transform(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] transformToArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] transformToLongArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transformToLongArray(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransform(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransform(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransformToArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransformToList(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] unsafeTransformToLongArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransformToLongArray(
			collection, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected UnsafeBiConsumer
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		<Collection<TestEntity>,
		 UnsafeFunction<TestEntity, TestEntity, Exception>, Exception>
			contextBatchUnsafeBiConsumer;
	protected UnsafeBiConsumer
		<Collection<TestEntity>, UnsafeConsumer<TestEntity, Exception>,
========
		<Collection<TestObject>,
		 UnsafeFunction<TestObject, TestObject, Exception>, Exception>
			contextBatchUnsafeBiConsumer;
	protected UnsafeBiConsumer
		<Collection<TestObject>, UnsafeConsumer<TestObject, Exception>,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java
		 Exception> contextBatchUnsafeConsumer;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<Filter> expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected SortParserProvider sortParserProvider;
	protected VulcanBatchEngineExportTaskResource
		vulcanBatchEngineExportTaskResource;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

	private static final com.liferay.portal.kernel.log.Log _log =
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestEntityResourceImpl.java
		LogFactoryUtil.getLog(BaseTestEntityResourceImpl.class);
========
		LogFactoryUtil.getLog(BaseTestObjectResourceImpl.class);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/BaseTestObjectResourceImpl.java

}