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

package com.liferay.chess.gogo;

import com.liferay.chess.piece.ChessPiece;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.command.function=piecesSayCatchphrase", "osgi.command.scope=chess"
	},
	service = Object.class
)
public class ChessPiecesSayCatchphraseCommand {

	public void piecesSayCatchphrase() {
		List<ChessPiece> chessPieces = new ArrayList<>(
			_serviceTrackerMap.values());

		for (ChessPiece chessPiece : chessPieces) {
			System.out.println(
				chessPiece.getName() + " says: " + chessPiece.getCathphrase());
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ChessPiece.class, null,
			(serviceReference, emitter) -> {
				ChessPiece fragmentCollectionFilter = bundleContext.getService(
					serviceReference);

				emitter.emit(fragmentCollectionFilter.getName());
			},
			new ChessPieceServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ChessPiece> _serviceTrackerMap;

	private class ChessPieceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ChessPiece, ChessPiece> {

		public ChessPieceServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public ChessPiece addingService(
			ServiceReference<ChessPiece> serviceReference) {

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<ChessPiece> serviceReference,
			ChessPiece fragmentCollectionFilter) {
		}

		@Override
		public void removedService(
			ServiceReference<ChessPiece> serviceReference,
			ChessPiece fragmentCollectionFilter) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}