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

package com.liferay.info.internal.request.struts;

import com.liferay.info.exception.InfoItemActionErrorMessageException;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.action.executor.InfoItemActionExecutor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "path=/portal/get_info_item_action_error_message",
	service = StrutsAction.class
)
public class GetInfoItemActionErrorMessageStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169992")) {
			return null;
		}

		try {
			InfoItemActionExecutor<Object> infoItemActionExecutor =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemActionExecutor.class,
					_portal.getClassName(
						ParamUtil.getLong(httpServletRequest, "classNameId")));

			if (infoItemActionExecutor == null) {
				throw new InfoItemActionErrorMessageException();
			}

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"message",
					infoItemActionExecutor.getInfoItemActionErrorMessage(
						ParamUtil.getString(httpServletRequest, "fieldId"))
				).toString());
		}
		catch (InfoItemActionErrorMessageException
					infoItemActionErrorMessageException) {

			if (_log.isDebugEnabled()) {
				_log.debug(infoItemActionErrorMessageException);
			}

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					infoItemActionErrorMessageException.getLocalizedMessage(
						httpServletRequest.getLocale())
				).toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoItemActionErrorMessageException
				infoItemActionErrorMessageException =
					new InfoItemActionErrorMessageException();

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					infoItemActionErrorMessageException.getLocalizedMessage(
						httpServletRequest.getLocale())
				).toString());
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInfoItemActionErrorMessageStrutsAction.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Portal _portal;

}