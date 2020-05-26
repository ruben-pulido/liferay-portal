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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.web.internal.handler.FragmentEntryExceptionRequestHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/publish_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class PublishFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		try {
			FragmentEntry fragmentEntry =
				_fragmentEntryService.fetchFragmentEntry(fragmentEntryId);

			if (fragmentEntry == null) {

				// TODO There is no draft. Handle

			}

			FragmentEntry publishedFragmentEntry = null;

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			if (fragmentEntry.getPublishedFragmentEntryId() == 0) {

				// The fragment has never been published

				publishedFragmentEntry =
					_fragmentEntryService.addFragmentEntry(
						fragmentEntry.getGroupId(),
						fragmentEntry.getFragmentCollectionId(),

						// TODO Needs to be unique for the site

						_getFragmentEntryKey(
							fragmentEntry.getGroupId(),
							fragmentEntry.getFragmentEntryKey()),
						fragmentEntry.getName(), fragmentEntry.getCss(),
						fragmentEntry.getHtml(),
						fragmentEntry.getJs(), fragmentEntry.isCacheable(),
						fragmentEntry.getConfiguration(),
						fragmentEntry.getPreviewFileEntryId(),
						fragmentEntry.getType(),
						WorkflowConstants.STATUS_APPROVED,
						serviceContext);
			}
			else {

				// The fragment has already been published

				// TODO Update and delete need to happen in a transaction

				publishedFragmentEntry =
					_fragmentEntryService.updateFragmentEntry(
						fragmentEntry.getPublishedFragmentEntryId(),
						fragmentEntry.getName(), fragmentEntry.getCss(),
						fragmentEntry.getHtml(), fragmentEntry.getJs(),
						fragmentEntry.isCacheable(),
						fragmentEntry.getConfiguration(),
						WorkflowConstants.STATUS_APPROVED);

				_fragmentEntryService.deleteFragmentEntry(fragmentEntryId);
			}

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL",
				getRedirectURL(actionResponse, publishedFragmentEntry));

			// TODO ??

			if (SessionErrors.contains(actionRequest, "fragmentNameInvalid")) {
				addSuccessMessage(actionRequest, actionResponse);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException portalException) {

			// TODO ??

			SessionErrors.add(actionRequest, "fragmentNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_fragmentEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	protected String getRedirectURL(
		ActionResponse actionResponse, FragmentEntry fragmentEntry) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entries");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentEntry.getFragmentCollectionId()));

		return portletURL.toString();
	}

	private String _getFragmentEntryKey(long groupId, String fragmentEntryKey) {
		String newFragmentEntryKey = null;

		if (fragmentEntryKey == null) {
			fragmentEntryKey = StringPool.BLANK;
		}
		else {
			fragmentEntryKey = fragmentEntryKey.trim();
			fragmentEntryKey = StringUtil.toLowerCase(fragmentEntryKey);
		}

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry == null) {
			return fragmentEntryKey;
		}

		for (int i = 1;; i++) {
			newFragmentEntryKey = fragmentEntryKey + i;

			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				groupId, newFragmentEntryKey);

			if (fragmentEntry == null) {
				return newFragmentEntryKey;
			}
		}
	}

	@Reference
	private FragmentEntryExceptionRequestHandler
		_fragmentEntryExceptionRequestHandler;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

}