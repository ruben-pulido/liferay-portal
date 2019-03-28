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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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

		private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
//							SimpleBeanPropertyFilter.serializeAll()
							SimpleBeanPropertyFilter.serializeAllExcept("childType")
							);
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAllExcept("childType"));
//							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	//	If this works, then I'd also like to update BaseStructuredContentTestCase to test regular DTO -> JSON (via Jackson), send this JSON to client DTO parser which will make a client DTO, then have the client DTO parser take the client DTO to send it back as a JSON, and using that JSON, with jackson, wire it back to a DTO. The two DTOs should be the same. If so.. our serialization is good!

	@Test
	public void testDeserializeSerialize() throws Exception {
		String fileContent = getFileContent("structured-content.json");

		com.liferay.headless.web.experience.dto.v1_0.StructuredContent
			structuredContent1 = _inputObjectMapper.readValue(
				fileContent,
				com.liferay.headless.web.experience.dto.v1_0.StructuredContent.class);

		String json1 = _outputObjectMapper.writeValueAsString(
			structuredContent1);

		StructuredContent structuredContent2 =
			StructuredContentSerDes.toDTO(json1);

		Assert.assertEquals(
			json1, _outputObjectMapper.writeValueAsString(structuredContent2));

		String json2 = StructuredContentSerDes.toJSON(structuredContent2);

		com.liferay.headless.web.experience.dto.v1_0.StructuredContent
			structuredContent3 = _inputObjectMapper.readValue(
			json2,
			com.liferay.headless.web.experience.dto.v1_0.StructuredContent.class);

		String json3 = _outputObjectMapper.writeValueAsString(structuredContent3);

		Assert.assertEquals(json1, json3);
	}

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