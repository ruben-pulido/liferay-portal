/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rubén Pulido
 */
public class YMLRESTOpenAPIPutDescriptionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/rest-openapi.yaml")) {
			return content;
		}

		Map<String, Set<String>> writablePropertyNamesMap =
			_getWritablePropertyNamesMap(content);

		_checkPutDescriptions(fileName, content, writablePropertyNamesMap);

		return content;
	}

	private void _checkMutabilitySentence(
		String description, int lineNumber, String fileName, String schemaName,
		Map<String, Set<String>> writablePropertyNamesMap) {

		Matcher matcher = _mutabilitySentencePattern.matcher(description);

		if (!matcher.find()) {
			addMessage(
				fileName,
				"The mutability sentence does not match the required format.",
				lineNumber);

			return;
		}

		if (schemaName == null) {
			return;
		}

		Set<String> writablePropertyNames = writablePropertyNamesMap.get(
			schemaName);

		if (writablePropertyNames == null) {
			return;
		}

		Set<String> listedPropertyNames = new TreeSet<>();

		listedPropertyNames.addAll(_getBacktickedNames(matcher.group(1)));
		listedPropertyNames.addAll(_getBacktickedNames(matcher.group(2)));

		Set<String> missingPropertyNames = new TreeSet<>(writablePropertyNames);

		missingPropertyNames.removeAll(listedPropertyNames);

		Set<String> extraPropertyNames = new TreeSet<>(listedPropertyNames);

		extraPropertyNames.removeAll(writablePropertyNames);

		if (missingPropertyNames.isEmpty() && extraPropertyNames.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("The mutability sentence must list every writable field of ");
		sb.append("'");
		sb.append(schemaName);
		sb.append("' as honored or ignored.");

		if (!missingPropertyNames.isEmpty()) {
			sb.append(" Missing: ");
			sb.append(StringUtil.merge(missingPropertyNames, ", "));
			sb.append(".");
		}

		if (!extraPropertyNames.isEmpty()) {
			sb.append(" Not writable fields: ");
			sb.append(StringUtil.merge(extraPropertyNames, ", "));
			sb.append(".");
		}

		addMessage(fileName, sb.toString(), lineNumber);
	}

	private void _checkPutDescriptions(
		String fileName, String content,
		Map<String, Set<String>> writablePropertyNamesMap) {

		String[] lines = content.split("\n", -1);

		for (int i = 0; i < lines.length; i++) {
			if (!StringUtil.equals(lines[i].trim(), "put:")) {
				continue;
			}

			int putIndent = _getIndent(lines[i]);

			String description = null;
			int descriptionLineNumber = -1;
			String schemaName = null;

			for (int j = i + 1; j < lines.length; j++) {
				String line = lines[j];

				if (line.isEmpty()) {
					continue;
				}

				if ((_getIndent(line) <= putIndent) &&
					!line.trim(
					).isEmpty()) {

					break;
				}

				String trimmedLine = line.trim();

				if (StringUtil.equals(trimmedLine, "description:") &&
					(description == null)) {

					descriptionLineNumber = j + 2;

					int descriptionIndent = _getIndent(line);

					StringBundler sb = new StringBundler();

					for (int k = j + 1;
						 (k < lines.length) &&
						 (_getIndent(lines[k]) > descriptionIndent); k++) {

						sb.append(lines[k].trim());
						sb.append(StringPool.SPACE);
					}

					description = sb.toString();
				}
				else if (trimmedLine.startsWith("$ref:") &&
						 (schemaName == null)) {

					schemaName = _getLocalSchemaName(trimmedLine);
				}
			}

			if ((description == null) || !description.contains("On update")) {
				continue;
			}

			_checkMutabilitySentence(
				description, descriptionLineNumber, fileName, schemaName,
				writablePropertyNamesMap);
		}
	}

	private Set<String> _getBacktickedNames(String text) {
		Set<String> names = new TreeSet<>();

		Matcher matcher = _backtickedNamePattern.matcher(text);

		while (matcher.find()) {
			names.add(matcher.group(1));
		}

		return names;
	}

	private int _getIndent(String line) {
		int indent = 0;

		while ((indent < line.length()) && (line.charAt(indent) == ' ')) {
			indent++;
		}

		return indent;
	}

	private String _getKey(String trimmedLine) {
		int pos = trimmedLine.indexOf(StringPool.COLON);

		if (pos == -1) {
			return trimmedLine;
		}

		return trimmedLine.substring(0, pos);
	}

	private String _getLocalSchemaName(String refLine) {
		Matcher matcher = _localSchemaRefPattern.matcher(refLine);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private Map<String, Set<String>> _getWritablePropertyNamesMap(
		String content) {

		Map<String, Set<String>> writablePropertyNamesMap = new HashMap<>();

		String[] lines = content.split("\n", -1);

		boolean componentsSection = false;
		boolean schemasSection = false;
		boolean propertiesSection = false;

		String schemaName = null;
		String propertyName = null;
		boolean propertyReadOnly = false;
		Set<String> writablePropertyNames = null;

		for (String line : lines) {
			if (line.trim(
				).isEmpty()) {

				continue;
			}

			int indent = _getIndent(line);
			String trimmedLine = line.trim();

			if ((indent <= 16) && (propertyName != null) && !propertyReadOnly &&
				(writablePropertyNames != null)) {

				writablePropertyNames.add(propertyName);

				propertyName = null;
				propertyReadOnly = false;
			}

			if (indent == 0) {
				componentsSection = StringUtil.equals(
					trimmedLine, "components:");
				schemasSection = false;
				propertiesSection = false;
				schemaName = null;
			}
			else if (indent == 4) {
				schemasSection =
					componentsSection &&
					StringUtil.equals(trimmedLine, "schemas:");
				propertiesSection = false;
				schemaName = null;
			}
			else if (indent == 8) {
				if (schemasSection) {
					schemaName = _getKey(trimmedLine);
					writablePropertyNames = new TreeSet<>();

					writablePropertyNamesMap.put(
						schemaName, writablePropertyNames);
				}

				propertiesSection = false;
			}
			else if (indent == 12) {
				propertiesSection =
					(schemaName != null) &&
					StringUtil.equals(trimmedLine, "properties:");
			}
			else if ((indent == 16) && propertiesSection) {
				propertyName = _getKey(trimmedLine);
				propertyReadOnly = false;
			}
			else if ((indent >= 20) && (propertyName != null) &&
					 StringUtil.equals(trimmedLine, "readOnly: true")) {

				propertyReadOnly = true;
			}
		}

		if ((propertyName != null) && !propertyReadOnly &&
			(writablePropertyNames != null)) {

			writablePropertyNames.add(propertyName);
		}

		return writablePropertyNamesMap;
	}

	private static final Pattern _backtickedNamePattern = Pattern.compile(
		"`(\\w+)`");
	private static final Pattern _localSchemaRefPattern = Pattern.compile(
		"#/components/schemas/(\\w+)");
	private static final Pattern _mutabilitySentencePattern = Pattern.compile(
		"On update, (.+?) (?:is|are) honored; any values sent for (.+?) " +
			"are ignored\\.");

}