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

package com.liferay.media.object.apio.internal.architect.resource.test.matcher;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.util.Optional;

import org.apache.commons.io.IOUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Rubén Pulido
 */
public class AdpativeMediaMatcher extends TypeSafeMatcher<AdaptiveMedia> {

	public static Matcher<AdaptiveMedia> validVariationOf(byte[] bytes) {
		return new AdpativeMediaMatcher(bytes);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"is a valid adaptive media variation of " + _bytes);
	}

	@Override
	protected boolean matchesSafely(AdaptiveMedia adaptiveMedia) {
		URI contentURL = adaptiveMedia.getURI();

		if (contentURL == null) {
			return false;
		}

		byte[] contentBytes;

		try {
			contentBytes = IOUtils.toByteArray(
				new URL(TestPropsValues.PORTAL_URL + contentURL).openStream());
		}
		catch (IOException ioe) {
			return false;
		}

		if (contentBytes.length == 0) {
			return false;
		}

		if (contentBytes.length > _bytes.length) {
			return false;
		}

		Optional heightOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		if (!heightOptional.equals(Optional.of(300)) ||
			!heightOptional.equals(Optional.of(512))) {

			return false;
		}

		Optional widthOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		if (!widthOptional.equals(Optional.of(300)) ||
			!widthOptional.equals(Optional.of(512))) {

			return false;
		}

		Optional contentLengthOptional = adaptiveMedia.getValueOptional(
			AMAttribute.getContentLengthAMAttribute());

		if (!contentLengthOptional.isPresent() ||
			((Long)contentLengthOptional.get() == 0L)) {

			return false;
		}

		Optional resolutionNameOptional = adaptiveMedia.getValueOptional(
			AMAttribute.getConfigurationUuidAMAttribute());

		if (!resolutionNameOptional.equals(Optional.of("Preview-1000x0")) ||
			!resolutionNameOptional.equals(Optional.of("Thumbnail-300x300"))) {

			return false;
		}

		return true;
	}

	private AdpativeMediaMatcher(byte[] bytes) {
		_bytes = bytes;
	}

	private final byte[] _bytes;

}