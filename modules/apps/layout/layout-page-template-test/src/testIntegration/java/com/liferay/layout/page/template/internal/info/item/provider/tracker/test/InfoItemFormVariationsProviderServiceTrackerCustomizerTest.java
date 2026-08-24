/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.info.item.provider.tracker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.test.util.DisplayPageTemplateTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class InfoItemFormVariationsProviderServiceTrackerCustomizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-103532")
	public void testClassTypeKeyIsUpdatedWhenProviderIsRegistered()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			DisplayPageTemplateTestUtil.addDisplayPageTemplate(
				_group.getGroupId(),
				_portal.getClassNameId(JournalArticle.class.getName()), null,
				false, WorkflowConstants.STATUS_APPROVED);

		layoutPageTemplateEntry.setClassTypeId(ddmStructure.getStructureId());
		layoutPageTemplateEntry.setClassTypeKey(StringPool.BLANK);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);

		Assert.assertTrue(
			Validator.isNull(layoutPageTemplateEntry.getClassTypeKey()));

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<InfoItemFormVariationsProvider>
			serviceRegistration = bundleContext.registerService(
				InfoItemFormVariationsProvider.class,
				new TestInfoItemFormVariationsProvider(),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", JournalArticle.class.getName()
				).build());

		try {
			layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			Assert.assertEquals(
				_EXTERNAL_REFERENCE_CODE,
				layoutPageTemplateEntry.getClassTypeKey());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _EXTERNAL_REFERENCE_CODE =
		"TEST_EXTERNAL_REFERENCE_CODE";

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	private class TestInfoItemFormVariationsProvider
		implements InfoItemFormVariationsProvider<Object> {

		@Override
		public InfoItemFormVariation getInfoItemFormVariation(
			long groupId, String formVariationKey) {

			return new InfoItemFormVariation(
				_EXTERNAL_REFERENCE_CODE, groupId, formVariationKey,
				InfoLocalizedValue.singleValue(formVariationKey));
		}

		@Override
		public Collection<InfoItemFormVariation> getInfoItemFormVariations(
			long groupId) {

			return Collections.emptyList();
		}

	}

}