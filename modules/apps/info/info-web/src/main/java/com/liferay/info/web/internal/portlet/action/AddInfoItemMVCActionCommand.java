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

package com.liferay.info.web.internal.portlet.action;

import com.liferay.info.constants.InfoPortletKeys;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.info.web.internal.helper.InfoRequestFieldValuesProviderHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + InfoPortletKeys.INFO_ITEM,
		"mvc.command.name=/not_used/info/add_info_item"
	},
	service = MVCActionCommand.class
)
public class AddInfoItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			_portal.getHttpServletRequest(actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		try {
			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class,
					_portal.getClassName(
						ParamUtil.getLong(actionRequest, "classNameId")));

			infoItemCreator.createFromInfoItemFieldValues(
				InfoItemFieldValues.builder(
				).infoFieldValues(
					_infoRequestFieldValuesProviderHelper.getInfoFieldValues(
						httpServletRequest)
				).build());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Info item validation failed", infoFormValidationException);
			}

			SessionErrors.add(
				originalHttpServletRequest,
				infoFormValidationException.getInfoFieldUniqueId(),
				infoFormValidationException);

			httpServletResponse.sendRedirect(
				httpServletRequest.getHeader(HttpHeaders.REFERER));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to create info item", exception);
			}
		}

		httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
	}

	@Activate
	@Modified
	protected void activate() {
		_infoRequestFieldValuesProviderHelper =
			new InfoRequestFieldValuesProviderHelper(_infoItemServiceTracker);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInfoItemMVCActionCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private volatile InfoRequestFieldValuesProviderHelper
		_infoRequestFieldValuesProviderHelper;

	@Reference
	private Portal _portal;

}