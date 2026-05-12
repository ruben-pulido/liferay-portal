/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.fragment.internal.model.listener.FragmentEntryVersionModelListener;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = CTEventListener.class)
public class FragmentEntryVersionCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select fragmentEntryId from FragmentEntryVersion where ",
					"ctCollectionId = 0 group by fragmentEntryId having ",
					"count(*) > ",
					FragmentEntryVersionModelListener.MAX_VERSIONS));

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_trimProductionVersions(resultSet.getLong("fragmentEntryId"));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clean up old fragment entry versions after " +
						"publishing CT collection ID " + ctCollectionId,
					exception);
			}
		}
	}

	private void _trimProductionVersions(long fragmentEntryId) {
		try {
			int versionCount = _fragmentEntryVersionPersistence.dslQueryCount(
				DSLQueryFactoryUtil.count(
				).from(
					FragmentEntryVersionTable.INSTANCE
				).where(
					FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
						0L
					).and(
						FragmentEntryVersionTable.INSTANCE.fragmentEntryId.eq(
							fragmentEntryId)
					)
				));

			if (versionCount <=
					FragmentEntryVersionModelListener.MAX_VERSIONS) {

				return;
			}

			List<FragmentEntryVersion> fragmentEntryVersions =
				_fragmentEntryVersionPersistence.dslQuery(
					DSLQueryFactoryUtil.select(
						FragmentEntryVersionTable.INSTANCE
					).from(
						FragmentEntryVersionTable.INSTANCE
					).where(
						FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
							0L
						).and(
							FragmentEntryVersionTable.INSTANCE.fragmentEntryId.
								eq(fragmentEntryId)
						)
					).orderBy(
						FragmentEntryVersionTable.INSTANCE.version.descending()
					).limit(
						FragmentEntryVersionModelListener.MAX_VERSIONS,
						versionCount
					));

			for (FragmentEntryVersion fragmentEntryVersionToDelete :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(
					fragmentEntryVersionToDelete);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clean up old fragment entry versions for " +
						"fragment entry ID " + fragmentEntryId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionCTEventListener.class);

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}