/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestEntity.java
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.UnreferencedTestEntitySerDes;
========
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.UnreferencedTestObjectSerDes;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestObject.java

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestEntity.java
public class UnreferencedTestEntity implements Cloneable, Serializable {

	public static UnreferencedTestEntity toDTO(String json) {
		return UnreferencedTestEntitySerDes.toDTO(json);
========
public class UnreferencedTestObject implements Cloneable, Serializable {

	public static UnreferencedTestObject toDTO(String json) {
		return UnreferencedTestObjectSerDes.toDTO(json);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestObject.java
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getPropertyWithHyphens() {
		return propertyWithHyphens;
	}

	public void setPropertyWithHyphens(String propertyWithHyphens) {
		this.propertyWithHyphens = propertyWithHyphens;
	}

	public void setPropertyWithHyphens(
		UnsafeSupplier<String, Exception> propertyWithHyphensUnsafeSupplier) {

		try {
			propertyWithHyphens = propertyWithHyphensUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String propertyWithHyphens;

	@Override
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestEntity.java
	public UnreferencedTestEntity clone() throws CloneNotSupportedException {
		return (UnreferencedTestEntity)super.clone();
========
	public UnreferencedTestObject clone() throws CloneNotSupportedException {
		return (UnreferencedTestObject)super.clone();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestObject.java
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestEntity.java
		if (!(object instanceof UnreferencedTestEntity)) {
			return false;
		}

		UnreferencedTestEntity unreferencedTestEntity =
			(UnreferencedTestEntity)object;

		return Objects.equals(toString(), unreferencedTestEntity.toString());
========
		if (!(object instanceof UnreferencedTestObject)) {
			return false;
		}

		UnreferencedTestObject unreferencedTestObject =
			(UnreferencedTestObject)object;

		return Objects.equals(toString(), unreferencedTestObject.toString());
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestObject.java
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestEntity.java
		return UnreferencedTestEntitySerDes.toJSON(this);
========
		return UnreferencedTestObjectSerDes.toJSON(this);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/UnreferencedTestObject.java
	}

}