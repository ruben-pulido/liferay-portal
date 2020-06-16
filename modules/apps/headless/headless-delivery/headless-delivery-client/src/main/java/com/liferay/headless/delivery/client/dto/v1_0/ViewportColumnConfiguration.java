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
import com.liferay.headless.delivery.client.serdes.v1_0.ViewportColumnConfigurationSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ViewportColumnConfiguration implements Cloneable {

	public static ViewportColumnConfiguration toDTO(String json) {
		return ViewportColumnConfigurationSerDes.toDTO(json);
	}

	public ViewportColumnConfigurationDefinition getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(
		ViewportColumnConfigurationDefinition landscapeMobile) {

		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<ViewportColumnConfigurationDefinition, Exception>
			landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigurationDefinition landscapeMobile;

	public ViewportColumnConfigurationDefinition getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(
		ViewportColumnConfigurationDefinition portraitMobile) {

		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<ViewportColumnConfigurationDefinition, Exception>
			portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigurationDefinition portraitMobile;

	public ViewportColumnConfigurationDefinition getTablet() {
		return tablet;
	}

	public void setTablet(ViewportColumnConfigurationDefinition tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<ViewportColumnConfigurationDefinition, Exception>
			tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigurationDefinition tablet;

	@Override
	public ViewportColumnConfiguration clone()
		throws CloneNotSupportedException {

		return (ViewportColumnConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ViewportColumnConfiguration)) {
			return false;
		}

		ViewportColumnConfiguration viewportColumnConfiguration =
			(ViewportColumnConfiguration)object;

		return Objects.equals(
			toString(), viewportColumnConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ViewportColumnConfigurationSerDes.toJSON(this);
	}

}