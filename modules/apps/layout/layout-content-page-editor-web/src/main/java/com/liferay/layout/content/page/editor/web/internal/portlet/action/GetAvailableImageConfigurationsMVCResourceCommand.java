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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_available_image_configurations"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableImageConfigurationsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		if (fileEntryId == 0) {
			fileEntryId = _getFileEntryId(resourceRequest);
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		Image image = ImageToolUtil.getImage(fileEntry.getContentStream());

		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"label", LanguageUtil.get(httpServletRequest, "auto")
			).put(
				"size", fileEntry.getSize() / 1000
			).put(
				"value", "auto"
			).put(
				"width", image.getWidth()
			));

		Map<String, String> mediaQueriesMap = new HashMap<>();

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			fileEntry);

		for (MediaQuery mediaQuery : mediaQueries) {
			List<Condition> conditions = mediaQuery.getConditions();

			StringBundler sb = new StringBundler();

			for (Condition condition : conditions) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(condition.getAttribute());
				sb.append(StringPool.COLON);
				sb.append(condition.getValue());
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (conditions.indexOf(condition) != (conditions.size() - 1)) {
					sb.append(" and ");
				}
			}

			mediaQueriesMap.put(mediaQuery.getSrc(), sb.toString());
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		List<AMImageEntry> amImageEntries =
			_amImageEntryLocalService.getAMImageEntries(
				fileVersion.getFileVersionId());

		for (AMImageEntry amImageEntry : amImageEntries) {
			JSONObject jsonObject = JSONUtil.put(
				"label", amImageEntry.getConfigurationUuid()
			).put(
				"size", amImageEntry.getSize() / 1000
			).put(
				"value", amImageEntry.getConfigurationUuid()
			).put(
				"width", amImageEntry.getWidth()
			);

			Optional<AMImageConfigurationEntry>
				amImageConfigurationEntryOptional =
					_amImageConfigurationHelper.getAMImageConfigurationEntry(
						fileEntry.getCompanyId(),
						amImageEntry.getConfigurationUuid());

			if (amImageConfigurationEntryOptional.isPresent()) {
				AMImageConfigurationEntry amImageConfigurationEntry =
					amImageConfigurationEntryOptional.get();

				URI uri = _amImageURLFactory.createFileEntryURL(
					fileEntry.getFileVersion(), amImageConfigurationEntry);

				jsonObject.put(
					"mediaQuery", mediaQueriesMap.get(uri.toString())
				).put(
					"url", uri.toString()
				);
			}

			jsonArray.put(jsonObject);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private long _getFileEntryId(ResourceRequest resourceRequest) {
		String className = ParamUtil.getString(resourceRequest, "className");

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return 0;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				infoItemIdentifier.getInfoItemServiceFilter());

		if (infoItemObjectProvider == null) {
			return 0;
		}

		Object object = null;

		try {
			object = infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			_log.warn(
				"Unable to get info item for info item identifier " +
					infoItemIdentifier);
		}

		if (object == null) {
			return 0;
		}

		String fieldId = ParamUtil.getString(resourceRequest, "fieldId");

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoItemFieldValue(object, fieldId);

		if (infoFieldValue == null) {
			return 0;
		}

		Object value = infoFieldValue.getValue();

		if (value == null) {
			return 0;
		}

		if (value instanceof WebImage) {
			WebImage webImage = (WebImage)value;

			InfoItemReference infoItemReference =
				webImage.getInfoItemReference();

			if (infoItemReference == null) {
				return 0;
			}

			InfoItemIdentifier webImageInfoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			if (webImageInfoItemIdentifier == null) {
				return 0;
			}

			if (webImageInfoItemIdentifier instanceof
					ClassPKInfoItemIdentifier) {

				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)webImageInfoItemIdentifier;

				return classPKInfoItemIdentifier.getClassPK();
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetAvailableImageConfigurationsMVCResourceCommand.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

	@Reference
	private Portal _portal;

}