/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestEntity.java
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.NestedArrayItemsTestEntitySerDes;
========
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.NestedArrayItemsTestObjectSerDes;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestObject.java

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestEntity.java
public class NestedArrayItemsTestEntity implements Cloneable, Serializable {

	public static NestedArrayItemsTestEntity toDTO(String json) {
		return NestedArrayItemsTestEntitySerDes.toDTO(json);
========
public class NestedArrayItemsTestObject implements Cloneable, Serializable {

	public static NestedArrayItemsTestObject toDTO(String json) {
		return NestedArrayItemsTestObjectSerDes.toDTO(json);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestObject.java
	}

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

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<String[][], Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[][] values;

	@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestEntity.java
	public NestedArrayItemsTestEntity clone()
		throws CloneNotSupportedException {

		return (NestedArrayItemsTestEntity)super.clone();
========
	public NestedArrayItemsTestObject clone()
		throws CloneNotSupportedException {

		return (NestedArrayItemsTestObject)super.clone();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestObject.java
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestEntity.java
		if (!(object instanceof NestedArrayItemsTestEntity)) {
			return false;
		}

		NestedArrayItemsTestEntity nestedArrayItemsTestEntity =
			(NestedArrayItemsTestEntity)object;

		return Objects.equals(
			toString(), nestedArrayItemsTestEntity.toString());
========
		if (!(object instanceof NestedArrayItemsTestObject)) {
			return false;
		}

		NestedArrayItemsTestObject nestedArrayItemsTestObject =
			(NestedArrayItemsTestObject)object;

		return Objects.equals(
			toString(), nestedArrayItemsTestObject.toString());
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestObject.java
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestEntity.java
		return NestedArrayItemsTestEntitySerDes.toJSON(this);
========
		return NestedArrayItemsTestObjectSerDes.toJSON(this);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedArrayItemsTestObject.java
	}

}