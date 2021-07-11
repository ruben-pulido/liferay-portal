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

package com.liferay.chess.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.chess.exception.NoSuchGameException;
import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalServiceUtil;
import com.liferay.chess.service.persistence.ChessGamePersistence;
import com.liferay.chess.service.persistence.ChessGameUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ChessGamePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.chess.service"));

	@Before
	public void setUp() {
		_persistence = ChessGameUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChessGame> iterator = _chessGames.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChessGame chessGame = _persistence.create(pk);

		Assert.assertNotNull(chessGame);

		Assert.assertEquals(chessGame.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChessGame newChessGame = addChessGame();

		_persistence.remove(newChessGame);

		ChessGame existingChessGame = _persistence.fetchByPrimaryKey(
			newChessGame.getPrimaryKey());

		Assert.assertNull(existingChessGame);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChessGame();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChessGame newChessGame = _persistence.create(pk);

		newChessGame.setMvccVersion(RandomTestUtil.nextLong());

		newChessGame.setUuid(RandomTestUtil.randomString());

		newChessGame.setGroupId(RandomTestUtil.nextLong());

		newChessGame.setCompanyId(RandomTestUtil.nextLong());

		newChessGame.setUserId(RandomTestUtil.nextLong());

		newChessGame.setUserName(RandomTestUtil.randomString());

		newChessGame.setCreateDate(RandomTestUtil.nextDate());

		newChessGame.setModifiedDate(RandomTestUtil.nextDate());

		newChessGame.setWhitePlayerId(RandomTestUtil.nextLong());

		newChessGame.setBlackPlayerId(RandomTestUtil.nextLong());

		newChessGame.setMoves(RandomTestUtil.randomString());

		newChessGame.setWinnerPlayerId(RandomTestUtil.nextLong());

		_chessGames.add(_persistence.update(newChessGame));

		ChessGame existingChessGame = _persistence.findByPrimaryKey(
			newChessGame.getPrimaryKey());

		Assert.assertEquals(
			existingChessGame.getMvccVersion(), newChessGame.getMvccVersion());
		Assert.assertEquals(
			existingChessGame.getUuid(), newChessGame.getUuid());
		Assert.assertEquals(
			existingChessGame.getChessGameId(), newChessGame.getChessGameId());
		Assert.assertEquals(
			existingChessGame.getGroupId(), newChessGame.getGroupId());
		Assert.assertEquals(
			existingChessGame.getCompanyId(), newChessGame.getCompanyId());
		Assert.assertEquals(
			existingChessGame.getUserId(), newChessGame.getUserId());
		Assert.assertEquals(
			existingChessGame.getUserName(), newChessGame.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingChessGame.getCreateDate()),
			Time.getShortTimestamp(newChessGame.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingChessGame.getModifiedDate()),
			Time.getShortTimestamp(newChessGame.getModifiedDate()));
		Assert.assertEquals(
			existingChessGame.getWhitePlayerId(),
			newChessGame.getWhitePlayerId());
		Assert.assertEquals(
			existingChessGame.getBlackPlayerId(),
			newChessGame.getBlackPlayerId());
		Assert.assertEquals(
			existingChessGame.getMoves(), newChessGame.getMoves());
		Assert.assertEquals(
			existingChessGame.getWinnerPlayerId(),
			newChessGame.getWinnerPlayerId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChessGame newChessGame = addChessGame();

		ChessGame existingChessGame = _persistence.findByPrimaryKey(
			newChessGame.getPrimaryKey());

		Assert.assertEquals(existingChessGame, newChessGame);
	}

	@Test(expected = NoSuchGameException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ChessGame> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ChessGame", "mvccVersion", true, "uuid", true, "chessGameId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "whitePlayerId",
			true, "blackPlayerId", true, "moves", true, "winnerPlayerId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChessGame newChessGame = addChessGame();

		ChessGame existingChessGame = _persistence.fetchByPrimaryKey(
			newChessGame.getPrimaryKey());

		Assert.assertEquals(existingChessGame, newChessGame);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChessGame missingChessGame = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChessGame);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ChessGame newChessGame1 = addChessGame();
		ChessGame newChessGame2 = addChessGame();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChessGame1.getPrimaryKey());
		primaryKeys.add(newChessGame2.getPrimaryKey());

		Map<Serializable, ChessGame> chessGames =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, chessGames.size());
		Assert.assertEquals(
			newChessGame1, chessGames.get(newChessGame1.getPrimaryKey()));
		Assert.assertEquals(
			newChessGame2, chessGames.get(newChessGame2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChessGame> chessGames =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(chessGames.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ChessGame newChessGame = addChessGame();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChessGame.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChessGame> chessGames =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, chessGames.size());
		Assert.assertEquals(
			newChessGame, chessGames.get(newChessGame.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChessGame> chessGames =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(chessGames.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ChessGame newChessGame = addChessGame();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChessGame.getPrimaryKey());

		Map<Serializable, ChessGame> chessGames =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, chessGames.size());
		Assert.assertEquals(
			newChessGame, chessGames.get(newChessGame.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ChessGameLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<ChessGame>() {

				@Override
				public void performAction(ChessGame chessGame) {
					Assert.assertNotNull(chessGame);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ChessGame newChessGame = addChessGame();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ChessGame.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"chessGameId", newChessGame.getChessGameId()));

		List<ChessGame> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChessGame existingChessGame = result.get(0);

		Assert.assertEquals(existingChessGame, newChessGame);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ChessGame.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"chessGameId", RandomTestUtil.nextLong()));

		List<ChessGame> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ChessGame newChessGame = addChessGame();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ChessGame.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("chessGameId"));

		Object newChessGameId = newChessGame.getChessGameId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"chessGameId", new Object[] {newChessGameId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChessGameId = result.get(0);

		Assert.assertEquals(existingChessGameId, newChessGameId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ChessGame.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("chessGameId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"chessGameId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ChessGame newChessGame = addChessGame();

		_persistence.clearCache();

		ChessGame existingChessGame = _persistence.findByPrimaryKey(
			newChessGame.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingChessGame.getUuid(),
				ReflectionTestUtil.invoke(
					existingChessGame, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingChessGame.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingChessGame, "getOriginalGroupId", new Class<?>[0]));
	}

	protected ChessGame addChessGame() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChessGame chessGame = _persistence.create(pk);

		chessGame.setMvccVersion(RandomTestUtil.nextLong());

		chessGame.setUuid(RandomTestUtil.randomString());

		chessGame.setGroupId(RandomTestUtil.nextLong());

		chessGame.setCompanyId(RandomTestUtil.nextLong());

		chessGame.setUserId(RandomTestUtil.nextLong());

		chessGame.setUserName(RandomTestUtil.randomString());

		chessGame.setCreateDate(RandomTestUtil.nextDate());

		chessGame.setModifiedDate(RandomTestUtil.nextDate());

		chessGame.setWhitePlayerId(RandomTestUtil.nextLong());

		chessGame.setBlackPlayerId(RandomTestUtil.nextLong());

		chessGame.setMoves(RandomTestUtil.randomString());

		chessGame.setWinnerPlayerId(RandomTestUtil.nextLong());

		_chessGames.add(_persistence.update(chessGame));

		return chessGame;
	}

	private List<ChessGame> _chessGames = new ArrayList<ChessGame>();
	private ChessGamePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}