/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.blogs.web.internal.info.item.creator;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.web.internal.info.item.BlogsEntryInfoItemFields;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rubén Pulido
 */
@Component(enabled = true, immediate = true, service = InfoItemCreator.class)
public class BlogsEntryInfoItemCreator implements InfoItemCreator<BlogsEntry> {

	@Override
	public BlogsEntry createFromInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues)
		throws InfoFormException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		try {
			_portletResourcePermission.check(
				themeDisplay.getPermissionChecker(),
				serviceContext.getScopeGroupId(), ActionKeys.ADD_ENTRY);
		}
		catch (PrincipalException principalException) {
			throw new InfoFormException();
		}

		InfoFieldValue<Object> contentInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				BlogsEntryInfoItemFields.contentInfoField.getName());

		if (contentInfoFieldValue == null) {
			return null;
		}

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				BlogsEntryInfoItemFields.titleInfoField.getName());

		if (titleInfoFieldValue == null) {
			return null;
		}

		Date displayDate = null;

		InfoFieldValue<Object> displayDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				BlogsEntryInfoItemFields.displayDateInfoField.getName());

		if (displayDateInfoFieldValue != null) {
			displayDate = (Date)displayDateInfoFieldValue.getValue(
				themeDisplay.getLocale());
		}

		try {
			BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
				themeDisplay.getUserId(),
				(String)titleInfoFieldValue.getValue(themeDisplay.getLocale()),
				(String)contentInfoFieldValue.getValue(
					themeDisplay.getLocale()),
				displayDate, serviceContext);

			InfoFieldValue<Object> descriptionInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue(
					BlogsEntryInfoItemFields.descriptionInfoField.getName());

			if (descriptionInfoFieldValue != null) {
				blogsEntry.setDescription(
					(String)descriptionInfoFieldValue.getValue(
						themeDisplay.getLocale()));
			}

			InfoFieldValue<Object> subtitleInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue(
					BlogsEntryInfoItemFields.subtitleInfoField.getName());

			if (subtitleInfoFieldValue != null) {
				blogsEntry.setSubtitle(
					(String)subtitleInfoFieldValue.getValue(
						themeDisplay.getLocale()));
			}

			return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw new InfoFormException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryInfoItemCreator.class);

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

}