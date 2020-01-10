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

package com.liferay.layout.page.template.admin.web.internal.configuration.util;

import com.liferay.layout.page.template.admin.web.internal.configuration.ImportExportLayoutPageTemplateConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.layout.page.template.admin.web.internal.configuration.ImportExportLayoutPageTemplateConfiguration",
	immediate = true,
	service = ImportExportLayoutPageTemplateConfigurationUtil.class
)
public class ImportExportLayoutPageTemplateConfigurationUtil {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_importExportLayoutPageTemplateConfiguration =
			ConfigurableUtil.createConfigurable(
				ImportExportLayoutPageTemplateConfiguration.class, properties);
	}

	public static boolean enabled() {
		return _importExportLayoutPageTemplateConfiguration.enabled();
	}

	private static volatile ImportExportLayoutPageTemplateConfiguration _importExportLayoutPageTemplateConfiguration;

}