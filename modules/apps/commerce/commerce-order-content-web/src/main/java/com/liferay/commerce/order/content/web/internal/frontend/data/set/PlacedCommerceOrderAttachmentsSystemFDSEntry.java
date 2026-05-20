/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.frontend.data.set.SystemFDSEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDER_ATTACHMENTS,
	service = SystemFDSEntry.class
)
public class PlacedCommerceOrderAttachmentsSystemFDSEntry
	implements SystemFDSEntry {

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return CommerceOrderFragmentFDSNames.PLACED_ORDER_ATTACHMENTS;
	}

	@Override
	public String getRESTApplication() {
		return "/headless-commerce-delivery-order/v1.0";
	}

	@Override
	public String getRESTEndpoint() {
		return "/v1.0/placed-orders/{placedOrderId}/attachments";
	}

	@Override
	public String getRESTSchema() {
		return "Attachment";
	}

	@Override
	public String getTitle() {
		return "Order Attachments";
	}

}