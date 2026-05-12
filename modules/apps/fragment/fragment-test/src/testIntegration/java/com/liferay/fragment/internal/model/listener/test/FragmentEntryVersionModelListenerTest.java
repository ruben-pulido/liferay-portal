/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.internal.model.listener.FragmentEntryVersionModelListener;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class FragmentEntryVersionModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_createDateTimestamp = new Timestamp(
			System.currentTimeMillis() + 3_600_000L);
		_modifiedDateTime = System.currentTimeMillis() + 3_600_000L;
		_version = 1000;
	}

	@Test
	public void testOnAfterCreate() throws Throwable {
		FragmentEntry fragmentEntry = _addFragmentEntry();

		int oldestVersion = _getOldestVersion(fragmentEntry);

		Assert.assertTrue(oldestVersion > 0);

		Assert.assertEquals(1, _countFragmentEntryVersions(fragmentEntry));

		_insertFragmentEntryVersions(
			FragmentEntryVersionModelListener.MAX_VERSIONS - 2, fragmentEntry);

		Assert.assertEquals(
			FragmentEntryVersionModelListener.MAX_VERSIONS - 1,
			_countFragmentEntryVersions(fragmentEntry));
		Assert.assertTrue(
			_hasFragmentEntryVersion(fragmentEntry, oldestVersion));

		_updateFragmentEntry(fragmentEntry);

		Assert.assertEquals(
			FragmentEntryVersionModelListener.MAX_VERSIONS,
			_countFragmentEntryVersions(fragmentEntry));
		Assert.assertTrue(
			_hasFragmentEntryVersion(fragmentEntry, oldestVersion));

		_updateFragmentEntry(fragmentEntry);

		Assert.assertEquals(
			FragmentEntryVersionModelListener.MAX_VERSIONS,
			_countFragmentEntryVersions(fragmentEntry));
		Assert.assertFalse(
			_hasFragmentEntryVersion(fragmentEntry, oldestVersion));
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		return _fragmentEntryLocalService.addFragmentEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
			false, false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private int _countFragmentEntryVersions(FragmentEntry fragmentEntry)
		throws Throwable {

		return TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> _fragmentEntryVersionPersistence.countByFragmentEntryId(
				fragmentEntry.getFragmentEntryId()));
	}

	private int _getOldestVersion(FragmentEntry fragmentEntry)
		throws Throwable {

		FragmentEntryVersion fragmentEntryVersion =
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() ->
					_fragmentEntryVersionPersistence.
						fetchByFragmentEntryId_First(
							fragmentEntry.getFragmentEntryId(),
							OrderByComparatorFactoryUtil.create(
								"FragmentEntryVersion", "version", true)));

		if (fragmentEntryVersion == null) {
			return -1;
		}

		return fragmentEntryVersion.getVersion();
	}

	private boolean _hasFragmentEntryVersion(
			FragmentEntry fragmentEntry, int version)
		throws Throwable {

		FragmentEntryVersion fragmentEntryVersion =
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() ->
					_fragmentEntryVersionPersistence.
						fetchByFragmentEntryId_Version(
							fragmentEntry.getFragmentEntryId(), version));

		if (fragmentEntryVersion != null) {
			return true;
		}

		return false;
	}

	private void _insertFragmentEntryVersions(
			int count, FragmentEntry fragmentEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"insert into FragmentEntryVersion (mvccVersion, ",
						"ctCollectionId, fragmentEntryVersionId, version, ",
						"fragmentEntryId, groupId, companyId, userId, ",
						"createDate, modifiedDate, name, status) values (0, ",
						"0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			for (int i = 0; i < count; i++) {
				Timestamp modifiedDateTimestamp = new Timestamp(
					_modifiedDateTime);

				_modifiedDateTime += 1000L;

				preparedStatement.setLong(
					1,
					_counterLocalService.increment(
						FragmentEntryVersion.class.getName()));
				preparedStatement.setInt(2, _version++);
				preparedStatement.setLong(
					3, fragmentEntry.getFragmentEntryId());
				preparedStatement.setLong(4, fragmentEntry.getGroupId());
				preparedStatement.setLong(5, fragmentEntry.getCompanyId());
				preparedStatement.setLong(6, fragmentEntry.getUserId());
				preparedStatement.setTimestamp(7, _createDateTimestamp);
				preparedStatement.setTimestamp(8, modifiedDateTimestamp);
				preparedStatement.setString(9, RandomTestUtil.randomString());
				preparedStatement.setInt(10, WorkflowConstants.STATUS_APPROVED);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}

		_fragmentEntryVersionPersistence.clearCache();
	}

	private FragmentEntry _updateFragmentEntry(FragmentEntry fragmentEntry)
		throws Exception {

		fragmentEntry.setHtml(RandomTestUtil.randomString());

		return _fragmentEntryLocalService.updateFragmentEntry(fragmentEntry);
	}

	@Inject
	private CounterLocalService _counterLocalService;

	private Timestamp _createDateTimestamp;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

	@DeleteAfterTestRun
	private Group _group;

	private long _modifiedDateTime;
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});
	private int _version;

}