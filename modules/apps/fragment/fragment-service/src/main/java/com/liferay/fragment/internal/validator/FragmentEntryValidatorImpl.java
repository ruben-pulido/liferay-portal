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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.JsonReader;

import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidatingException;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.ProblemHandler;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		InputStream configurationJSONSchemaInputStream =
			FragmentEntryValidatorImpl.class.getResourceAsStream(
				"dependencies/configuration-json-schema.json");

		JsonSchema jsonSchema = _jsonValidationService.readSchema(
			configurationJSONSchemaInputStream);

		ProblemHandler problemHandler = ProblemHandler.throwing();

		InputStream configurationInputStream = new ByteArrayInputStream(
			configuration.getBytes());

		try (JsonReader jsonReader = _jsonValidationService.createReader(
				configurationInputStream, jsonSchema, problemHandler)) {

			try {
				jsonReader.readValue();
			}
			catch (JsonValidatingException jve) {
				throw new FragmentEntryConfigurationException(jve);
			}
		}
	}

	private static final JsonValidationService _jsonValidationService =
		JsonValidationService.newInstance();

}