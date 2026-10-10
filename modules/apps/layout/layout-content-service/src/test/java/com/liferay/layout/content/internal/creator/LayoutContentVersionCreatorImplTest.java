/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.internal.creator;

import com.liferay.layout.content.provider.LayoutContentVersionDataProvider;
import com.liferay.layout.content.service.LayoutContentVersionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.InitialRequestSyncUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

/**
 * @author Rubén Pulido
 */
public class LayoutContentVersionCreatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_layoutContentVersionCreatorImpl,
			"_layoutContentVersionDataProvider",
			Mockito.mock(LayoutContentVersionDataProvider.class));
		ReflectionTestUtil.setFieldValue(
			_layoutContentVersionCreatorImpl,
			"_layoutContentVersionLocalService",
			_layoutContentVersionLocalService);
		ReflectionTestUtil.setFieldValue(
			_layoutContentVersionCreatorImpl,
			"_layoutPageTemplateEntryLocalService",
			Mockito.mock(LayoutPageTemplateEntryLocalService.class));
		ReflectionTestUtil.setFieldValue(
			_layoutContentVersionCreatorImpl,
			"_layoutUtilityPageEntryLocalService",
			Mockito.mock(LayoutUtilityPageEntryLocalService.class));
	}

	@Test
	@TestInfo({"LPD-103832", "LPD-109570"})
	public void testCreateLayoutContentVersion() throws Exception {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.isDraftLayout()
		).thenReturn(
			true
		);

		Mockito.when(
			layout.isTypeContent()
		).thenReturn(
			true
		);

		try (MockedStatic<FeatureFlagManagerUtil>
				featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
					FeatureFlagManagerUtil.class);
			MockedStatic<InitialRequestSyncUtil>
				initialRequestSyncUtilMockedStatic = Mockito.mockStatic(
					InitialRequestSyncUtil.class)) {

			featureFlagManagerUtilMockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(
					Mockito.anyLong(), Mockito.eq("LPD-10622"))
			).thenReturn(
				true
			);

			initialRequestSyncUtilMockedStatic.when(
				InitialRequestSyncUtil::isSynced
			).thenReturn(
				false
			);

			_layoutContentVersionCreatorImpl.createLayoutContentVersion(layout);

			_verifyAddLayoutContentVersion(Mockito.never());

			initialRequestSyncUtilMockedStatic.when(
				InitialRequestSyncUtil::isSynced
			).thenReturn(
				true
			);

			_layoutContentVersionCreatorImpl.createLayoutContentVersion(layout);

			_verifyAddLayoutContentVersion(Mockito.times(1));
		}
	}

	private void _verifyAddLayoutContentVersion(
			VerificationMode verificationMode)
		throws Exception {

		Mockito.verify(
			_layoutContentVersionLocalService, verificationMode
		).addLayoutContentVersion(
			Mockito.any(), Mockito.anyLong(), Mockito.any(), Mockito.any(),
			Mockito.anyLong(), Mockito.anyInt()
		);
	}

	private final LayoutContentVersionCreatorImpl
		_layoutContentVersionCreatorImpl =
			new LayoutContentVersionCreatorImpl();
	private final LayoutContentVersionLocalService
		_layoutContentVersionLocalService = Mockito.mock(
			LayoutContentVersionLocalService.class);

}