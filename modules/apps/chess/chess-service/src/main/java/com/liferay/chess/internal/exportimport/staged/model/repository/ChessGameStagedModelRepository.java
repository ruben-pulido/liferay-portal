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

package com.liferay.chess.internal.exportimport.staged.model.repository;

import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.chess.model.ChessGame",
	service = StagedModelRepository.class
)
public class ChessGameStagedModelRepository
	implements StagedModelRepository<ChessGame> {

	@Override
	public ChessGame addStagedModel(
			PortletDataContext portletDataContext,
			ChessGame chessGame)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			chessGame.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			chessGame);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(chessGame.getUuid());
		}

		return _chessGameLocalService.addChessGame(
			userId, chessGame.getGroupId(),
			chessGame.getWhitePlayerId(), chessGame.getBlackPlayerId(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		ChessGame chessGame = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (chessGame != null) {
			deleteStagedModel(chessGame);
		}
	}

	@Override
	public void deleteStagedModel(ChessGame chessGame)
		throws PortalException {

		_chessGameLocalService.deleteChessGame(chessGame);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public ChessGame fetchMissingReference(String uuid, long groupId) {
		return (ChessGame)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public ChessGame fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _chessGameLocalService.fetchChessGameByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<ChessGame> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _chessGameLocalService.
			getChessGamesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _chessGameLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public ChessGame getStagedModel(long classPK) throws PortalException {
		return _chessGameLocalService.getChessGame(classPK);
	}

	@Override
	public ChessGame saveStagedModel(ChessGame chessGame)
		throws PortalException {

		return _chessGameLocalService.updateChessGame(chessGame);
	}

	@Override
	public ChessGame updateStagedModel(
			PortletDataContext portletDataContext,
			ChessGame chessGame)
		throws PortalException {

		return _chessGameLocalService.updateChessGame(
			chessGame.getChessGameId(), chessGame.getMoves());
	}

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private ChessGameLocalService _chessGameLocalService;

}