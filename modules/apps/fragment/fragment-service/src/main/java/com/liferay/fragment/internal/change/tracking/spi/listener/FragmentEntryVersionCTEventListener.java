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
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

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
		System.err.println(
			"LPD-75909 onAfterPublish fired ctCollectionId=" + ctCollectionId +
				" threadLocalCT=" +
					CTCollectionThreadLocal.getCTCollectionId());

		_dumpRawCounts("onAfterPublish entry (pre-commit)");

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_dumpRawCounts("commit callback (post-commit)");

				try {
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> {
							_fragmentEntryVersionPersistence.clearCache();

							List<Long> fragmentEntryIds =
								_fragmentEntryVersionPersistence.dslQuery(
									DSLQueryFactoryUtil.selectDistinct(
										FragmentEntryVersionTable.INSTANCE.
											fragmentEntryId
									).from(
										FragmentEntryVersionTable.INSTANCE
									).where(
										FragmentEntryVersionTable.INSTANCE.
											ctCollectionId.eq(0L)
									));

							System.err.println(
								"LPD-75909 commit callback found " +
									"fragmentEntryIds=" + fragmentEntryIds);

							for (long fragmentEntryId : fragmentEntryIds) {
								_trimProductionVersions(fragmentEntryId);
							}

							return null;
						});
				}
				catch (Throwable throwable) {
					System.err.println(
						"LPD-75909 commit callback FAILED for ctCollectionId=" +
							ctCollectionId);
					throwable.printStackTrace();
				}

				return null;
			});
	}

	private void _dumpRawCounts(String label) {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select ctCollectionId, count(*) c from FragmentEntryVersion " +
					"where fragmentEntryId=102 group by ctCollectionId order " +
						"by ctCollectionId");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			StringBuilder sb = new StringBuilder(
				"LPD-75909 RAW @ " + label + " ->");

			while (resultSet.next()) {
				sb.append(
					" ct=" + resultSet.getLong(1) + ":" + resultSet.getInt(2));
			}

			System.err.println(sb);
		}
		catch (Exception exception) {
			System.err.println("LPD-75909 RAW dump failed: " + exception);
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

			System.err.println(
				"LPD-75909 _trimProductionVersions fragmentEntryId=" +
					fragmentEntryId + " versionCount=" + versionCount);

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

			System.err.println(
				"LPD-75909 _trimProductionVersions deleting " +
					fragmentEntryVersions.size() + " rows for fragmentEntryId=" +
					fragmentEntryId);

			for (FragmentEntryVersion fragmentEntryVersionToDelete :
					fragmentEntryVersions) {

				System.err.println(
					"LPD-75909 _trimProductionVersions removing version=" +
						fragmentEntryVersionToDelete.getVersion() +
							" fragmentEntryVersionId=" +
								fragmentEntryVersionToDelete.
									getFragmentEntryVersionId());

				_fragmentEntryVersionPersistence.remove(
					fragmentEntryVersionToDelete);
			}
		}
		catch (Exception exception) {
			System.err.println(
				"LPD-75909 _trimProductionVersions FAILED fragmentEntryId=" +
					fragmentEntryId);
			exception.printStackTrace();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionCTEventListener.class);

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

}