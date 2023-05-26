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

package com.liferay.fragment.entry.processor.editable.internal.parser;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.editable.parser.util.EditableElementParserUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import org.jsoup.nodes.Element;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rubén Pulido
 */
@Component(property = "type=object-action", service = EditableElementParser.class)
public class ActionEditableElementParser implements EditableElementParser {

	@Override
	public String getValue(Element element) {
		String html = element.html();

		if (Validator.isNull(html.trim())) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			return _language.get(resourceBundle, "example-object-action");
		}

		return html;
	}

	@Override
	public void replace(
		Element element, String value, JSONObject configJSONObject) {

		List<Element> elements = element.getElementsByTag("btn");

		if (ListUtil.isEmpty(elements)) {
			return;
		}

		Element replaceableElement = elements.get(0);

		Element bodyElement = EditableElementParserUtil.getDocumentBody(value);

		if (configJSONObject == null) {
			replaceableElement.html(bodyElement.html());

			return;
		}

		EditableElementParserUtil.addAttribute(
			replaceableElement, configJSONObject, "data-class-name-id", "classNameId");
		EditableElementParserUtil.addAttribute(
			replaceableElement, configJSONObject, "data-class-pk", "classPK");
		EditableElementParserUtil.addAttribute(
			replaceableElement, configJSONObject, "data-field-id", "fieldId");

		replaceableElement.html(bodyElement.html());

//		long fileEntryId = 0;
//
//		if (JSONUtil.isValid(value)) {
//			try {
//				JSONObject jsonObject = _jsonFactory.createJSONObject(value);
//
//				fileEntryId = jsonObject.getLong("fileEntryId");
//				value = jsonObject.getString("url");
//			}
//			catch (JSONException jsonException) {
//				_log.error(
//					"Unable to parse JSON value " + value, jsonException);
//
//				value = StringPool.BLANK;
//			}
//		}
//		else {
//			fileEntryId = configJSONObject.getLong("fileEntryId");
//		}

//		String actionURL =
//			"http://localhost:8080/o/c/mycompanyobjects/by-external-reference-code/4c415347-4702-e099-1106-4798b0b080a8/object-actions/myActionOne";

//				"mappedAction": {
//					"className": "com.liferay.object.model.ObjectDefinition#44032",
//					"classNameId": "44115",
//					"classPK": "44178",
//					"fieldId": "ObjectAction_myActionOne"
	}

	@Override
	public void replace(Element element, String value) {

	}

	@Reference
	private Language _language;

}
