/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import java.util.List;

/**
 * @author Rubén Pulido
 */
public class TypedStagedModelType extends StagedModelType {

	public TypedStagedModelType(String className, List<String> types) {
		super(className, StagedModelType.REFERRER_CLASS_NAME_ALL);

		_types = types;
	}

	public List<String> getTypes() {
		return _types;
	}

	private final List<String> _types;

}