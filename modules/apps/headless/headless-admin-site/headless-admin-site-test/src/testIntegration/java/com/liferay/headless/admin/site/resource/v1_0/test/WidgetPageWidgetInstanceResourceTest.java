/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class WidgetPageWidgetInstanceResourceTest
	extends BaseWidgetPageWidgetInstanceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypePortletLayout(testGroup.getGroupId());
	}

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)_layout.getLayoutType();

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		Assert.assertTrue(layoutTypePortlet.hasPortletId(portletId));

		widgetPageWidgetInstanceResource.
			deleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(), portletId);

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)_layout.getLayoutType();

		Assert.assertFalse(layoutTypePortlet.hasPortletId(portletId));

		try {
			widgetPageWidgetInstanceResource.
				deleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		String portletId = PortletIdCodec.encode(
			postWidgetPageWidgetInstance.getWidgetName(),
			postWidgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance getWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				getSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId);

		assertEquals(postWidgetPageWidgetInstance, getWidgetPageWidgetInstance);
		assertValid(getWidgetPageWidgetInstance);

		try {
			widgetPageWidgetInstanceResource.
				getSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(),
					RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		String portletId = PortletIdCodec.encode(
			postWidgetPageWidgetInstance.getWidgetName(),
			postWidgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance patchWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				patchSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId,
					postWidgetPageWidgetInstance);

		assertEquals(
			postWidgetPageWidgetInstance, patchWidgetPageWidgetInstance);
		assertValid(patchWidgetPageWidgetInstance);

		try {
			widgetPageWidgetInstanceResource.
				patchSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(),
					RandomTestUtil.randomString(),
					randomWidgetPageWidgetInstance());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance()
		throws Exception {

		// KO

		//		_testPostWidgetInstanceFailsERCNullWidgetInstanceIdNotNullWidgetNameNull();
		//		_testPostWidgetInstanceFailsERCNullWidgetInstanceIdNullWidgetNameNull();
		//		_testPostWidgetInstanceFailsERCNullWidgetInstanceIdNullWidgetNameNotNullWidgetNotExist();

		_testPostWidgetInstanceFailsWidgetInstanceableERCNotNullDoesNotContainWidgetNameWidgetInstanceIdNotNullWidgetNameNotNull(); //5x
		_testPostWidgetInstanceFailsWidgetInstanceableERCNotNullDoesNotContainWidgetInstanceIdWidgetInstanceNotNullWidgetNameNotNull(); //5x
		_testPostWidgetInstanceFailsWidgetInstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNullWidgetInstanceAlreadyExists();

		//		_testPostWidgetInstanceFailsWidgetInstanceableERCNullWidgetInstanceNotNullWidgetNameNotNull();
		//		_testPostWidgetInstanceFailsWidgetInstanceableERCNullWidgetInstanceNullWidgetNameNotNull();
		//		_testPostWidgetInstanceFailsWidgetInstanceableERCNullWidgetInstanceNullWidgetNameNull();

		//		_testPostWidgetInstanceFailsWidgetNoninstanceableERCNotNullWithInstanceSeparatorWidgetInstanceNullWidgetNameNotNull();
		//		_testPostWidgetInstanceFailsWidgetNoninstanceableERCNullWidgetInstanceNotNullWidgetNameNotNull();
		//		_testPostWidgetInstanceFailsWidgetNoninstanceableERCNotNullWidgetInstanceNullWidgetNameNotNullWidgetInstanceAlreadyExists();

		// OK

		_testPostWidgetInstanceSucceedsWidgetInstanceableERCNotNullWidgetInstanceIdNotNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetInstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetInstanceableERCNullWidgetInstanceIdNotNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetInstanceableERCNullWidgetInstanceIdNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNullWidgetInstanceIdNullWidgetNameNotNull();
		_testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNullWidgetInstanceIdNullWidgetNameNull();
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			randomWidgetPageWidgetInstance();

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance putWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				putSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId,
					widgetPageWidgetInstance);

		assertEquals(widgetPageWidgetInstance, putWidgetPageWidgetInstance);
		assertValid(putWidgetPageWidgetInstance);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"parentSectionId", "position"};
	}

	@Override
	protected WidgetPageWidgetInstance randomWidgetPageWidgetInstance()
		throws Exception {

		return _randomWidgetPageWidgetInstance(
			null, null, AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	@Override
	protected WidgetPageWidgetInstance
			testGetSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage_addWidgetPageWidgetInstance(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		return widgetPageWidgetInstanceResource.
			postSiteSiteByExternalReferenceCodeSitePageWidgetInstance(
				siteExternalReferenceCode, sitePageExternalReferenceCode,
				widgetPageWidgetInstance);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage_getSitePageExternalReferenceCode()
		throws Exception {

		return _layout.getExternalReferenceCode();
	}

	@Override
	protected WidgetPageWidgetInstance
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		return widgetPageWidgetInstanceResource.
			postSiteSiteByExternalReferenceCodeSitePageWidgetInstance(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(), widgetPageWidgetInstance);
	}

	private String _encodePortletId(String portletName, String instanceId) {
		return portletName + "_INSTANCE_" + instanceId;
	}

	private WidgetPageWidgetInstance _randomWidgetPageWidgetInstance(
		String externalReferenceCode, String widgetInstanceId,
		String widgetName) {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			new WidgetPageWidgetInstance();

		widgetPageWidgetInstance.setExternalReferenceCode(
			externalReferenceCode);
		widgetPageWidgetInstance.setParentSectionId("column-1");
		widgetPageWidgetInstance.setPosition(_position++);
		widgetPageWidgetInstance.setWidgetInstanceId(widgetInstanceId);
		widgetPageWidgetInstance.setWidgetName(widgetName);

		return widgetPageWidgetInstance;
	}

	private void _testPostWidgetInstanceFails(
			String externalReferenceCode, String widgetInstanceId,
			String widgetName, boolean repeat)
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			_randomWidgetPageWidgetInstance(
				externalReferenceCode, widgetInstanceId, widgetName);

		try {
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

			if (repeat) {
				testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
					randomWidgetPageWidgetInstance);
			}

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}

		_position = 0;
	}

	private void _testPostWidgetInstanceFailsWidgetInstanceableERCNotNullDoesNotContainWidgetInstanceIdWidgetInstanceNotNullWidgetNameNotNull()
		throws Exception {

		_testPostWidgetInstanceFails(
			AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_" +
				RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			AssetPublisherPortletKeys.ASSET_PUBLISHER, false);
	}

	private void _testPostWidgetInstanceFailsWidgetInstanceableERCNotNullDoesNotContainWidgetNameWidgetInstanceIdNotNullWidgetNameNotNull()
		throws Exception {

		String widgetInstanceId = RandomTestUtil.randomString();

		_testPostWidgetInstanceFails(
			RandomTestUtil.randomString() + "_INSTANCE_" + widgetInstanceId,
			widgetInstanceId, AssetPublisherPortletKeys.ASSET_PUBLISHER, false);
	}

	private void _testPostWidgetInstanceFailsWidgetInstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNullWidgetInstanceAlreadyExists()
		throws Exception {

		_testPostWidgetInstanceFails(
			AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_" +
				RandomTestUtil.randomString(),
			null, AssetPublisherPortletKeys.ASSET_PUBLISHER, true);
	}

	private void _testPostWidgetInstanceSucceeds(
			boolean instanceable, String expectedExternalReferenceCode,
			String expectedWidgetInstanceId, String expectedWidgetName,
			String externalReferenceCode, String widgetInstanceId,
			String widgetName)
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			_randomWidgetPageWidgetInstance(
				externalReferenceCode, widgetInstanceId, widgetName);

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

		assertEquals(
			randomWidgetPageWidgetInstance, postWidgetPageWidgetInstance);
		assertValid(postWidgetPageWidgetInstance);

		String actualExternalReferenceCode =
			postWidgetPageWidgetInstance.getExternalReferenceCode();

		if (expectedExternalReferenceCode != null) {
			Assert.assertEquals(
				expectedExternalReferenceCode, actualExternalReferenceCode);
		}

		Assert.assertNotNull(actualExternalReferenceCode);

		if (instanceable) {
			Assert.assertNotNull(
				postWidgetPageWidgetInstance.getWidgetInstanceId());
			Assert.assertTrue(
				actualExternalReferenceCode,
				actualExternalReferenceCode.contains("_INSTANCE_"));
			Assert.assertEquals(
				postWidgetPageWidgetInstance.getWidgetInstanceId(),
				actualExternalReferenceCode.split("_INSTANCE_")[1]);
			Assert.assertEquals(
				postWidgetPageWidgetInstance.getWidgetName(),
				actualExternalReferenceCode.split("_INSTANCE_")[0]);
		}
		else {
			Assert.assertNull(
				postWidgetPageWidgetInstance.getWidgetInstanceId());
			Assert.assertFalse(
				actualExternalReferenceCode,
				actualExternalReferenceCode.contains("_INSTANCE_"));
			Assert.assertEquals(
				postWidgetPageWidgetInstance.getWidgetName(),
				actualExternalReferenceCode);
		}

		if (expectedWidgetInstanceId != null) {
			Assert.assertEquals(
				expectedWidgetInstanceId,
				postWidgetPageWidgetInstance.getWidgetInstanceId());
		}

		Assert.assertEquals(
			expectedWidgetName, postWidgetPageWidgetInstance.getWidgetName());

		_position = 0;
	}

	private void _testPostWidgetInstanceSucceedsWidgetInstanceableERCNotNullWidgetInstanceIdNotNullWidgetNameNotNull()
		throws Exception {

		String widgetInstanceId = RandomTestUtil.randomString(12);

		_testPostWidgetInstanceSucceeds(
			true,
			_encodePortletId(
				AssetPublisherPortletKeys.ASSET_PUBLISHER, widgetInstanceId),
			widgetInstanceId, AssetPublisherPortletKeys.ASSET_PUBLISHER, null,
			widgetInstanceId, AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	private void _testPostWidgetInstanceSucceedsWidgetInstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNull()
		throws Exception {

		_testPostWidgetInstanceSucceeds(
			true, null, null, AssetPublisherPortletKeys.ASSET_PUBLISHER, null,
			null, AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	private void _testPostWidgetInstanceSucceedsWidgetInstanceableERCNullWidgetInstanceIdNotNullWidgetNameNotNull()
		throws Exception {

		String widgetInstanceId = RandomTestUtil.randomString(12);

		_testPostWidgetInstanceSucceeds(
			true,
			_encodePortletId(
				AssetPublisherPortletKeys.ASSET_PUBLISHER, widgetInstanceId),
			widgetInstanceId, AssetPublisherPortletKeys.ASSET_PUBLISHER, null,
			widgetInstanceId, AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	private void _testPostWidgetInstanceSucceedsWidgetInstanceableERCNullWidgetInstanceIdNullWidgetNameNotNull()
		throws Exception {

		_testPostWidgetInstanceSucceeds(
			true, null, null, AssetPublisherPortletKeys.ASSET_PUBLISHER, null,
			null, AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	private void _testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNotNullWidgetInstanceIdNullWidgetNameNotNull()
		throws Exception {

		_testPostWidgetInstanceSucceeds(
			false, _NONINSTANCEABLE_PORTLET_NAME, null,
			_NONINSTANCEABLE_PORTLET_NAME, _NONINSTANCEABLE_PORTLET_NAME, null,
			_NONINSTANCEABLE_PORTLET_NAME);
	}

	private void _testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNullWidgetInstanceIdNullWidgetNameNotNull()
		throws Exception {

		_testPostWidgetInstanceSucceeds(
			false, _NONINSTANCEABLE_PORTLET_NAME, null,
			_NONINSTANCEABLE_PORTLET_NAME, null, null,
			_NONINSTANCEABLE_PORTLET_NAME);
	}

	private void _testPostWidgetInstanceSucceedsWidgetNoninstanceableERCNullWidgetInstanceIdNullWidgetNameNull()
		throws Exception {

		_testPostWidgetInstanceSucceeds(
			false, _NONINSTANCEABLE_PORTLET_NAME, null,
			_NONINSTANCEABLE_PORTLET_NAME, null, null, null);
	}

	private static final String _NONINSTANCEABLE_PORTLET_NAME =
		"com_liferay_cookies_banner_web_portlet_CookiesBannerConfiguration" +
			"Portlet";

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private int _position;

}