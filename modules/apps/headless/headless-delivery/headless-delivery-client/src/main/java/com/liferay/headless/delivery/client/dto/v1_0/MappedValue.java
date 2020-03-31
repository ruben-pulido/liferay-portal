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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.MappedValueSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MappedValue implements Cloneable {

	public InlineValue getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(InlineValue defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDefaultValue(
		UnsafeSupplier<InlineValue, Exception> defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected InlineValue defaultValue;

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public void setMapping(
		UnsafeSupplier<Mapping, Exception> mappingUnsafeSupplier) {

		try {
			mapping = mappingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Mapping mapping;

	@Override
	public MappedValue clone() throws CloneNotSupportedException {
		return (MappedValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MappedValue)) {
			return false;
		}

		MappedValue mappedValue = (MappedValue)object;

		return Objects.equals(toString(), mappedValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MappedValueSerDes.toJSON(this);
	}

}