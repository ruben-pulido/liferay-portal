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

package com.liferay.chess.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChessGame. This utility wraps
 * <code>com.liferay.chess.service.impl.ChessGameLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Rubén Pulido
 * @see ChessGameLocalService
 * @generated
 */
public class ChessGameLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.chess.service.impl.ChessGameLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the chess game to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChessGameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param chessGame the chess game
	 * @return the chess game that was added
	 */
	public static com.liferay.chess.model.ChessGame addChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return getService().addChessGame(chessGame);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>ChessGameLocalService</code> via injection or a <code>ServiceTracker</code> or use <code>ChessGameLocalServiceUtil</code>.
	 */
	public static com.liferay.chess.model.ChessGame addChessGame(
			long userId, long groupId, long whiteBlackPlayerId,
			long blackPlayerId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addChessGame(
			userId, groupId, whiteBlackPlayerId, blackPlayerId, serviceContext);
	}

	/**
	 * Creates a new chess game with the primary key. Does not add the chess game to the database.
	 *
	 * @param chessGameId the primary key for the new chess game
	 * @return the new chess game
	 */
	public static com.liferay.chess.model.ChessGame createChessGame(
		long chessGameId) {

		return getService().createChessGame(chessGameId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the chess game from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChessGameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param chessGame the chess game
	 * @return the chess game that was removed
	 */
	public static com.liferay.chess.model.ChessGame deleteChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return getService().deleteChessGame(chessGame);
	}

	/**
	 * Deletes the chess game with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChessGameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game that was removed
	 * @throws PortalException if a chess game with the primary key could not be found
	 */
	public static com.liferay.chess.model.ChessGame deleteChessGame(
			long chessGameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteChessGame(chessGameId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.chess.model.impl.ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.chess.model.impl.ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.chess.model.ChessGame fetchChessGame(
		long chessGameId) {

		return getService().fetchChessGame(chessGameId);
	}

	/**
	 * Returns the chess game matching the UUID and group.
	 *
	 * @param uuid the chess game's UUID
	 * @param groupId the primary key of the group
	 * @return the matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	public static com.liferay.chess.model.ChessGame
		fetchChessGameByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchChessGameByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the chess game with the primary key.
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game
	 * @throws PortalException if a chess game with the primary key could not be found
	 */
	public static com.liferay.chess.model.ChessGame getChessGame(
			long chessGameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getChessGame(chessGameId);
	}

	/**
	 * Returns the chess game matching the UUID and group.
	 *
	 * @param uuid the chess game's UUID
	 * @param groupId the primary key of the group
	 * @return the matching chess game
	 * @throws PortalException if a matching chess game could not be found
	 */
	public static com.liferay.chess.model.ChessGame
			getChessGameByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getChessGameByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the chess games.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.chess.model.impl.ChessGameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @return the range of chess games
	 */
	public static java.util.List<com.liferay.chess.model.ChessGame>
		getChessGames(int start, int end) {

		return getService().getChessGames(start, end);
	}

	/**
	 * Returns all the chess games matching the UUID and company.
	 *
	 * @param uuid the UUID of the chess games
	 * @param companyId the primary key of the company
	 * @return the matching chess games, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.chess.model.ChessGame>
		getChessGamesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getChessGamesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of chess games matching the UUID and company.
	 *
	 * @param uuid the UUID of the chess games
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of chess games
	 * @param end the upper bound of the range of chess games (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching chess games, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.chess.model.ChessGame>
		getChessGamesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.chess.model.ChessGame> orderByComparator) {

		return getService().getChessGamesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of chess games.
	 *
	 * @return the number of chess games
	 */
	public static int getChessGamesCount() {
		return getService().getChessGamesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the chess game in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChessGameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param chessGame the chess game
	 * @return the chess game that was updated
	 */
	public static com.liferay.chess.model.ChessGame updateChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return getService().updateChessGame(chessGame);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>ChessGameLocalService</code> via injection or a <code>ServiceTracker</code> or use <code>ChessGameLocalServiceUtil</code>.
	 */
	public static com.liferay.chess.model.ChessGame updateChessGame(
			long chessGameId, String move)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateChessGame(chessGameId, move);
	}

	public static ChessGameLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChessGameLocalService, ChessGameLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChessGameLocalService.class);

		ServiceTracker<ChessGameLocalService, ChessGameLocalService>
			serviceTracker =
				new ServiceTracker
					<ChessGameLocalService, ChessGameLocalService>(
						bundle.getBundleContext(), ChessGameLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}