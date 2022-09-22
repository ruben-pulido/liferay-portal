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

package com.liferay.chess.service.persistence;

import com.liferay.chess.exception.NoSuchGameException;
import com.liferay.chess.model.ChessGame;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the chess game service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Rubén Pulido
 * @see ChessGameUtil
 * @generated
 */
@ProviderType
public interface ChessGamePersistence extends BasePersistence<ChessGame> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChessGameUtil} to access the chess game persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the chess games where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching chess games
	 */
	public java.util.List<ChessGame> findByUuid(String uuid);

	/**
	 * Returns a range of all the chess games where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the chess games where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns an ordered range of all the chess games where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first chess game in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the first chess game in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the last chess game in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the last chess game in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the chess games before and after the current chess game in the ordered set where uuid = &#63;.
	 *
	 * @param chessGameId the primary key of the current chess game
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next chess game
	 * @throws NoSuchGameException if a chess game with the primary key could not be found
	 */
	public ChessGame[] findByUuid_PrevAndNext(
			long chessGameId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Removes all the chess games where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of chess games where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching chess games
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the chess game where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchGameException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByUUID_G(String uuid, long groupId)
		throws NoSuchGameException;

	/**
	 * Returns the chess game where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the chess game where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the chess game where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the chess game that was removed
	 */
	public ChessGame removeByUUID_G(String uuid, long groupId)
		throws NoSuchGameException;

	/**
	 * Returns the number of chess games where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching chess games
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the chess games where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching chess games
	 */
	public java.util.List<ChessGame> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the chess games where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the chess games where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns an ordered range of all the chess games where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first chess game in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the first chess game in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the last chess game in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the last chess game in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the chess games before and after the current chess game in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param chessGameId the primary key of the current chess game
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next chess game
	 * @throws NoSuchGameException if a chess game with the primary key could not be found
	 */
	public ChessGame[] findByUuid_C_PrevAndNext(
			long chessGameId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Removes all the chess games where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of chess games where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching chess games
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the chess games where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(long groupId);

	/**
	 * Returns a range of all the chess games where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the chess games where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns an ordered range of all the chess games where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first chess game in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the first chess game in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the last chess game in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game
	 * @throws NoSuchGameException if a matching chess game could not be found
	 */
	public ChessGame findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns the last chess game in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public ChessGame fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns the chess games before and after the current chess game in the ordered set where groupId = &#63;.
	 *
	 * @param chessGameId the primary key of the current chess game
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next chess game
	 * @throws NoSuchGameException if a chess game with the primary key could not be found
	 */
	public ChessGame[] findByGroupId_PrevAndNext(
			long chessGameId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
				orderByComparator)
		throws NoSuchGameException;

	/**
	 * Returns all the chess games where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the chess games where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the chess games where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns an ordered range of all the chess games where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching chess games
	 */
	public java.util.List<ChessGame> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the chess games where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of chess games where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching chess games
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of chess games where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching chess games
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Caches the chess game in the entity cache if it is enabled.
	 *
	 * @param chessGame the chess game
	 */
	public void cacheResult(ChessGame chessGame);

	/**
	 * Caches the chess games in the entity cache if it is enabled.
	 *
	 * @param chessGames the chess games
	 */
	public void cacheResult(java.util.List<ChessGame> chessGames);

	/**
	 * Creates a new chess game with the primary key. Does not add the chess game to the database.
	 *
	 * @param chessGameId the primary key for the new chess game
	 * @return the new chess game
	 */
	public ChessGame create(long chessGameId);

	/**
	 * Removes the chess game with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game that was removed
	 * @throws NoSuchGameException if a chess game with the primary key could not be found
	 */
	public ChessGame remove(long chessGameId) throws NoSuchGameException;

	public ChessGame updateImpl(ChessGame chessGame);

	/**
	 * Returns the chess game with the primary key or throws a <code>NoSuchGameException</code> if it could not be found.
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game
	 * @throws NoSuchGameException if a chess game with the primary key could not be found
	 */
	public ChessGame findByPrimaryKey(long chessGameId)
		throws NoSuchGameException;

	/**
	 * Returns the chess game with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game, or <code>null</code> if a chess game with the primary key could not be found
	 */
	public ChessGame fetchByPrimaryKey(long chessGameId);

	/**
	 * Returns all the chess games.
	 *
	 * @return the chess games
	 */
	public java.util.List<ChessGame> findAll();

	/**
	 * Returns a range of all the chess games.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of chess games
	 */
	public java.util.List<ChessGame> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the chess games.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of chess games
	 */
	public java.util.List<ChessGame> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator);

	/**
	 * Returns an ordered range of all the chess games.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of chess games
	 */
	public java.util.List<ChessGame> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChessGame>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the chess games from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of chess games.
	 *
	 * @return the number of chess games
	 */
	public int countAll();

}