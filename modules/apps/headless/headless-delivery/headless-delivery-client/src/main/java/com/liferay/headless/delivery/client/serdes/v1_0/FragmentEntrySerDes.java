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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentEntry;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentEntrySerDes {

	public static FragmentEntry toDTO(String json) {
		FragmentEntryJSONParser fragmentEntryJSONParser =
			new FragmentEntryJSONParser();

		return fragmentEntryJSONParser.parseToDTO(json);
	}

	public static FragmentEntry[] toDTOs(String json) {
		FragmentEntryJSONParser fragmentEntryJSONParser =
			new FragmentEntryJSONParser();

		return fragmentEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentEntry fragmentEntry) {
		if (fragmentEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentEntry.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getConfiguration()));

			sb.append("\"");
		}

		if (fragmentEntry.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getContent()));

			sb.append("\"");
		}

		if (fragmentEntry.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(fragmentEntry.getCreator()));
		}

		if (fragmentEntry.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getCss()));

			sb.append("\"");
		}

		if (fragmentEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(fragmentEntry.getDateCreated()));

			sb.append("\"");
		}

		if (fragmentEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					fragmentEntry.getDateModified()));

			sb.append("\"");
		}

		if (fragmentEntry.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getDescription()));

			sb.append("\"");
		}

		if (fragmentEntry.getHtml() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"html\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getHtml()));

			sb.append("\"");
		}

		if (fragmentEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(fragmentEntry.getId());
		}

		if (fragmentEntry.getJs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"js\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getJs()));

			sb.append("\"");
		}

		if (fragmentEntry.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntry.getName()));

			sb.append("\"");
		}

		if (fragmentEntry.getUsageCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usageCount\": ");

			sb.append(fragmentEntry.getUsageCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentEntryJSONParser fragmentEntryJSONParser =
			new FragmentEntryJSONParser();

		return fragmentEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FragmentEntry fragmentEntry) {
		if (fragmentEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentEntry.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(fragmentEntry.getConfiguration()));
		}

		if (fragmentEntry.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(fragmentEntry.getContent()));
		}

		if (fragmentEntry.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(fragmentEntry.getCreator()));
		}

		if (fragmentEntry.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(fragmentEntry.getCss()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(fragmentEntry.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(fragmentEntry.getDateModified()));

		if (fragmentEntry.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(fragmentEntry.getDescription()));
		}

		if (fragmentEntry.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(fragmentEntry.getHtml()));
		}

		if (fragmentEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(fragmentEntry.getId()));
		}

		if (fragmentEntry.getJs() == null) {
			map.put("js", null);
		}
		else {
			map.put("js", String.valueOf(fragmentEntry.getJs()));
		}

		if (fragmentEntry.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fragmentEntry.getName()));
		}

		if (fragmentEntry.getUsageCount() == null) {
			map.put("usageCount", null);
		}
		else {
			map.put(
				"usageCount", String.valueOf(fragmentEntry.getUsageCount()));
		}

		return map;
	}

	public static class FragmentEntryJSONParser
		extends BaseJSONParser<FragmentEntry> {

		@Override
		protected FragmentEntry createDTO() {
			return new FragmentEntry();
		}

		@Override
		protected FragmentEntry[] createDTOArray(int size) {
			return new FragmentEntry[size];
		}

		@Override
		protected void setField(
			FragmentEntry fragmentEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setConfiguration(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setHtml((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setJs((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "usageCount")) {
				if (jsonParserFieldValue != null) {
					fragmentEntry.setUsageCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}