/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryVersionsRequiredException extends PortalException {

	public FragmentEntryVersionsRequiredException() {
	}

	public FragmentEntryVersionsRequiredException(String msg) {
		super(msg);
	}

	public FragmentEntryVersionsRequiredException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public FragmentEntryVersionsRequiredException(Throwable throwable) {
		super(throwable);
	}

}
