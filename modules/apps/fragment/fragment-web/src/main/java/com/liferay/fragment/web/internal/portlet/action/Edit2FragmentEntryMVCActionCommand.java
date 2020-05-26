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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/edit2_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class Edit2FragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		FragmentEntry fragmentEntry = _fragmentEntryService.fetchFragmentEntry(
			fragmentEntryId);

		long redirectedFragmentEntryId = fragmentEntryId;

		if (WorkflowConstants.STATUS_DRAFT != fragmentEntry.getStatus()) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			FragmentEntry draftFragmentEntry =
				_fragmentEntryService.addFragmentEntry(
					fragmentEntry.getGroupId(),
					fragmentEntry.getFragmentCollectionId(),
					_getFragmentEntryKey(
						fragmentEntry.getGroupId(),
						fragmentEntry.getFragmentEntryKey() + "_draft"),
					fragmentEntry.getName(), fragmentEntry.getCss(),
					fragmentEntry.getHtml(), fragmentEntry.getJs(),
					fragmentEntry.isCacheable(),
					fragmentEntry.getConfiguration(),
					fragmentEntry.getPreviewFileEntryId(),
					fragmentEntry.getType(), WorkflowConstants.STATUS_DRAFT,
					serviceContext);

			redirectedFragmentEntryId = draftFragmentEntry.getFragmentEntryId();
		}

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(actionResponse);

		sendRedirect(
			actionRequest, actionResponse,
			_getRedirectURL(
				liferayPortletResponse, redirectedFragmentEntryId,
				fragmentEntry.getFragmentCollectionId()));
	}

	private String _getFragmentEntryKey(long groupId, String fragmentEntryKey) {
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

		String newFragmentEntryKey = null;

		for (int i = 1;; i++) {
			newFragmentEntryKey = fragmentEntryKey + i;

			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				groupId, newFragmentEntryKey);

			if (fragmentEntry == null) {
				return newFragmentEntryKey;
			}
		}
	}

	private String _getRedirectURL(
		LiferayPortletResponse liferayPortletResponse,
		long fragmentCollectionId, long redirectedFragmentEntryId) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/edit_fragment_entry");
		portletURL.setParameter(
			"fragmentCollectionId", String.valueOf(fragmentCollectionId));
		portletURL.setParameter(
			"fragmentEntryId", String.valueOf(redirectedFragmentEntryId));

		return portletURL.toString();
	}

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

}