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

package com.liferay.chess.quote.internal;

import com.liferay.chess.quote.ChessQuoteProvider;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = ChessQuoteProvider.class)
public class ChessDefaultQuoteProviderImpl implements ChessQuoteProvider {

	@Override
	public String getQuote() {
		return portal.getComputerName() +
			" is my computer name and this is an OSGi identifier: " +
				companyService.getOSGiServiceIdentifier();
	}

	@Reference
	protected CompanyService companyService;

	@Reference
	protected Portal portal;

}