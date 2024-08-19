/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0_0.factory;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.tools.rest.builder.test.internal.security.permission.LiberalPermissionChecker;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
import com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.TestEntityResource;
========
import com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.TestObjectResource;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Component(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
	property = "resource.locator.key=/test/1.0.0/TestEntity",
	service = TestEntityResource.Factory.class
)
@Generated("")
public class TestEntityResourceFactoryImpl
	implements TestEntityResource.Factory {

	@Override
	public TestEntityResource.Builder create() {
		return new TestEntityResource.Builder() {

			@Override
			public TestEntityResource build() {
========
	property = "resource.locator.key=/test/1.0.0/TestObject",
	service = TestObjectResource.Factory.class
)
@Generated("")
public class TestObjectResourceFactoryImpl
	implements TestObjectResource.Factory {

	@Override
	public TestObjectResource.Builder create() {
		return new TestObjectResource.Builder() {

			@Override
			public TestObjectResource build() {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				if (_user == null) {
					throw new IllegalArgumentException("User is not set");
				}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
				Function<InvocationHandler, TestEntityResource>
					testEntityResourceProxyProviderFunction =
						ResourceProxyProviderFunctionHolder.
							_testEntityResourceProxyProviderFunction;

				return testEntityResourceProxyProviderFunction.apply(
========
				Function<InvocationHandler, TestObjectResource>
					testObjectResourceProxyProviderFunction =
						ResourceProxyProviderFunctionHolder.
							_testObjectResourceProxyProviderFunction;

				return testObjectResourceProxyProviderFunction.apply(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
					(proxy, method, arguments) -> _invoke(
						method, arguments, _checkPermissions,
						_httpServletRequest, _httpServletResponse,
						_preferredLocale, _uriInfo, _user));
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder checkPermissions(
========
			public TestObjectResource.Builder checkPermissions(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				boolean checkPermissions) {

				_checkPermissions = checkPermissions;

				return this;
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder httpServletRequest(
========
			public TestObjectResource.Builder httpServletRequest(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return this;
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder httpServletResponse(
========
			public TestObjectResource.Builder httpServletResponse(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				HttpServletResponse httpServletResponse) {

				_httpServletResponse = httpServletResponse;

				return this;
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder preferredLocale(
========
			public TestObjectResource.Builder preferredLocale(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				Locale preferredLocale) {

				_preferredLocale = preferredLocale;

				return this;
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder uriInfo(UriInfo uriInfo) {
========
			public TestObjectResource.Builder uriInfo(UriInfo uriInfo) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				_uriInfo = uriInfo;

				return this;
			}

			@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			public TestEntityResource.Builder user(User user) {
========
			public TestObjectResource.Builder user(User user) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				_user = user;

				return this;
			}

			private boolean _checkPermissions = true;
			private HttpServletRequest _httpServletRequest;
			private HttpServletResponse _httpServletResponse;
			private Locale _preferredLocale;
			private UriInfo _uriInfo;
			private User _user;

		};
	}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
	private static Function<InvocationHandler, TestEntityResource>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			TestEntityResource.class.getClassLoader(),
			TestEntityResource.class);

		try {
			Constructor<TestEntityResource> constructor =
				(Constructor<TestEntityResource>)proxyClass.getConstructor(
========
	private static Function<InvocationHandler, TestObjectResource>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			TestObjectResource.class.getClassLoader(),
			TestObjectResource.class);

		try {
			Constructor<TestObjectResource> constructor =
				(Constructor<TestObjectResource>)proxyClass.getConstructor(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private Object _invoke(
			Method method, Object[] arguments, boolean checkPermissions,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale preferredLocale,
			UriInfo uriInfo, User user)
		throws Throwable {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (checkPermissions) {
			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(user));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(
				new LiberalPermissionChecker(user));
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
		TestEntityResource testEntityResource =
			_componentServiceObjects.getService();

		testEntityResource.setContextAcceptLanguage(
========
		TestObjectResource testObjectResource =
			_componentServiceObjects.getService();

		testObjectResource.setContextAcceptLanguage(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
			new AcceptLanguageImpl(httpServletRequest, preferredLocale, user));

		Company company = _companyLocalService.getCompany(user.getCompanyId());

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
		testEntityResource.setContextCompany(company);

		testEntityResource.setContextHttpServletRequest(httpServletRequest);
		testEntityResource.setContextHttpServletResponse(httpServletResponse);
		testEntityResource.setContextUriInfo(uriInfo);
		testEntityResource.setContextUser(user);
		testEntityResource.setExpressionConvert(_expressionConvert);
		testEntityResource.setFilterParserProvider(_filterParserProvider);
		testEntityResource.setGroupLocalService(_groupLocalService);
		testEntityResource.setResourceActionLocalService(
			_resourceActionLocalService);
		testEntityResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		testEntityResource.setRoleLocalService(_roleLocalService);
		testEntityResource.setSortParserProvider(_sortParserProvider);

		try {
			return method.invoke(testEntityResource, arguments);
========
		testObjectResource.setContextCompany(company);

		testObjectResource.setContextHttpServletRequest(httpServletRequest);
		testObjectResource.setContextHttpServletResponse(httpServletResponse);
		testObjectResource.setContextUriInfo(uriInfo);
		testObjectResource.setContextUser(user);
		testObjectResource.setExpressionConvert(_expressionConvert);
		testObjectResource.setFilterParserProvider(_filterParserProvider);
		testObjectResource.setGroupLocalService(_groupLocalService);
		testObjectResource.setResourceActionLocalService(
			_resourceActionLocalService);
		testObjectResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		testObjectResource.setRoleLocalService(_roleLocalService);
		testObjectResource.setSortParserProvider(_sortParserProvider);

		try {
			return method.invoke(testObjectResource, arguments);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}
		finally {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
			_componentServiceObjects.ungetService(testEntityResource);
========
			_componentServiceObjects.ungetService(testObjectResource);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java

			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
	private ComponentServiceObjects<TestEntityResource>
========
	private ComponentServiceObjects<TestObjectResource>
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
		_componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private UserLocalService _userLocalService;

	private static class ResourceProxyProviderFunctionHolder {

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestEntityResourceFactoryImpl.java
		private static final Function<InvocationHandler, TestEntityResource>
			_testEntityResourceProxyProviderFunction =
========
		private static final Function<InvocationHandler, TestObjectResource>
			_testObjectResourceProxyProviderFunction =
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-impl/src/main/java/com/liferay/portal/tools/rest/builder/test/internal/resource/v1_0_0/factory/TestObjectResourceFactoryImpl.java
				_getProxyProviderFunction();

	}

	private class AcceptLanguageImpl implements AcceptLanguage {

		public AcceptLanguageImpl(
			HttpServletRequest httpServletRequest, Locale preferredLocale,
			User user) {

			_httpServletRequest = httpServletRequest;
			_preferredLocale = preferredLocale;
			_user = user;
		}

		@Override
		public List<Locale> getLocales() {
			return Arrays.asList(getPreferredLocale());
		}

		@Override
		public String getPreferredLanguageId() {
			return LocaleUtil.toLanguageId(getPreferredLocale());
		}

		@Override
		public Locale getPreferredLocale() {
			if (_preferredLocale != null) {
				return _preferredLocale;
			}

			if (_httpServletRequest != null) {
				Locale locale = (Locale)_httpServletRequest.getAttribute(
					WebKeys.LOCALE);

				if (locale != null) {
					return locale;
				}
			}

			return _user.getLocale();
		}

		@Override
		public boolean isAcceptAllLanguages() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final Locale _preferredLocale;
		private final User _user;

	}

}