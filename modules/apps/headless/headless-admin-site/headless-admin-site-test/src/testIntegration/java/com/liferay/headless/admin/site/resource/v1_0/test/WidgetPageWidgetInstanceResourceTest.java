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

		_testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceInstanceable();
		_testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceInstanceableInvalidExternalReferenceCode();
		_testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceNoninstanceable();
		_testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceNoninstanceableInvalidExternalReferenceCode();
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
		return new String[] {
			"externalReferenceCode", "parentSectionId", "position", "widgetName"
		};
	}

	@Override
	protected WidgetPageWidgetInstance randomWidgetPageWidgetInstance()
		throws Exception {

		return _randomWidgetPageWidgetInstance(
			null, true, AssetPublisherPortletKeys.ASSET_PUBLISHER);
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

	private WidgetPageWidgetInstance _randomWidgetPageWidgetInstance(
		String externalReferenceCode, boolean instanceable,
		String portletName) {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			new WidgetPageWidgetInstance();

		String portletId = PortletIdCodec.encode(portletName);

		if (!instanceable) {
			portletId = PortletIdCodec.encode(portletName, 0);
		}

		if (externalReferenceCode == null) {
			externalReferenceCode = portletId;
		}

		widgetPageWidgetInstance.setExternalReferenceCode(
			externalReferenceCode);
		widgetPageWidgetInstance.setParentSectionId("column-1");
		widgetPageWidgetInstance.setPosition(_position++);
		widgetPageWidgetInstance.setWidgetInstanceId(
			PortletIdCodec.decodeInstanceId(portletId));
		widgetPageWidgetInstance.setWidgetName(portletName);

		return widgetPageWidgetInstance;
	}

	private void _testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceInstanceable()
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			randomWidgetPageWidgetInstance();

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

		assertEquals(
			randomWidgetPageWidgetInstance, postWidgetPageWidgetInstance);
		assertValid(postWidgetPageWidgetInstance);
		Assert.assertNotNull(
			postWidgetPageWidgetInstance.getWidgetInstanceId());
		Assert.assertEquals(
			PortletIdCodec.decodeInstanceId(
				postWidgetPageWidgetInstance.getExternalReferenceCode()),
			postWidgetPageWidgetInstance.getWidgetInstanceId());

		_position = 0;
	}

	private void _testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceInstanceableInvalidExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			_randomWidgetPageWidgetInstance(
				RandomTestUtil.randomString(), true,
				AssetPublisherPortletKeys.ASSET_PUBLISHER);

		try {
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}

		_position = 0;
	}

	private void _testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceNoninstanceable()
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			_randomWidgetPageWidgetInstance(
				null, false,
				"com_liferay_cookies_banner_web_portlet_CookiesBanner" +
					"ConfigurationPortlet");

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

		assertEquals(
			randomWidgetPageWidgetInstance, postWidgetPageWidgetInstance);
		assertValid(postWidgetPageWidgetInstance);
		Assert.assertNull(postWidgetPageWidgetInstance.getWidgetInstanceId());
		Assert.assertEquals(
			postWidgetPageWidgetInstance.getWidgetName(),
			postWidgetPageWidgetInstance.getExternalReferenceCode());

		_position = 0;
	}

	private void _testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstanceNoninstanceableInvalidExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance randomWidgetPageWidgetInstance =
			_randomWidgetPageWidgetInstance(
				RandomTestUtil.randomString(), false,
				"com_liferay_cookies_banner_web_portlet_CookiesBanner" +
					"ConfigurationPortlet");

		try {
			testPostSiteSiteByExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}

		_position = 0;
	}

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private int _position;

}