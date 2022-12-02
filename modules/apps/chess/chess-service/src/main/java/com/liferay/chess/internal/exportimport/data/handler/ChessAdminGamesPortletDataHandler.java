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

import com.liferay.chess.constants.ChessConstants;
import com.liferay.chess.constants.ChessPortletKeys;
import com.liferay.chess.model.ChessGame;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletPreferences;
import java.util.List;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "javax.portlet.name=" + ChessPortletKeys.CHESS_ADMIN_GAMES,
	service = PortletDataHandler.class
)
public class ChessAdminGamesPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "chess";

	@Override
	public boolean isConfigurationEnabled() {
		return false;
	}

	@Override
	public boolean isStaged() {
		return false;
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(ChessGame.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "games", true, false, null,
				ChessGame.class.getName()));
		setPublishToLiveByDefault(true);
		setStagingControls(getExportControls());
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "games")) {
			return getExportDataRootElementString(rootElement);
		}

		portletDataContext.addPortletPermissions(
			ChessConstants.RESOURCE_NAME);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery exportActionableDynamicQuery =
			_stagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "games")) {
			return null;
		}

		portletDataContext.importPortletPermissions(
			ChessConstants.RESOURCE_NAME);

		Element chessGamesElement =
			portletDataContext.getImportDataGroupElement(ChessGame.class);

		List<Element> chessGamesElements =
			chessGamesElement.elements();

		for (Element chessGameElement : chessGamesElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, chessGameElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(ChessGame.class.getName())
				});

			return;
		}

		ActionableDynamicQuery exportActionableDynamicQuery =
			_stagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.performCount();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference(
		target = "(model.class.name=com.liferay.chess.model.ChessGame)",
		unbind = "-"
	)
	private StagedModelRepository<ChessGame> _stagedModelRepository;

	@Reference
	private Staging _staging;

}