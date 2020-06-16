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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.ViewportColumnConfiguration;
import com.liferay.headless.delivery.dto.v1_0.ViewportColumnConfigurationDefinition;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class ColumnLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return ColumnLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageColumnDefinition() {
					{
						size = columnLayoutStructureItem.getSize();

						Map<String, JSONObject> viewportConfigurations =
							columnLayoutStructureItem.
								getViewportSizeConfigurations();

						if (MapUtil.isNotEmpty(viewportConfigurations)) {
							viewportColumnConfiguration =
								new ViewportColumnConfiguration() {
									{
										landscapeMobile =
											_getViewportConfiguration(
												ViewportSize.MOBILE_LANDSCAPE,
												viewportConfigurations);

										portraitMobile =
											_getViewportConfiguration(
												ViewportSize.PORTRAIT_MOBILE,
												viewportConfigurations);

										tablet = _getViewportConfiguration(
											ViewportSize.TABLET,
											viewportConfigurations);
									}
								};
						}
					}
				};
				type = PageElement.Type.COLUMN;
			}
		};
	}

	private ViewportColumnConfigurationDefinition _getViewportConfiguration(
		ViewportSize viewportSize,
		Map<String, JSONObject> viewportConfigurations) {

		if (!viewportConfigurations.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = viewportConfigurations.get(
			viewportSize.getViewportSizeId());

		return new ViewportColumnConfigurationDefinition() {
			{
				if (jsonObject.has("size")) {
					size = jsonObject.getInt("size");
				}
			}
		};
	}

}