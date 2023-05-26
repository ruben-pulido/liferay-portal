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

import com.liferay.info.exception.InfoFormInvalidGroupException;
import com.liferay.info.exception.InfoFormInvalidLayoutModeException;
import com.liferay.info.exception.InfoItemActionExecutionException;
import com.liferay.info.exception.InfoItemActionExecutionInvalidLayoutModeException;
import com.liferay.info.exception.InfoItemActionExecutionPrincipalException;
import com.liferay.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemActionExecutor;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "path=/portal/execute_info_item_action", service = StrutsAction.class
)
public class ExecuteInfoItemActionStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String actionItemId = ParamUtil.getString(
			httpServletRequest, "actionItemId");
		String actionName = ParamUtil.getString(
			httpServletRequest, "actionName");
		long classNameId =
			ParamUtil.getLong(httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		try {
			if (!Objects.equals(
					Constants.VIEW,
					ParamUtil.getString(httpServletRequest, "p_l_mode"))) {

				throw new InfoFormInvalidLayoutModeException();
			}

			Layout layout = _layoutLocalService.fetchLayout(
				ParamUtil.getLong(httpServletRequest, "plid"));

			if ((layout == null) || layout.isDraftLayout()) {
				throw new InfoItemActionExecutionInvalidLayoutModeException();
			}

			String className = _portal.getClassName(classNameId);

			InfoItemActionExecutor<Object> infoItemActionExecutor =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemActionExecutor.class, className);

			if (infoItemActionExecutor == null) {
				throw new InfoItemActionExecutionException();
			}

			infoItemActionExecutor.executeInfoItemAction(
				new ClassNameClassPKInfoItemIdentifier(className, classPK),
				actionName);
		}
		catch (InfoItemActionExecutionException infoItemActionExecutionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoItemActionExecutionException);
			}

			SessionErrors.add(
				httpServletRequest, actionItemId,
				infoItemActionExecutionException);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoItemActionExecutionException infoItemActionExecutionException =
				new InfoItemActionExecutionException();

			if (exception instanceof PrincipalException) {
				infoItemActionExecutionException =
					new InfoItemActionExecutionPrincipalException();
			}

			SessionErrors.add(
				httpServletRequest, actionItemId,
				infoItemActionExecutionException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExecuteInfoItemActionStrutsAction.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}