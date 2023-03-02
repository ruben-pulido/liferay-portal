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

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class CompanyWebIdGroupKeyKeyInfoItemIdentifier
	extends BaseInfoItemIdentifier {

	public static final InfoItemServiceFilter INFO_ITEM_SERVICE_FILTER =
		getInfoItemServiceFilter(
			CompanyWebIdGroupKeyKeyInfoItemIdentifier.class);

	public CompanyWebIdGroupKeyKeyInfoItemIdentifier(
		String companyWebId, String groupKey, String key) {

		_companyWebId = companyWebId;
		_groupKey = groupKey;
		_key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CompanyWebIdGroupKeyKeyInfoItemIdentifier)) {
			return false;
		}

		CompanyWebIdGroupKeyKeyInfoItemIdentifier
			companyWebIdGroupKeyKeyInfoItemIdentifier =
				(CompanyWebIdGroupKeyKeyInfoItemIdentifier)object;

		if (Objects.equals(
				_companyWebId,
				companyWebIdGroupKeyKeyInfoItemIdentifier._companyWebId) &&
			Objects.equals(
				_groupKey,
				companyWebIdGroupKeyKeyInfoItemIdentifier._groupKey) &&
			Objects.equals(
				_key, companyWebIdGroupKeyKeyInfoItemIdentifier._key)) {

			return true;
		}

		return false;
	}

	public String getCompanyWebId() {
		return _companyWebId;
	}

	public String getGroupKey() {
		return _groupKey;
	}

	@Override
	public InfoItemServiceFilter getInfoItemServiceFilter() {
		return INFO_ITEM_SERVICE_FILTER;
	}

	public String getKey() {
		return _key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_companyWebId, _groupKey, _key);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=",
			CompanyWebIdGroupKeyKeyInfoItemIdentifier.class.getName(),
			", _companyWebId=", _companyWebId, ", _groupKey=", _groupKey,
			", _key=", _key, "}");
	}

	private final String _companyWebId;
	private final String _groupKey;
	private final String _key;

}