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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public class FragmentEntryLinkImpl extends FragmentEntryLinkBaseImpl {

	@Override
	public boolean isLatestVersion() throws PortalException {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.getFragmentEntry(
				getFragmentEntryId());

		Date fragmentEntryModifiedDate = fragmentEntry.getModifiedDate();

		return fragmentEntryModifiedDate.before(getLastPropagationDate());
	}

	@Override
	public String getEditableValues(boolean includeDefault) {
		JSONObject editableValuesJSONObject =
			FragmentEntryConfigUtil.getEditableValuesJSONObject(
				getConfiguration(), getEditableValues(), includeDefault);

		if (Validator.isNull(editableValuesJSONObject)) {
			return null;
		}
		else {
			return editableValuesJSONObject.toJSONString();
		}
	}

}