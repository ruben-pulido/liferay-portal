/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentBatchStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-A")
	public void testPublish() throws Exception {
		FragmentCollection liveFragmentCollection = _addFragmentCollection(
			_liveGroup);

		Group stagingGroup = _enableLocalStaging();

		FragmentCollection stagingFragmentCollection =
			_fragmentCollectionLocalService.
				getFragmentCollectionByExternalReferenceCode(
					liveFragmentCollection.getExternalReferenceCode(),
					stagingGroup.getGroupId());

		Assert.assertEquals(
			liveFragmentCollection.getUuid(),
			stagingFragmentCollection.getUuid());
	}

	private FragmentCollection _addFragmentCollection(Group group)
		throws Exception {

		return _fragmentCollectionLocalService.addFragmentCollection(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private Group _enableLocalStaging() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_liveGroup.getGroupId());

		for (String key :
				new String[] {
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
					PortletDataHandlerKeys.PORTLET_DATA_ALL,
					PortletDataHandlerKeys.PORTLET_SETUP_ALL
				}) {

			serviceContext.setAttribute(
				StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
				Boolean.TRUE.toString());
		}

		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _liveGroup, false, false,
			serviceContext);

		_liveGroup = _groupLocalService.getGroup(_liveGroup.getGroupId());

		return _liveGroup.getStagingGroup();
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@Inject
	private StagingLocalService _stagingLocalService;

}