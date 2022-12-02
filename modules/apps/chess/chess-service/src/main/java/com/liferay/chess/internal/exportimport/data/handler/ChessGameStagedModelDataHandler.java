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

package com.liferay.chess.internal.exportimport.data.handler;

import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class ChessGameStagedModelDataHandler
	extends BaseStagedModelDataHandler<ChessGame> {

	public static final String[] CLASS_NAMES = {ChessGame.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(ChessGame chessGame)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(chessGame);
	}

	@Override
	public List<ChessGame> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			ChessGame chessGame)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			chessGame);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(chessGame),
			chessGame);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long chessGameId)
		throws Exception {

		ChessGame existingChessGame = fetchMissingReference(
			uuid, groupId);

		if (existingChessGame == null) {
			return;
		}

		Map<Long, Long> chessGameIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				ChessGame.class);

		chessGameIds.put(
			chessGameId, existingChessGame.getChessGameId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			ChessGame chessGame)
		throws Exception {

		ChessGame importedChessGame =
			(ChessGame)chessGame.clone();

		importedChessGame.setGroupId(portletDataContext.getScopeGroupId());

		ChessGame existingChessGame =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				chessGame.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingChessGame == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedChessGame = _stagedModelRepository.addStagedModel(
				portletDataContext, importedChessGame);
		}
		else {
			importedChessGame.setMvccVersion(
				existingChessGame.getMvccVersion());
			importedChessGame.setChessGameId(
				existingChessGame.getChessGameId());

			importedChessGame = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedChessGame);
		}

		portletDataContext.importClassedModel(
			chessGame, importedChessGame);
	}

	@Override
	protected StagedModelRepository<ChessGame> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.chess.model.ChessGame)",
		unbind = "-"
	)
	private StagedModelRepository<ChessGame> _stagedModelRepository;

	@Reference
	private ChessGameLocalService _chessGameLocalService;

}