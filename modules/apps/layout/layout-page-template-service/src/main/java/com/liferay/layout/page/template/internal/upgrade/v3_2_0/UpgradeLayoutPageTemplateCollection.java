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

package com.liferay.layout.page.template.internal.upgrade.v3_2_0;

import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateCollectionTable;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Jürgen Kappler
 */
public class UpgradeLayoutPageTemplateCollection extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		upgradeLayoutPageTemplateCollectionKey();
	}

	protected void upgradeLayoutPageTemplateCollectionKey() {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select layoutPageTemplateCollectionId, name from " +
					"LayoutPageTemplateCollection");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateCollection set " +
						"lptCollectionKey = ? where " +
							"layoutPageTemplateCollectionId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateCollectionId = rs.getLong(
					"layoutPageTemplateCollectionId");

				String name = rs.getString("name");

				ps.setString(1, _generateLayoutPageTemplateCollectionKey(name));

				ps.setLong(2, layoutPageTemplateCollectionId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			LayoutPageTemplateCollectionTable.class,
			new AlterTableAddColumn("lptCollectionKey VARCHAR(75)"));
	}

	private String _generateLayoutPageTemplateCollectionKey(String name) {
		String layoutPageTemplateCollectionKey = StringUtil.toLowerCase(
			name.trim());

		return StringUtil.replace(
			layoutPageTemplateCollectionKey, CharPool.SPACE, CharPool.DASH);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutPageTemplateCollection.class);

}