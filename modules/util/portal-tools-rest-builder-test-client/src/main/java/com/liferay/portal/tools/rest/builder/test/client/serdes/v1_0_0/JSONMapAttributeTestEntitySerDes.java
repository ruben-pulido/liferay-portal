/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.JSONMapAttributeTestEntity;
========
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.JSONMapAttributeTestObject;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

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
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
public class JSONMapAttributeTestEntitySerDes {

	public static JSONMapAttributeTestEntity toDTO(String json) {
		JSONMapAttributeTestEntityJSONParser
			jsonMapAttributeTestEntityJSONParser =
				new JSONMapAttributeTestEntityJSONParser();

		return jsonMapAttributeTestEntityJSONParser.parseToDTO(json);
	}

	public static JSONMapAttributeTestEntity[] toDTOs(String json) {
		JSONMapAttributeTestEntityJSONParser
			jsonMapAttributeTestEntityJSONParser =
				new JSONMapAttributeTestEntityJSONParser();

		return jsonMapAttributeTestEntityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		JSONMapAttributeTestEntity jsonMapAttributeTestEntity) {

		if (jsonMapAttributeTestEntity == null) {
========
public class JSONMapAttributeTestObjectSerDes {

	public static JSONMapAttributeTestObject toDTO(String json) {
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToDTO(json);
	}

	public static JSONMapAttributeTestObject[] toDTOs(String json) {
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		JSONMapAttributeTestObject jsonMapAttributeTestObject) {

		if (jsonMapAttributeTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
		if (jsonMapAttributeTestEntity.getDescription() != null) {
========
		if (jsonMapAttributeTestObject.getDescription() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
			sb.append(_escape(jsonMapAttributeTestEntity.getDescription()));
========
			sb.append(_escape(jsonMapAttributeTestObject.getDescription()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
		if (jsonMapAttributeTestEntity.getName() != null) {
========
		if (jsonMapAttributeTestObject.getName() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
			sb.append(_escape(jsonMapAttributeTestEntity.getName()));
========
			sb.append(_escape(jsonMapAttributeTestObject.getName()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
		if (jsonMapAttributeTestEntity.getProperties1() != null) {
========
		if (jsonMapAttributeTestObject.getProperties1() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties1\": ");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
			sb.append(_toJSON(jsonMapAttributeTestEntity.getProperties1()));
		}

		if (jsonMapAttributeTestEntity.getProperties2() != null) {
========
			sb.append(_toJSON(jsonMapAttributeTestObject.getProperties1()));
		}

		if (jsonMapAttributeTestObject.getProperties2() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties2\": ");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
			sb.append(_toJSON(jsonMapAttributeTestEntity.getProperties2()));
========
			sb.append(_toJSON(jsonMapAttributeTestObject.getProperties2()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
		JSONMapAttributeTestEntityJSONParser
			jsonMapAttributeTestEntityJSONParser =
				new JSONMapAttributeTestEntityJSONParser();

		return jsonMapAttributeTestEntityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		JSONMapAttributeTestEntity jsonMapAttributeTestEntity) {

		if (jsonMapAttributeTestEntity == null) {
========
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		JSONMapAttributeTestObject jsonMapAttributeTestObject) {

		if (jsonMapAttributeTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			return null;
		}

		Map<String, String> map = new TreeMap<>();

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
		if (jsonMapAttributeTestEntity.getDescription() == null) {
========
		if (jsonMapAttributeTestObject.getDescription() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			map.put("description", null);
		}
		else {
			map.put(
				"description",
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
				String.valueOf(jsonMapAttributeTestEntity.getDescription()));
		}

		if (jsonMapAttributeTestEntity.getName() == null) {
========
				String.valueOf(jsonMapAttributeTestObject.getDescription()));
		}

		if (jsonMapAttributeTestObject.getName() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			map.put("name", null);
		}
		else {
			map.put(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
				"name", String.valueOf(jsonMapAttributeTestEntity.getName()));
		}

		if (jsonMapAttributeTestEntity.getProperties1() == null) {
========
				"name", String.valueOf(jsonMapAttributeTestObject.getName()));
		}

		if (jsonMapAttributeTestObject.getProperties1() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			map.put("properties1", null);
		}
		else {
			map.put(
				"properties1",
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
				String.valueOf(jsonMapAttributeTestEntity.getProperties1()));
		}

		if (jsonMapAttributeTestEntity.getProperties2() == null) {
========
				String.valueOf(jsonMapAttributeTestObject.getProperties1()));
		}

		if (jsonMapAttributeTestObject.getProperties2() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			map.put("properties2", null);
		}
		else {
			map.put(
				"properties2",
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
				String.valueOf(jsonMapAttributeTestEntity.getProperties2()));
========
				String.valueOf(jsonMapAttributeTestObject.getProperties2()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
		}

		return map;
	}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
	public static class JSONMapAttributeTestEntityJSONParser
		extends BaseJSONParser<JSONMapAttributeTestEntity> {

		@Override
		protected JSONMapAttributeTestEntity createDTO() {
			return new JSONMapAttributeTestEntity();
		}

		@Override
		protected JSONMapAttributeTestEntity[] createDTOArray(int size) {
			return new JSONMapAttributeTestEntity[size];
========
	public static class JSONMapAttributeTestObjectJSONParser
		extends BaseJSONParser<JSONMapAttributeTestObject> {

		@Override
		protected JSONMapAttributeTestObject createDTO() {
			return new JSONMapAttributeTestObject();
		}

		@Override
		protected JSONMapAttributeTestObject[] createDTOArray(int size) {
			return new JSONMapAttributeTestObject[size];
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "properties1")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "properties2")) {
				return true;
			}

			return false;
		}

		@Override
		protected void setField(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
			JSONMapAttributeTestEntity jsonMapAttributeTestEntity,
========
			JSONMapAttributeTestObject jsonMapAttributeTestObject,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
					jsonMapAttributeTestEntity.setDescription(
========
					jsonMapAttributeTestObject.setDescription(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
					jsonMapAttributeTestEntity.setName(
========
					jsonMapAttributeTestObject.setName(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties1")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
					jsonMapAttributeTestEntity.setProperties1(
========
					jsonMapAttributeTestObject.setProperties1(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
						(Map<String, Object>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties2")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestEntitySerDes.java
					jsonMapAttributeTestEntity.setProperties2(
========
					jsonMapAttributeTestObject.setProperties2(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/JSONMapAttributeTestObjectSerDes.java
						(Map<String, Object>)jsonParserFieldValue);
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