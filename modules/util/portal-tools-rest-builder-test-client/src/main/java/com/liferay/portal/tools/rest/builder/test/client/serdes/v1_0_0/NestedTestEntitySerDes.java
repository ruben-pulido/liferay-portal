/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedTestEntity;
========
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedTestObject;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
public class NestedTestEntitySerDes {

	public static NestedTestEntity toDTO(String json) {
		NestedTestEntityJSONParser nestedTestEntityJSONParser =
			new NestedTestEntityJSONParser();

		return nestedTestEntityJSONParser.parseToDTO(json);
	}

	public static NestedTestEntity[] toDTOs(String json) {
		NestedTestEntityJSONParser nestedTestEntityJSONParser =
			new NestedTestEntityJSONParser();

		return nestedTestEntityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NestedTestEntity nestedTestEntity) {
		if (nestedTestEntity == null) {
========
public class NestedTestObjectSerDes {

	public static NestedTestObject toDTO(String json) {
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToDTO(json);
	}

	public static NestedTestObject[] toDTOs(String json) {
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NestedTestObject nestedTestObject) {
		if (nestedTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getDateCreated() != null) {
========
		if (nestedTestObject.getDateCreated() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.getDateCreated()));
========
					nestedTestObject.getDateCreated()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getDateModified() != null) {
========
		if (nestedTestObject.getDateModified() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.getDateModified()));
========
					nestedTestObject.getDateModified()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getDescription() != null) {
========
		if (nestedTestObject.getDescription() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			sb.append(_escape(nestedTestEntity.getDescription()));
========
			sb.append(_escape(nestedTestObject.getDescription()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getId() != null) {
========
		if (nestedTestObject.getId() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			sb.append(nestedTestEntity.getId());
		}

		if (nestedTestEntity.getName() != null) {
========
			sb.append(nestedTestObject.getId());
		}

		if (nestedTestObject.getName() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			sb.append(_escape(nestedTestEntity.getName()));
========
			sb.append(_escape(nestedTestObject.getName()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getTestEntity() != null) {
========
		if (nestedTestObject.getTestObject() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			sb.append("\"testEntity\": ");

			sb.append(String.valueOf(nestedTestEntity.getTestEntity()));
========
			sb.append("\"testObject\": ");

			sb.append(String.valueOf(nestedTestObject.getTestObject()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		NestedTestEntityJSONParser nestedTestEntityJSONParser =
			new NestedTestEntityJSONParser();

		return nestedTestEntityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(NestedTestEntity nestedTestEntity) {
		if (nestedTestEntity == null) {
========
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(NestedTestObject nestedTestObject) {
		if (nestedTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
		if (nestedTestEntity.getDateCreated() == null) {
========
		if (nestedTestObject.getDateCreated() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.getDateCreated()));
		}

		if (nestedTestEntity.getDateModified() == null) {
========
					nestedTestObject.getDateCreated()));
		}

		if (nestedTestObject.getDateModified() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.getDateModified()));
		}

		if (nestedTestEntity.getDescription() == null) {
========
					nestedTestObject.getDateModified()));
		}

		if (nestedTestObject.getDescription() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			map.put("description", null);
		}
		else {
			map.put(
				"description",
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
				String.valueOf(nestedTestEntity.getDescription()));
		}

		if (nestedTestEntity.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(nestedTestEntity.getId()));
		}

		if (nestedTestEntity.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(nestedTestEntity.getName()));
		}

		if (nestedTestEntity.getTestEntity() == null) {
			map.put("testEntity", null);
		}
		else {
			map.put(
				"testEntity", String.valueOf(nestedTestEntity.getTestEntity()));
========
				String.valueOf(nestedTestObject.getDescription()));
		}

		if (nestedTestObject.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(nestedTestObject.getId()));
		}

		if (nestedTestObject.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(nestedTestObject.getName()));
		}

		if (nestedTestObject.getTestObject() == null) {
			map.put("testObject", null);
		}
		else {
			map.put(
				"testObject", String.valueOf(nestedTestObject.getTestObject()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
		}

		return map;
	}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
	public static class NestedTestEntityJSONParser
		extends BaseJSONParser<NestedTestEntity> {

		@Override
		protected NestedTestEntity createDTO() {
			return new NestedTestEntity();
		}

		@Override
		protected NestedTestEntity[] createDTOArray(int size) {
			return new NestedTestEntity[size];
========
	public static class NestedTestObjectJSONParser
		extends BaseJSONParser<NestedTestObject> {

		@Override
		protected NestedTestObject createDTO() {
			return new NestedTestObject();
		}

		@Override
		protected NestedTestObject[] createDTOArray(int size) {
			return new NestedTestObject[size];
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			else if (Objects.equals(jsonParserFieldName, "testEntity")) {
========
			else if (Objects.equals(jsonParserFieldName, "testObject")) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
			NestedTestEntity nestedTestEntity, String jsonParserFieldName,
========
			NestedTestObject nestedTestObject, String jsonParserFieldName,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.setDateCreated(
========
					nestedTestObject.setDateCreated(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.setDateModified(
========
					nestedTestObject.setDateModified(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.setDescription(
========
					nestedTestObject.setDescription(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.setId(
========
					nestedTestObject.setId(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestEntitySerDes.java
					nestedTestEntity.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testEntity")) {
				if (jsonParserFieldValue != null) {
					nestedTestEntity.setTestEntity(
						TestEntitySerDes.toDTO((String)jsonParserFieldValue));
========
					nestedTestObject.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testObject")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setTestObject(
						TestObjectSerDes.toDTO((String)jsonParserFieldValue));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedTestObjectSerDes.java
				}
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
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}