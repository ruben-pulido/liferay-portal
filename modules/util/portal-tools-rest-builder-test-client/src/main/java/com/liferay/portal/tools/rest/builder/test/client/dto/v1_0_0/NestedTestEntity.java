/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.NestedTestEntitySerDes;
========
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.NestedTestObjectSerDes;
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
public class NestedTestEntity implements Cloneable, Serializable {

	public static NestedTestEntity toDTO(String json) {
		return NestedTestEntitySerDes.toDTO(json);
========
public class NestedTestObject implements Cloneable, Serializable {

	public static NestedTestObject toDTO(String json) {
		return NestedTestObjectSerDes.toDTO(json);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

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

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
	public TestEntity getTestEntity() {
		return testEntity;
	}

	public void setTestEntity(TestEntity testEntity) {
		this.testEntity = testEntity;
	}

	public void setTestEntity(
		UnsafeSupplier<TestEntity, Exception> testEntityUnsafeSupplier) {

		try {
			testEntity = testEntityUnsafeSupplier.get();
========
	public TestObject getTestObject() {
		return testObject;
	}

	public void setTestObject(TestObject testObject) {
		this.testObject = testObject;
	}

	public void setTestObject(
		UnsafeSupplier<TestObject, Exception> testObjectUnsafeSupplier) {

		try {
			testObject = testObjectUnsafeSupplier.get();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
	protected TestEntity testEntity;

	@Override
	public NestedTestEntity clone() throws CloneNotSupportedException {
		return (NestedTestEntity)super.clone();
========
	protected TestObject testObject;

	@Override
	public NestedTestObject clone() throws CloneNotSupportedException {
		return (NestedTestObject)super.clone();
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
		if (!(object instanceof NestedTestEntity)) {
			return false;
		}

		NestedTestEntity nestedTestEntity = (NestedTestEntity)object;

		return Objects.equals(toString(), nestedTestEntity.toString());
========
		if (!(object instanceof NestedTestObject)) {
			return false;
		}

		NestedTestObject nestedTestObject = (NestedTestObject)object;

		return Objects.equals(toString(), nestedTestObject.toString());
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
<<<<<<<< HEAD:modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestEntity.java
		return NestedTestEntitySerDes.toJSON(this);
========
		return NestedTestObjectSerDes.toJSON(this);
>>>>>>>> 16dddedda3376 (LPD-31166 Following service builder pattern to deploy and test rest builder):modules/util/portal-tools-rest-builder-test-client/src/main/java/com/liferay/portal/tools/rest/builder/test/client/dto/v1_0_0/NestedTestObject.java
	}

}