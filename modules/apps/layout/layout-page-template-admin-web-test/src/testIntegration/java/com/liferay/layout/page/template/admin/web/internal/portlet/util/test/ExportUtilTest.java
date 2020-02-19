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

package com.liferay.layout.page.template.admin.web.internal.portlet.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ExportUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();

		_file = _generateZipFile(_FRAGMENTS_PATH);
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.delete(_file);
	}

	@Test
	public void testExportPageTemplateDefinitions() {
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, String path, String key)
		throws IOException {

		if (Validator.isNull(key)) {
			return;
		}

		String entryPath = path + StringPool.FORWARD_SLASH + key;

		String zipPath = StringUtil.removeSubstring(entryPath, _FRAGMENTS_PATH);

		URL url = _bundle.getEntry(entryPath);

		zipWriter.addEntry(zipPath, url.openStream());
	}

	private File _generateZipFile(String path) throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

//		URL collectionURL = _bundle.getEntry(
//			path + FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG);
//
//		zipWriter.addEntry(
//			FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG,
//			collectionURL.openStream());
//
//		Enumeration<URL> enumeration = _bundle.findEntries(
//			path, FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG,
//			true);
//
//		try {
//			while (enumeration.hasMoreElements()) {
//				URL url = enumeration.nextElement();
//
//				_populateZipWriter(zipWriter, url);
//			}
//
			zipWriter.finish();

			return zipWriter.getFile();
//		}
//		catch (Exception exception) {
//			throw new Exception(exception);
//		}
	}

	private void _populateZipWriter(ZipWriter zipWriter, URL url)
		throws IOException, JSONException {

		String content = StringUtil.read(url.openStream());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		String path = FileUtil.getPath(url.getPath());

//		_addZipWriterEntry(
//			zipWriter, path,
//			FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG);
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("cssPath"));
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("htmlPath"));
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("jsPath"));
		_addZipWriterEntry(
			zipWriter, path, jsonObject.getString("thumbnailPath"));
	}

	private static final String _FRAGMENTS_PATH =
		"com/liferay/fragment/dependencies/fragments/";

	private Bundle _bundle;
	private File _file;

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}