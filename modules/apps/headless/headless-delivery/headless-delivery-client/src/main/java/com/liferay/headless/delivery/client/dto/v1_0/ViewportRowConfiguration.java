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
import com.liferay.headless.delivery.client.serdes.v1_0.ViewportRowConfigurationSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ViewportRowConfiguration implements Cloneable {

	public static ViewportRowConfiguration toDTO(String json) {
		return ViewportRowConfigurationSerDes.toDTO(json);
	}

	public ViewportRowConfigurationDefinition getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(
		ViewportRowConfigurationDefinition landscapeMobile) {

		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<ViewportRowConfigurationDefinition, Exception>
			landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportRowConfigurationDefinition landscapeMobile;

	public ViewportRowConfigurationDefinition getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(
		ViewportRowConfigurationDefinition portraitMobile) {

		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<ViewportRowConfigurationDefinition, Exception>
			portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportRowConfigurationDefinition portraitMobile;

	public ViewportRowConfigurationDefinition getTablet() {
		return tablet;
	}

	public void setTablet(ViewportRowConfigurationDefinition tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<ViewportRowConfigurationDefinition, Exception>
			tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportRowConfigurationDefinition tablet;

	@Override
	public ViewportRowConfiguration clone() throws CloneNotSupportedException {
		return (ViewportRowConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ViewportRowConfiguration)) {
			return false;
		}

		ViewportRowConfiguration viewportRowConfiguration =
			(ViewportRowConfiguration)object;

		return Objects.equals(toString(), viewportRowConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ViewportRowConfigurationSerDes.toJSON(this);
	}

}