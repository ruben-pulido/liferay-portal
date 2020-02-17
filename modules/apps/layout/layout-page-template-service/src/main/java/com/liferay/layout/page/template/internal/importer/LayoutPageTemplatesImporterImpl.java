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

package com.liferay.layout.page.template.internal.importer;

import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutPageTemplatesImporter.class)
public class LayoutPageTemplatesImporterImpl
	implements LayoutPageTemplatesImporter {

	@Override
	public void importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			Map<String, LayoutPageTemplateCollectionFolder>
				layoutPageTemplateCollectionFolderMap =
					_getLayoutPageTemplateCollectionFolderMap(zipFile);

			if (MapUtil.isEmpty(layoutPageTemplateCollectionFolderMap)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No valid layout page template entries found in " +
							zipFile.getName());
				}
			}
		}
	}

	private Map<String, LayoutPageTemplateCollectionFolder>
		_getLayoutPageTemplateCollectionFolderMap(ZipFile zipFile) {

		Map<String, LayoutPageTemplateCollectionFolder>
			layoutPageTemplateCollectionFolderMap = new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			String[] pathParameters = StringUtil.split(
				fileName, CharPool.SLASH);

			if ((pathParameters.length != 4) ||
				!Objects.equals(pathParameters[0], _ROOT_FOLDER) ||
				!Objects.equals(pathParameters[3], _PAGE_TEMPLATE_FILE_NAME)) {

				continue;
			}

			String layoutPageTemplateCollectionKey = pathParameters[1];

			String layoutPageTemplateEntryKey = pathParameters[2];

			LayoutPageTemplateCollectionFolder
				layoutPageTemplateCollectionFolder =
					layoutPageTemplateCollectionFolderMap.computeIfAbsent(
						layoutPageTemplateCollectionKey,
						key -> new LayoutPageTemplateCollectionFolder(key));

			layoutPageTemplateCollectionFolder.addLayoutPageTemplateEntry(
				layoutPageTemplateEntryKey, fileName);

			layoutPageTemplateCollectionFolderMap.put(
				layoutPageTemplateCollectionKey,
				layoutPageTemplateCollectionFolder);
		}

		return layoutPageTemplateCollectionFolderMap;
	}

	private static final String _PAGE_TEMPLATE_FILE_NAME = "page-template.json";

	private static final String _ROOT_FOLDER = "page-templates";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesImporterImpl.class);

	private class LayoutPageTemplateCollectionFolder {

		public LayoutPageTemplateCollectionFolder(String key) {
			_key = key;

			_layoutPageTemplateEntries = new HashMap<>();
		}

		public void addLayoutPageTemplateEntry(String key, String fileName) {
			_layoutPageTemplateEntries.put(key, fileName);
		}

		public String getKey() {
			return _key;
		}

		private final String _key;
		private final Map<String, String> _layoutPageTemplateEntries;

	}

}