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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChessGameLocalService}.
 *
 * @author Rubén Pulido
 * @see ChessGameLocalService
 * @generated
 */
public class ChessGameLocalServiceWrapper
	implements ChessGameLocalService, ServiceWrapper<ChessGameLocalService> {

	public ChessGameLocalServiceWrapper(
		ChessGameLocalService chessGameLocalService) {

		_chessGameLocalService = chessGameLocalService;
	}

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
	@Override
	public com.liferay.chess.model.ChessGame addChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return _chessGameLocalService.addChessGame(chessGame);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>ChessGameLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>ChessGameLocalServiceUtil</code>.
	 */
	@Override
	public com.liferay.chess.model.ChessGame addChessGame(
			long userId, long groupId, long whiteBlackPlayerId,
			long blackPlayerId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.addChessGame(
			userId, groupId, whiteBlackPlayerId, blackPlayerId, serviceContext);
	}

	/**
	 * Creates a new chess game with the primary key. Does not add the chess game to the database.
	 *
	 * @param chessGameId the primary key for the new chess game
	 * @return the new chess game
	 */
	@Override
	public com.liferay.chess.model.ChessGame createChessGame(long chessGameId) {
		return _chessGameLocalService.createChessGame(chessGameId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.chess.model.ChessGame deleteChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return _chessGameLocalService.deleteChessGame(chessGame);
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
	@Override
	public com.liferay.chess.model.ChessGame deleteChessGame(long chessGameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.deleteChessGame(chessGameId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _chessGameLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _chessGameLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _chessGameLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _chessGameLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _chessGameLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _chessGameLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _chessGameLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.chess.model.ChessGame fetchChessGame(long chessGameId) {
		return _chessGameLocalService.fetchChessGame(chessGameId);
	}

	/**
	 * Returns the chess game matching the UUID and group.
	 *
	 * @param uuid the chess game's UUID
	 * @param groupId the primary key of the group
	 * @return the matching chess game, or <code>null</code> if a matching chess game could not be found
	 */
	@Override
	public com.liferay.chess.model.ChessGame fetchChessGameByUuidAndGroupId(
		String uuid, long groupId) {

		return _chessGameLocalService.fetchChessGameByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _chessGameLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the chess game with the primary key.
	 *
	 * @param chessGameId the primary key of the chess game
	 * @return the chess game
	 * @throws PortalException if a chess game with the primary key could not be found
	 */
	@Override
	public com.liferay.chess.model.ChessGame getChessGame(long chessGameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.getChessGame(chessGameId);
	}

	/**
	 * Returns the chess game matching the UUID and group.
	 *
	 * @param uuid the chess game's UUID
	 * @param groupId the primary key of the group
	 * @return the matching chess game
	 * @throws PortalException if a matching chess game could not be found
	 */
	@Override
	public com.liferay.chess.model.ChessGame getChessGameByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.getChessGameByUuidAndGroupId(
			uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.chess.model.ChessGame> getChessGames(
		int start, int end) {

		return _chessGameLocalService.getChessGames(start, end);
	}

	/**
	 * Returns all the chess games matching the UUID and company.
	 *
	 * @param uuid the UUID of the chess games
	 * @param companyId the primary key of the company
	 * @return the matching chess games, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.chess.model.ChessGame>
		getChessGamesByUuidAndCompanyId(String uuid, long companyId) {

		return _chessGameLocalService.getChessGamesByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.chess.model.ChessGame>
		getChessGamesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.chess.model.ChessGame> orderByComparator) {

		return _chessGameLocalService.getChessGamesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of chess games.
	 *
	 * @return the number of chess games
	 */
	@Override
	public int getChessGamesCount() {
		return _chessGameLocalService.getChessGamesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _chessGameLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _chessGameLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _chessGameLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.chess.model.ChessGame updateChessGame(
		com.liferay.chess.model.ChessGame chessGame) {

		return _chessGameLocalService.updateChessGame(chessGame);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 * <p>
	 * Never reference this class directly. Use <code>ChessGameLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>ChessGameLocalServiceUtil</code>.
	 */
	@Override
	public com.liferay.chess.model.ChessGame updateChessGame(
			long chessGameId, String move)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _chessGameLocalService.updateChessGame(chessGameId, move);
	}

	@Override
	public ChessGameLocalService getWrappedService() {
		return _chessGameLocalService;
	}

	@Override
	public void setWrappedService(ChessGameLocalService chessGameLocalService) {
		_chessGameLocalService = chessGameLocalService;
	}

	private ChessGameLocalService _chessGameLocalService;

}