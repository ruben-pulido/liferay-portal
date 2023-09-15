/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.instance.lifecycle;

import com.liferay.layout.content.LayoutContentProvider;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class LayoutLocalizationPortalInstanceLifecycleListener
	extends InitialRequestPortalInstanceLifecycleListener {

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {

		// TODO Change this to dynamic query to avoid memory and performance
		// problems. This is just a proof of concept to verify that page can
		// be rendered correctly at this time

		List<Layout> layouts = _layoutLocalService.getLayouts(companyId);

		for (Layout layout : layouts) {
			_processLayout(layout);
		}
	}

	private void _processLayout(Layout layout) {
		List<LayoutLocalization> layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				layout.getPlid());

		if (layout.isTypeContent() && layoutLocalizations.isEmpty() &&
			layout.isPublished()) {

			try (AutoCloseable autoCloseable =
					_layoutServiceContextHelper.getServiceContextAutoCloseable(
						layout)) {

				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

				for (Locale locale :
						_language.getAvailableLocales(layout.getGroupId())) {

					_layoutLocalizationLocalService.updateLayoutLocalization(
						_layoutContentProvider.getLayoutContent(
							themeDisplay.getRequest(),
							themeDisplay.getResponse(), layout, locale),
						LocaleUtil.toLanguageId(locale), layout.getPlid(),
						serviceContext);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to add LayoutLocalization for plid " +
							layout.getPlid(),
						exception);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLocalizationPortalInstanceLifecycleListener.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutContentProvider _layoutContentProvider;

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

}