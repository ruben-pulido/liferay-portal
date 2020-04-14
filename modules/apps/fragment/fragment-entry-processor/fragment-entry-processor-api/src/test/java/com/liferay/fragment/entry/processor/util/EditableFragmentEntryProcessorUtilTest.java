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

package com.liferay.fragment.entry.processor.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class EditableFragmentEntryProcessorUtilTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());
	}

	@Test
	public void testGetEditableTypes() throws Exception {
		Map<String, String> editableTypes =
			EditableFragmentEntryProcessorUtil.getEditableTypes(
				_read("index.html"));

		Assert.assertEquals(editableTypes.toString(), 1, editableTypes.size());
		Assert.assertEquals("text", editableTypes.get("element-text"));
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}