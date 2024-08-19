/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestEntity.java
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.JSONMapAttributeTestEntitySerDes;
========
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.JSONMapAttributeTestObjectSerDes;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestObject.java

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestEntity.java
public class JSONMapAttributeTestEntity implements Cloneable, Serializable {

	public static JSONMapAttributeTestEntity toDTO(String json) {
		return JSONMapAttributeTestEntitySerDes.toDTO(json);
========
public class JSONMapAttributeTestObject implements Cloneable, Serializable {

	public static JSONMapAttributeTestObject toDTO(String json) {
		return JSONMapAttributeTestObjectSerDes.toDTO(json);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestObject.java
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Map<String, Object> getProperties1() {
		return properties1;
	}

	public void setProperties1(Map<String, Object> properties1) {
		this.properties1 = properties1;
	}

	public void setProperties1(
		UnsafeSupplier<Map<String, Object>, Exception>
			properties1UnsafeSupplier) {

		try {
			properties1 = properties1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> properties1;

	public Map<String, Object> getProperties2() {
		return properties2;
	}

	public void setProperties2(Map<String, Object> properties2) {
		this.properties2 = properties2;
	}

	public void setProperties2(
		UnsafeSupplier<Map<String, Object>, Exception>
			properties2UnsafeSupplier) {

		try {
			properties2 = properties2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> properties2;

	@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestEntity.java
	public JSONMapAttributeTestEntity clone()
		throws CloneNotSupportedException {

		return (JSONMapAttributeTestEntity)super.clone();
========
	public JSONMapAttributeTestObject clone()
		throws CloneNotSupportedException {

		return (JSONMapAttributeTestObject)super.clone();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestObject.java
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestEntity.java
		if (!(object instanceof JSONMapAttributeTestEntity)) {
			return false;
		}

		JSONMapAttributeTestEntity jsonMapAttributeTestEntity =
			(JSONMapAttributeTestEntity)object;

		return Objects.equals(
			toString(), jsonMapAttributeTestEntity.toString());
========
		if (!(object instanceof JSONMapAttributeTestObject)) {
			return false;
		}

		JSONMapAttributeTestObject jsonMapAttributeTestObject =
			(JSONMapAttributeTestObject)object;

		return Objects.equals(
			toString(), jsonMapAttributeTestObject.toString());
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestObject.java
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestEntity.java
		return JSONMapAttributeTestEntitySerDes.toJSON(this);
========
		return JSONMapAttributeTestObjectSerDes.toJSON(this);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/JSONMapAttributeTestObject.java
	}

}