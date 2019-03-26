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

package com.liferay.headless.web.experience.client.dto.v1_0.parsing;

import com.liferay.headless.web.experience.client.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.client.dto.v1_0.Creator;
import com.liferay.headless.web.experience.client.dto.v1_0.Page;
import com.liferay.headless.web.experience.client.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.client.serdes.v1_0.ContentListElementSerDes;
import com.liferay.headless.web.experience.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.headless.web.experience.client.serdes.v1_0.page.PageSerDes;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class JSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testToPageOfContentListElements() throws Exception {
		String fileContent = getFileContent("page-content-list-elements.json");

		Page<ContentListElement> page = PageSerDes.toPage(
			fileContent, ContentListElementSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(2, page.getTotalCount());

		List<ContentListElement> contentListElements =
			(List<ContentListElement>)page.getItems();

		Assert.assertEquals(
			contentListElements.toString(), 2, contentListElements.size());

		ContentListElement contentListElement1 = contentListElements.get(0);

		Assert.assertEquals(
			"StructuredContent", contentListElement1.getContentType());

		Assert.assertEquals(1.2, contentListElement1.getOrder());

		ContentListElement contentListElement2 = contentListElements.get(1);

		Assert.assertEquals("WikiPage", contentListElement2.getContentType());

		Assert.assertEquals(-3.4, contentListElement2.getOrder());
	}

	@Test
	public void testToPageOfStructuredContents() throws Exception {
		String fileContent = getFileContent("page-structured-contents.json");

		Page page = PageSerDes.toPage(
			fileContent, StructuredContentSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(2, page.getTotalCount());

		Collection<StructuredContent> structuredContents = page.getItems();

		Assert.assertEquals(
			structuredContents.toString(), 2, structuredContents.size());

		for (StructuredContent structuredContent : structuredContents) {
			Assert.assertArrayEquals(
				new String[] {"en-US"},
				structuredContent.getAvailableLanguages());

			Creator creator = structuredContent.getCreator();

			Assert.assertNotNull(creator);
		}
	}

}