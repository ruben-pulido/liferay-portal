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

package com.liferay.fragment.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.segments.constants.SegmentsConstants;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryConfigUtilTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		new FileUtil().setFile(new FileImpl());
	}

	@Test
	public void testGetConfigurationDefaultValuesJSONObject() throws Exception {
		JSONObject configurationDefaultValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationDefaultValuesJSONObject(
				_getFileContent("configuration.json"));

		JSONObject expectedConfigurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		Assert.assertEquals(
			expectedConfigurationDefaultValuesJSONObject.toJSONString(),
			configurationDefaultValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesBlank() throws Exception {
		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"), StringPool.BLANK,
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesConfigBlank() throws Exception {
		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				StringPool.BLANK,
				_getFileContent("editable-values-default.json"),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals("{}", configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesConfigNull() throws Exception {
		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				null, _getFileContent("editable-values-default.json"),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals("{}", configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesDefault() throws Exception {
		String fileName = "editable-values-default.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesEmtpy() throws Exception {
		String fileName = "editable-values-empty.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldInvalidDataType()
		throws Exception {

		String fileName = "editable-values-field-invalid-data-type.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldInvalidName() throws Exception {
		String fileName = "editable-values-field-invalid-name.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldInvalidOption()
		throws Exception {

		String fileName = "editable-values-field-invalid-option.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldMissingNondefaultInvalidOptionNullInvalidName()
		throws Exception {

		String fileName =
			"editable-values-field-missing-non-default-invalid-option-null-" +
				"invalid-name.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent(
					"expected-configuration-values-field-missing-non-default-" +
						"invalid-option-null-invalid-name.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldNondefault() throws Exception {
		String fileName = "editable-values-field-non-default.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent(
					"expected-configuration-values-field-non-default.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldNull() throws Exception {
		String fileName = "editable-values-field-null.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldSegmentExperienceId0Empty()
		throws Exception {

		String fileName = "editable-values-segments-experience-id-0-empty.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldSegmentExperienceIdInvalid()
		throws Exception {

		String fileName = "editable-values-default.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName), 10);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldSetEmpty() throws Exception {
		String fileName = "editable-values-fieldSet-empty.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldSetInvalidJSON()
		throws Exception {

		String fileName = "editable-values-fieldSet-invalid-json.json.txt";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFieldSetInvalidName()
		throws Exception {

		String fileName = "editable-values-fieldSet-invalid-name.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesFreeMarkerFragmentEntryProcessorEmpty()
		throws Exception {

		String fileName =
			"editable-values-FreeMarkerFragmentEntryProcessor-empty.json";

		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"),
				_getFileContent(fileName),
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	@Test
	public void testGetConfigurationValuesNull() throws Exception {
		JSONObject configurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_getFileContent("expected-configuration-default-values.json"));

		JSONObject configurationValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationValues(
				_getFileContent("configuration.json"), null,
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		Assert.assertEquals(
			configurationDefaultValuesJSONObject.toJSONString(),
			configurationValuesJSONObject.toJSONString());
	}

	private String _getFileContent(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}