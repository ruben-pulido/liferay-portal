/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.info.item.provider.tracker;

import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = {})
public class InfoItemFormVariationsProviderServiceTrackerCustomizer
	implements EagerServiceTrackerCustomizer
		<InfoItemFormVariationsProvider<?>, InfoItemFormVariationsProvider<?>> {

	@Override
	public InfoItemFormVariationsProvider<?> addingService(
		ServiceReference<InfoItemFormVariationsProvider<?>> serviceReference) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_bundleContext.getService(serviceReference);

		try {
			_updateClassTypeKey(
				_getItemClassName(
					infoItemFormVariationsProvider, serviceReference),
				infoItemFormVariationsProvider);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update the class type key of the display page " +
					"templates",
				exception);
		}

		return infoItemFormVariationsProvider;
	}

	@Override
	public void modifiedService(
		ServiceReference<InfoItemFormVariationsProvider<?>> serviceReference,
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider) {
	}

	@Override
	public void removedService(
		ServiceReference<InfoItemFormVariationsProvider<?>> serviceReference,
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider) {

		_bundleContext.ungetService(serviceReference);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext,
			(Class<InfoItemFormVariationsProvider<?>>)
				(Class<?>)InfoItemFormVariationsProvider.class,
			null, this);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();

		_bundleContext = null;
	}

	private String _getItemClassName(
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider,
		ServiceReference<InfoItemFormVariationsProvider<?>> serviceReference) {

		String itemClassName = GetterUtil.getString(
			serviceReference.getProperty("item.class.name"));

		if (Validator.isNotNull(itemClassName)) {
			return itemClassName;
		}

		return GenericUtil.getGenericClassName(infoItemFormVariationsProvider);
	}

	private void _updateClassTypeKey(
			String className,
			InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider)
		throws Exception {

		ClassName classNameModel = _classNameLocalService.fetchClassName(
			className);

		if ((classNameModel == null) ||
			(classNameModel.getClassNameId() == 0)) {

			return;
		}

		ActionableDynamicQuery layoutPageTemplateEntryActionableDynamicQuery =
			_layoutPageTemplateEntryLocalService.getActionableDynamicQuery();

		layoutPageTemplateEntryActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"classNameId", classNameModel.getClassNameId()));
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"type",
						LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));
			});
		layoutPageTemplateEntryActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateEntry>)layoutPageTemplateEntry -> {
					if (Validator.isNotNull(
							layoutPageTemplateEntry.getClassTypeKey())) {

						return;
					}

					InfoItemFormVariation infoItemFormVariation =
						infoItemFormVariationsProvider.getInfoItemFormVariation(
							layoutPageTemplateEntry.getGroupId(),
							layoutPageTemplateEntry.getClassTypeKey(),
							String.valueOf(
								layoutPageTemplateEntry.getClassTypeId()));

					if ((infoItemFormVariation == null) ||
						Validator.isNull(
							infoItemFormVariation.getExternalReferenceCode())) {

						return;
					}

					layoutPageTemplateEntry.setClassTypeKey(
						infoItemFormVariation.getExternalReferenceCode());

					_layoutPageTemplateEntryLocalService.
						updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
				});

		layoutPageTemplateEntryActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoItemFormVariationsProviderServiceTrackerCustomizer.class);

	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	private ServiceTrackerList<InfoItemFormVariationsProvider<?>>
		_serviceTrackerList;

}