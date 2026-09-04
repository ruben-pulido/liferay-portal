/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.renderer;

import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.renderer.LayoutPreviewRenderer;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.theme.ThemeUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletToken;
import com.liferay.segments.constants.SegmentsWebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutPreviewRenderer.class)
public class LayoutPreviewRendererImpl implements LayoutPreviewRenderer {

	@Override
	public String render(
			Layout layout, Locale locale, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws Exception {

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		ThemeDisplay originalThemeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ThemeDisplay themeDisplay = (ThemeDisplay)originalThemeDisplay.clone();

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());

		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		_initThemeDisplay(themeDisplay, layout);

		long originalClassNameId = layout.getClassNameId();
		Layout originalLayout = (Layout)httpServletRequest.getAttribute(
			WebKeys.LAYOUT);
		LayoutStructure originalLayoutStructure =
			(LayoutStructure)httpServletRequest.getAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE);
		boolean originalPortletDecorate = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(WebKeys.PORTLET_DECORATE));
		long[] originalSegmentsExperienceIds = GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS));
		Locale originalThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		ServiceContext clonedServiceContext =
			(ServiceContext)serviceContext.clone();

		clonedServiceContext.setPlid(layout.getPlid());
		clonedServiceContext.setScopeGroupId(layout.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(clonedServiceContext);

		try {
			LocaleThreadLocal.setThemeDisplayLocale(locale);

			httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				new long[] {segmentsExperienceId});
			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, Boolean.FALSE);

			themeDisplay.setLocale(locale);

			Theme theme = layout.getTheme();

			themeDisplay.setLookAndFeel(theme, layout.getColorScheme());

			themeDisplay.setShowSignInIcon(true);
			themeDisplay.setSignedIn(false);

			User guestUser = _userLocalService.getGuestUser(
				themeDisplay.getCompanyId());

			themeDisplay.setUser(guestUser);

			layout.setClassNameId(0);

			httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			if (segmentsExperienceId != GetterUtil.getLong(
					httpServletRequest.getParameter("segmentsExperienceId"))) {

				DynamicServletRequest dynamicServletRequest =
					new DynamicServletRequest(httpServletRequest);

				dynamicServletRequest.setParameter(
					"segmentsExperienceId",
					String.valueOf(segmentsExperienceId));

				httpServletRequest = dynamicServletRequest;
			}

			layout.includeLayoutContent(
				httpServletRequest, themeDisplay.getResponse());

			Document document = Jsoup.parse(
				ThemeUtil.include(
					ServletContextPool.get(_portal.getServletContextName()),
					httpServletRequest, themeDisplay.getResponse(),
					"portal_normal.ftl", theme, false));

			Element element = document.getElementById("content");

			if (element == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Replacing all body content because theme " +
							theme.getThemeId() +
								" lacks a tag with ID \"content\"");
				}

				element = document.body();
			}

			StringBundler sb = (StringBundler)httpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

			element.html(sb.toString());

			return document.html();
		}
		finally {
			httpServletRequest.setAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE, originalLayoutStructure);
			httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				originalSegmentsExperienceIds);
			httpServletRequest.setAttribute(WebKeys.LAYOUT, originalLayout);
			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, originalPortletDecorate);
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, originalThemeDisplay);

			layout.setClassNameId(originalClassNameId);

			LocaleThreadLocal.setThemeDisplayLocale(originalThemeDisplayLocale);

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _initThemeDisplay(ThemeDisplay themeDisplay, Layout layout)
		throws Exception {

		String pathImage = _portal.getPathImage();
		String pathMain = _portal.getPathMain();

		String companyLogo = pathImage + "/company_logo";

		Company company = themeDisplay.getCompany();

		long companyLogoId = company.getLogoId();

		if (companyLogoId > 0) {
			companyLogo = StringBundler.concat(
				companyLogo, "?img_id=", companyLogoId, "&t=",
				_webServerServletToken.getToken(companyLogoId));
		}

		String layoutSetLogo = null;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (company.isSiteLogo() && layoutSet.isLogo()) {
			long layoutSetLogoId = layoutSet.getLogoId();

			if (layoutSetLogoId > 0) {
				layoutSetLogo = StringBundler.concat(
					pathImage, "/layout_set_logo?img_id=", layoutSetLogoId,
					"&t=", _webServerServletToken.getToken(layoutSetLogoId));

				companyLogo = layoutSetLogo;
			}
		}

		themeDisplay.setCompanyLogo(companyLogo);
		themeDisplay.setLayoutSetLogo(layoutSetLogo);
		themeDisplay.setLayouts(
			ListUtil.filter(
				_layoutService.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID),
				curLayout -> !curLayout.isHidden() && curLayout.isPublished()));
		themeDisplay.setPathContext(_portal.getPathContext());
		themeDisplay.setPathImage(pathImage);
		themeDisplay.setPathMain(pathMain);
		themeDisplay.setRealCompanyLogo(companyLogo);
		themeDisplay.setURLSignIn(
			HttpComponentsUtil.addParameter(
				StringBundler.concat(
					themeDisplay.getPortalURL(), pathMain, "/portal/login"),
				"p_l_id", layout.getPlid()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPreviewRendererImpl.class);

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}