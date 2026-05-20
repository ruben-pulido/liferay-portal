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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
		try {
			List<Long> fragmentEntryIds =
				_fragmentEntryVersionPersistence.dslQuery(
					DSLQueryFactoryUtil.selectDistinct(
						FragmentEntryVersionTable.INSTANCE.fragmentEntryId
					).from(
						FragmentEntryVersionTable.INSTANCE
					).where(
						FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(0L)
					));

			for (long fragmentEntryId : fragmentEntryIds) {
				_trimProductionVersions(fragmentEntryId);
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