/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentElement;
import com.liferay.headless.admin.site.dto.v1_0.FragmentElementValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.TextDefaultValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentElementValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextInlineFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextMappedFragmentValue;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentElementUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition =
				(FragmentInstancePageElementDefinition)
					pageElement.getPageElementDefinition();

		if (fragmentInstancePageElementDefinition == null) {
			return null;
		}

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.
				fetchFragmentEntryLinkByExternalReferenceCode(
					fragmentInstancePageElementDefinition.
						getFragmentInstanceExternalReferenceCode(),
					layoutStructureItemImporterContext.getGroupId());

		if (fragmentEntryLink == null) {
			fragmentEntryLink = _addFragmentEntryLink(
				fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext);
		}
		else {
			fragmentEntryLink = _updateFragmentEntryLink(
				fragmentEntryLink, fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext);
		}

		if (fragmentEntryLink == null) {
			return null;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.addFragmentStyledLayoutStructureItem(
					fragmentEntryLink.getFragmentEntryLinkId(),
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		fragmentStyledLayoutStructureItem.setCssClasses(
			SetUtil.fromArray(
				fragmentInstancePageElementDefinition.getCssClasses()));
		fragmentStyledLayoutStructureItem.setCustomCSS(
			fragmentInstancePageElementDefinition.getCustomCSS());
		fragmentStyledLayoutStructureItem.setIndexed(
			GetterUtil.getBoolean(
				fragmentInstancePageElementDefinition.getIndexed(), true));
		fragmentStyledLayoutStructureItem.setName(
			fragmentInstancePageElementDefinition.getName());

		return fragmentStyledLayoutStructureItem;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentInstancePageElementDefinition,
			layoutStructureItemImporterContext);

		if (fragmentEntry == null) {
			throw new UnsupportedOperationException();
		}

		Layout layout = layoutStructureItemImporterContext.getLayout();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date createDate = serviceContext.getCreateDate();
		String uuid = serviceContext.getUuid();

		try {
			serviceContext.setCreateDate(
				fragmentInstancePageElementDefinition.getDatePropagated());
			serviceContext.setUuid(
				fragmentInstancePageElementDefinition.getUuid());

			return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				fragmentInstancePageElementDefinition.
					getFragmentInstanceExternalReferenceCode(),
				layoutStructureItemImporterContext.getUserId(),
				layout.getGroupId(),
				_getOriginalFragmentEntryLinkERC(
					fragmentInstancePageElementDefinition,
					layoutStructureItemImporterContext),
				fragmentEntry.getExternalReferenceCode(),
				fragmentEntry.getScopeERC(),
				layoutStructureItemImporterContext.getSegmentsExperienceId(),
				layout.getPlid(),
				GetterUtil.getString(
					fragmentInstancePageElementDefinition.getCss()),
				GetterUtil.getString(
					fragmentInstancePageElementDefinition.getHtml()),
				GetterUtil.getString(
					fragmentInstancePageElementDefinition.getJs()),
				GetterUtil.getString(
					fragmentInstancePageElementDefinition.getConfiguration()),
				_getFragmentElementsEditableValues(
					fragmentInstancePageElementDefinition, layoutStructureItemImporterContext),
				fragmentInstancePageElementDefinition.getNamespace(), 0,
				fragmentEntry.getFragmentEntryKey(),
				_getType(fragmentInstancePageElementDefinition),
				serviceContext);
		}
		finally {
			serviceContext.setCreateDate(createDate);
			serviceContext.setUuid(uuid);
		}
	}

	private JSONObject _createBaseFragmentElementJSONObject(
			TextFragmentElementValue textFragmentElementValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (textFragmentElementValue == null) {
			return jsonObject;
		}

		TextDefaultValue textDefaultValue =
			textFragmentElementValue.getTextDefaultValue();
		TextFragmentValue textFragmentValue =
			textFragmentElementValue.getTextFragmentValue();

		if ((textFragmentValue == null) && (textDefaultValue == null)) {
			return jsonObject;
		}

		if (textDefaultValue != null) {
			jsonObject.put("defaultValue", textDefaultValue.getValue());
		}

		if (textFragmentValue == null) {
			return jsonObject;
		}

		if (textFragmentValue instanceof TextInlineFragmentValue) {
			TextInlineFragmentValue textInlineFragmentValue =
				(TextInlineFragmentValue)textFragmentValue;

			FragmentInlineValue inlineValue =
				textInlineFragmentValue.getInlineValue();

			if (inlineValue == null) {
				return jsonObject;
			}

			Map<String, String> valueI18nMap = inlineValue.getValue_i18n();

			if (valueI18nMap == null) {
				return jsonObject;
			}

			for (Map.Entry<String, String> entry : valueI18nMap.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}

			return jsonObject;
		}

		if (!(textFragmentValue instanceof TextMappedFragmentValue)) {
			return jsonObject;
		}

		TextMappedFragmentValue textMappedFragmentValue =
			(TextMappedFragmentValue)textFragmentValue;

		FragmentMappedValue fragmentMappedValue =
			textMappedFragmentValue.getMappedValue();

		if (fragmentMappedValue == null) {
			return jsonObject;
		}

		return _deepMerge(
			jsonObject,
			FragmentElementUtil.getFragmentMappedValueJSONObject(
				layoutStructureItemImporterContext.getCompanyId(),
				fragmentMappedValue.getMapping(),
				layoutStructureItemImporterContext.getInfoItemServiceRegistry(),
				layoutStructureItemImporterContext.getGroupId()));
	}

	private JSONObject _deepMerge(
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws Exception {

		if (jsonObject1 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject2.toString());
		}

		if (jsonObject2 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject1.toString());
		}

		JSONObject jsonObject3 = JSONFactoryUtil.createJSONObject(
			jsonObject1.toString());

		Iterator<String> iterator = jsonObject2.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!jsonObject3.has(key)) {
				jsonObject3.put(key, jsonObject2.get(key));
			}
			else {
				Object value1 = jsonObject1.get(key);
				Object value2 = jsonObject2.get(key);

				if ((value1 instanceof JSONObject) &&
					(value2 instanceof JSONObject)) {

					jsonObject3.put(
						key,
						_deepMerge(
							(JSONObject)value1,
							jsonObject2.getJSONObject(key)));
				}
				else {
					jsonObject3.put(key, value2);
				}
			}
		}

		return jsonObject3;
	}

	private String _getFragmentElementsEditableValues(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		JSONObject fragmentEntryProcessorValuesJSONObject = JSONUtil.put(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			() -> {
				JSONObject editableFragmentEntryProcessorJSONObject =
					_toEditableFragmentEntryProcessorJSONObject(
						fragmentInstancePageElementDefinition.
							getFragmentElements(),
						layoutStructureItemImporterContext);

				if (editableFragmentEntryProcessorJSONObject.length() > 0) {
					return editableFragmentEntryProcessorJSONObject;
				}

				return null;
			});

		JSONObject jsonObject = _deepMerge(
			defaultEditableValuesJSONObject,
			fragmentEntryProcessorValuesJSONObject);

		return jsonObject.toString();
	}

	private FragmentEntry _getFragmentEntry(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		FragmentReference fragmentReference =
			fragmentInstancePageElementDefinition.getFragmentReference();

		if (fragmentReference == null) {
			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				fragmentReference.getFragmentReferenceType(),
				FragmentReference.FragmentReferenceType.
					FRAGMENT_ITEM_EXTERNAL_REFERENCE)) {

			FragmentItemExternalReference fragmentItemExternalReference =
				(FragmentItemExternalReference)fragmentReference;

			long scopeGroupId = layoutStructureItemImporterContext.getGroupId();

			Scope scope = fragmentItemExternalReference.getScope();

			if (scope != null) {
				scopeGroupId = GroupUtil.getGroupId(
					true, true,
					layoutStructureItemImporterContext.getCompanyId(),
					GetterUtil.getString(scope.getExternalReferenceCode()));
			}

			return FragmentEntryLocalServiceUtil.
				fetchFragmentEntryByExternalReferenceCode(
					GetterUtil.getString(
						fragmentItemExternalReference.
							getExternalReferenceCode()),
					scopeGroupId);
		}

		DefaultFragmentReference defaultFragmentReference =
			(DefaultFragmentReference)fragmentReference;

		return FragmentCollectionContributorRegistryUtil.getFragmentEntry(
			GetterUtil.getString(
				defaultFragmentReference.getDefaultFragmentKey()));
	}

	private String _getOriginalFragmentEntryLinkERC(
		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		if (Validator.isNull(
				fragmentInstancePageElementDefinition.
					getDraftFragmentInstanceExternalReferenceCode())) {

			return null;
		}

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.
				fetchFragmentEntryLinkByExternalReferenceCode(
					fragmentInstancePageElementDefinition.
						getDraftFragmentInstanceExternalReferenceCode(),
					layoutStructureItemImporterContext.getGroupId());

		if (fragmentEntryLink == null) {
			return null;
		}

		return fragmentEntryLink.getExternalReferenceCode();
	}

	private int _getType(
		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition) {

		int type = FragmentConstants.TYPE_COMPONENT;

		if (Objects.equals(
				FragmentInstancePageElementDefinition.FragmentType.FORM,
				fragmentInstancePageElementDefinition.getFragmentType())) {

			type = FragmentConstants.TYPE_INPUT;
		}

		return type;
	}

	private JSONObject _toEditableFragmentEntryProcessorJSONObject(
			FragmentElement[] fragmentElements,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentElements == null) {
			return jsonObject;
		}

		for (FragmentElement fragmentElement : fragmentElements) {
			JSONObject fragmentElementJSONObject =
				JSONFactoryUtil.createJSONObject();

			String fragmentElementId = fragmentElement.getId();

			if (Validator.isNull(fragmentElementId)) {
				continue;
			}

			FragmentElementValue fragmentElementValue =
				fragmentElement.getValue();

			if ((fragmentElementValue == null) ||
				(fragmentElementValue.getType() !=
					FragmentElementValue.Type.TEXT)) {

				continue;
			}

			TextFragmentElementValue textFragmentElementValue =
				(TextFragmentElementValue)fragmentElementValue;

			JSONObject baseFragmentElementJSONObject =
				_createBaseFragmentElementJSONObject(
					textFragmentElementValue,
					layoutStructureItemImporterContext);

			jsonObject.put(
				fragmentElementId,
				JSONUtil.merge(
					fragmentElementJSONObject, baseFragmentElementJSONObject));
		}

		return jsonObject;
	}

	private FragmentEntryLink _updateFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink,
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentInstancePageElementDefinition,
			layoutStructureItemImporterContext);
		Layout layout = layoutStructureItemImporterContext.getLayout();

		if ((fragmentEntry == null) ||
			(fragmentEntryLink.getPlid() != layout.getPlid()) ||
			(fragmentEntryLink.getSegmentsExperienceId() !=
				layoutStructureItemImporterContext.getSegmentsExperienceId())) {

			throw new UnsupportedOperationException();
		}

		fragmentEntryLink.setOriginalFragmentEntryLinkERC(
			_getOriginalFragmentEntryLinkERC(
				fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext));

		fragmentEntryLink.setFragmentEntryERC(
			fragmentEntry.getExternalReferenceCode());
		fragmentEntryLink.setFragmentEntryScopeERC(fragmentEntry.getScopeERC());
		fragmentEntryLink.setCss(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getCss()));
		fragmentEntryLink.setHtml(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getHtml()));
		fragmentEntryLink.setJs(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getJs()));
		fragmentEntryLink.setConfiguration(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getConfiguration()));
		fragmentEntryLink.setNamespace(
			fragmentInstancePageElementDefinition.getNamespace());
		fragmentEntryLink.setRendererKey(fragmentEntry.getFragmentEntryKey());
		fragmentEntryLink.setType(
			_getType(fragmentInstancePageElementDefinition));
		fragmentEntryLink.setLastPropagationDate(
			fragmentInstancePageElementDefinition.getDatePropagated());

		return FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLink(
			fragmentEntryLink);
	}

}