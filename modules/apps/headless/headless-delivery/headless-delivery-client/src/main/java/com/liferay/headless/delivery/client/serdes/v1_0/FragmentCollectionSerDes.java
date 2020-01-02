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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentCollection;
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
public class FragmentCollectionSerDes {

	public static FragmentCollection toDTO(String json) {
		FragmentCollectionJSONParser fragmentCollectionJSONParser =
			new FragmentCollectionJSONParser();

		return fragmentCollectionJSONParser.parseToDTO(json);
	}

	public static FragmentCollection[] toDTOs(String json) {
		FragmentCollectionJSONParser fragmentCollectionJSONParser =
			new FragmentCollectionJSONParser();

		return fragmentCollectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentCollection fragmentCollection) {
		if (fragmentCollection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentCollection.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(fragmentCollection.getCreator()));
		}

		if (fragmentCollection.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					fragmentCollection.getDateCreated()));

			sb.append("\"");
		}

		if (fragmentCollection.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					fragmentCollection.getDateModified()));

			sb.append("\"");
		}

		if (fragmentCollection.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(fragmentCollection.getDescription()));

			sb.append("\"");
		}

		if (fragmentCollection.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(fragmentCollection.getId());
		}

		if (fragmentCollection.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fragmentCollection.getName()));

			sb.append("\"");
		}

		if (fragmentCollection.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(fragmentCollection.getSiteId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentCollectionJSONParser fragmentCollectionJSONParser =
			new FragmentCollectionJSONParser();

		return fragmentCollectionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentCollection fragmentCollection) {

		if (fragmentCollection == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentCollection.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(fragmentCollection.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				fragmentCollection.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				fragmentCollection.getDateModified()));

		if (fragmentCollection.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(fragmentCollection.getDescription()));
		}

		if (fragmentCollection.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(fragmentCollection.getId()));
		}

		if (fragmentCollection.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fragmentCollection.getName()));
		}

		if (fragmentCollection.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(fragmentCollection.getSiteId()));
		}

		return map;
	}

	public static class FragmentCollectionJSONParser
		extends BaseJSONParser<FragmentCollection> {

		@Override
		protected FragmentCollection createDTO() {
			return new FragmentCollection();
		}

		@Override
		protected FragmentCollection[] createDTOArray(int size) {
			return new FragmentCollection[size];
		}

		@Override
		protected void setField(
			FragmentCollection fragmentCollection, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					fragmentCollection.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
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