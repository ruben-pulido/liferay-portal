/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.I18nServlet;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayPageFriendlyURLProvider.class)
public class AssetDisplayPageFriendlyURLProviderImpl
	implements AssetDisplayPageFriendlyURLProvider {

	@Override
	public String getDefaultURL(
			InfoItemReference infoItemReference, ThemeDisplay themeDisplay)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				infoItemReference.getClassName());

		if (assetRendererFactory == null) {
			return getFriendlyURL(infoItemReference, themeDisplay);
		}

		try {
			AssetRenderer<?> assetRenderer = null;

			if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

				assetRenderer = assetRendererFactory.getAssetRenderer(
					classPKInfoItemIdentifier.getClassPK());
			}

			if (assetRenderer == null) {
				return getFriendlyURL(infoItemReference, themeDisplay);
			}

			String viewInContextURL = assetRenderer.getURLViewInContext(
				themeDisplay, StringPool.BLANK);

			if (Validator.isNotNull(viewInContextURL)) {
				return viewInContextURL;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return getFriendlyURL(infoItemReference, themeDisplay);
	}

	@Override
	public String getFriendlyURL(
			InfoItemReference infoItemReference, Locale locale,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return _getFriendlyURL(infoItemReference, locale, themeDisplay);
	}

	@Override
	public String getFriendlyURL(
			InfoItemReference infoItemReference, ThemeDisplay themeDisplay)
		throws PortalException {

		return _getFriendlyURL(
			infoItemReference, themeDisplay.getLocale(), themeDisplay);
	}

	@Override
	public String getURL(
			InfoItemReference infoItemReference,
			LayoutPageTemplateEntry layoutPageTemplateEntry, Locale locale,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Group group = themeDisplay.getScopeGroup();

		return StringBundler.concat(
			_portal.getGroupFriendlyURL(
				group.getPublicLayoutSet(), themeDisplay, false, false),
			"/e", layout.getFriendlyURL(locale), StringPool.SLASH,
			_portal.getClassNameId(infoItemReference.getClassName()),
			StringPool.SLASH, _getInfoItemIdentifier(infoItemReference));
	}

	private String _getFriendlyURL(
			InfoItemReference infoItemReference, Locale locale,
			ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(
					_infoSearchClassMapperRegistry.getClassName(
						infoItemReference.getClassName()));

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				infoItemReference);

		if (layoutDisplayPageObjectProvider == null) {
			return null;
		}

		long groupId = themeDisplay.getScopeGroupId();

		if ((layoutDisplayPageObjectProvider.getGroupId() != 0) &&
			(groupId != layoutDisplayPageObjectProvider.getGroupId())) {

			Group layoutDisplayPageObjectGroup = _groupLocalService.getGroup(
				layoutDisplayPageObjectProvider.getGroupId());

			if (!layoutDisplayPageObjectGroup.isCompany() &&
				!layoutDisplayPageObjectGroup.isDepot()) {

				groupId = layoutDisplayPageObjectGroup.getGroupId();
			}
		}

		return _getFriendlyURL(
			groupId, layoutDisplayPageProvider, layoutDisplayPageObjectProvider,
			locale, themeDisplay);
	}

	private String _getFriendlyURL(
			long groupId,
			LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			Locale locale, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return null;
		}

		return StringBundler.concat(
			_getGroupFriendlyURL(groupId, locale, themeDisplay),
			layoutDisplayPageProvider.getURLSeparator(),
			layoutDisplayPageObjectProvider.getURLTitle(locale));
	}

	private String _getGroupFriendlyURL(
			long groupId, Locale locale, ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (locale != null) {
			try {
				ThemeDisplay clonedThemeDisplay =
					(ThemeDisplay)themeDisplay.clone();

				_setThemeDisplayI18n(clonedThemeDisplay, locale);

				return _portal.getGroupFriendlyURL(
					group.getPublicLayoutSet(), clonedThemeDisplay, false,
					false);
			}
			catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new PortalException(cloneNotSupportedException);
			}
		}

		return _portal.getGroupFriendlyURL(
			group.getPublicLayoutSet(), themeDisplay, false, false);
	}

	private String _getI18nPath(Locale locale) {
		Locale defaultLocale = _language.getLocale(locale.getLanguage());

		if (LocaleUtil.equals(defaultLocale, locale)) {
			return StringPool.SLASH + defaultLocale.getLanguage();
		}

		return StringPool.SLASH + locale.toLanguageTag();
	}

	private String _getInfoItemIdentifier(InfoItemReference infoItemReference) {
		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			return String.valueOf(classPKInfoItemIdentifier.getClassPK());
		}

		if (infoItemIdentifier instanceof ERCInfoItemIdentifier) {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			return ercInfoItemIdentifier.getExternalReferenceCode();
		}

		return StringPool.BLANK;
	}

	private void _setThemeDisplayI18n(
		ThemeDisplay themeDisplay, Locale locale) {

		String i18nPath = null;

		Set<String> languageIds = I18nServlet.getLanguageIds();

		int localePrependFriendlyURLStyle = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(),
			PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE);

		if ((languageIds.contains(CharPool.SLASH + locale.toString()) &&
			 (localePrependFriendlyURLStyle == 1) &&
			 !locale.equals(LocaleUtil.getDefault())) ||
			(localePrependFriendlyURLStyle == 2)) {

			i18nPath = _getI18nPath(locale);
		}

		themeDisplay.setI18nLanguageId(locale.toString());
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setLocale(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageFriendlyURLProviderImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}