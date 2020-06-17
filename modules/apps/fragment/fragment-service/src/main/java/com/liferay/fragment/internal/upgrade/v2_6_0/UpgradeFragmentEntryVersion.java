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

package com.liferay.fragment.internal.upgrade.v2_6_0;

import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryTable;
import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryVersionTable;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryVersion extends UpgradeProcess {

	protected String getEntryTableName() {
		return FragmentEntryTable.TABLE_NAME;
	}

	protected String getEntryPrimaryKeyColumnName() {
		return "fragmentEntryId";
	}

	protected String getEntryVersionPrimaryKeyColumnName() {
		return "fragmentEntryVersionId";
	}

	protected String getEntryVersionTableName() {
		return FragmentEntryVersionTable.TABLE_NAME;
	}

	protected String[] getEntryTableColumnNames() {
		List<String> tableColumnNames = new ArrayList<>();

		for (Object[] tableColumn : FragmentEntryTable.TABLE_COLUMNS) {
			String tableColumnName = (String)tableColumn[0];

			if(ArrayUtil.contains({"head", "headId"}))
		}


	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(FragmentEntryVersionTable.TABLE_SQL_CREATE);

		insertIntoFragmentEntryVersion();

		upgradeFragmentEntryVersionCounter();
	}

	protected void insertIntoFragmentEntryVersion() throws Exception {
		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(17);

			sb.append("insert into ");
			sb.append(getEntryVersionTableName());
			sb.append("( ");
			sb.append("fragmentEntryVersionId, version, uuid_, ");
			sb.append("fragmentEntryId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("fragmentCollectionId, fragmentEntryKey, name, css, ");
			sb.append("html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate");
			sb.append(") ");
			sb.append("select ");
			sb.append(getEntryPrimaryKeyColumnName() + " as " + getEntryVersionPrimaryKeyColumnName() + ", ");
			sb.append("1 as version, ");
			sb.append("uuid_, fragmentEntryId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, fragmentCollectionId, fragmentEntryKey, ");
			sb.append("name, css, html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate ");
			sb.append("from ");
			sb.append(getEntryTableName() + " ");
			sb.append("where status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(sb.toString());
		}
	}

	protected Class getEntryVersionClass() {
		return FragmentEntryVersion.class;
	}

	protected void upgradeFragmentEntryVersionCounter() throws Exception {
		Class clazz = getEntryVersionClass();

		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				clazz.getName(), "', max(",
				getEntryVersionPrimaryKeyColumnName(), ") from ",
				getEntryVersionTableName()));
	}

}