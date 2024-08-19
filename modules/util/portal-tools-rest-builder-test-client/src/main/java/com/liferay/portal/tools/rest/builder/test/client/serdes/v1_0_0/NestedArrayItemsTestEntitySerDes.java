/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedArrayItemsTestEntity;
========
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedArrayItemsTestObject;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
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
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
public class NestedArrayItemsTestEntitySerDes {

	public static NestedArrayItemsTestEntity toDTO(String json) {
		NestedArrayItemsTestEntityJSONParser
			nestedArrayItemsTestEntityJSONParser =
				new NestedArrayItemsTestEntityJSONParser();

		return nestedArrayItemsTestEntityJSONParser.parseToDTO(json);
	}

	public static NestedArrayItemsTestEntity[] toDTOs(String json) {
		NestedArrayItemsTestEntityJSONParser
			nestedArrayItemsTestEntityJSONParser =
				new NestedArrayItemsTestEntityJSONParser();

		return nestedArrayItemsTestEntityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		NestedArrayItemsTestEntity nestedArrayItemsTestEntity) {

		if (nestedArrayItemsTestEntity == null) {
========
public class NestedArrayItemsTestObjectSerDes {

	public static NestedArrayItemsTestObject toDTO(String json) {
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToDTO(json);
	}

	public static NestedArrayItemsTestObject[] toDTOs(String json) {
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		NestedArrayItemsTestObject nestedArrayItemsTestObject) {

		if (nestedArrayItemsTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
		if (nestedArrayItemsTestEntity.getName() != null) {
========
		if (nestedArrayItemsTestObject.getName() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
			sb.append(_escape(nestedArrayItemsTestEntity.getName()));
========
			sb.append(_escape(nestedArrayItemsTestObject.getName()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java

			sb.append("\"");
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
		if (nestedArrayItemsTestEntity.getValues() != null) {
========
		if (nestedArrayItemsTestObject.getValues() != null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			sb.append("[");

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
			for (int i = 0; i < nestedArrayItemsTestEntity.getValues().length;
				 i++) {

				sb.append(nestedArrayItemsTestEntity.getValues()[i]);

				if ((i + 1) < nestedArrayItemsTestEntity.getValues().length) {
========
			for (int i = 0; i < nestedArrayItemsTestObject.getValues().length;
				 i++) {

				sb.append(nestedArrayItemsTestObject.getValues()[i]);

				if ((i + 1) < nestedArrayItemsTestObject.getValues().length) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
		NestedArrayItemsTestEntityJSONParser
			nestedArrayItemsTestEntityJSONParser =
				new NestedArrayItemsTestEntityJSONParser();

		return nestedArrayItemsTestEntityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NestedArrayItemsTestEntity nestedArrayItemsTestEntity) {

		if (nestedArrayItemsTestEntity == null) {
========
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NestedArrayItemsTestObject nestedArrayItemsTestObject) {

		if (nestedArrayItemsTestObject == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			return null;
		}

		Map<String, String> map = new TreeMap<>();

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
		if (nestedArrayItemsTestEntity.getName() == null) {
========
		if (nestedArrayItemsTestObject.getName() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			map.put("name", null);
		}
		else {
			map.put(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
				"name", String.valueOf(nestedArrayItemsTestEntity.getName()));
		}

		if (nestedArrayItemsTestEntity.getValues() == null) {
========
				"name", String.valueOf(nestedArrayItemsTestObject.getName()));
		}

		if (nestedArrayItemsTestObject.getValues() == null) {
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			map.put("values", null);
		}
		else {
			map.put(
				"values",
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
				String.valueOf(nestedArrayItemsTestEntity.getValues()));
========
				String.valueOf(nestedArrayItemsTestObject.getValues()));
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
		}

		return map;
	}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
	public static class NestedArrayItemsTestEntityJSONParser
		extends BaseJSONParser<NestedArrayItemsTestEntity> {

		@Override
		protected NestedArrayItemsTestEntity createDTO() {
			return new NestedArrayItemsTestEntity();
		}

		@Override
		protected NestedArrayItemsTestEntity[] createDTOArray(int size) {
			return new NestedArrayItemsTestEntity[size];
========
	public static class NestedArrayItemsTestObjectJSONParser
		extends BaseJSONParser<NestedArrayItemsTestObject> {

		@Override
		protected NestedArrayItemsTestObject createDTO() {
			return new NestedArrayItemsTestObject();
		}

		@Override
		protected NestedArrayItemsTestObject[] createDTOArray(int size) {
			return new NestedArrayItemsTestObject[size];
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
			NestedArrayItemsTestEntity nestedArrayItemsTestEntity,
========
			NestedArrayItemsTestObject nestedArrayItemsTestObject,
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
					nestedArrayItemsTestEntity.setName(
========
					nestedArrayItemsTestObject.setName(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestEntitySerDes.java
					nestedArrayItemsTestEntity.setValues(
========
					nestedArrayItemsTestObject.setValues(
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/serdes/v1_0_0/NestedArrayItemsTestObjectSerDes.java
						(String[][])jsonParserFieldValue);
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