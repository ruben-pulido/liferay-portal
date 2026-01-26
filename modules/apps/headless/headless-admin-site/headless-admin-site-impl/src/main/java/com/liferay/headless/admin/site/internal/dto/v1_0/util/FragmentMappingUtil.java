/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValueItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.dto.v1_0.Mapping;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.scope.Scope;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class FragmentMappingUtil {

	public static String getFieldKey(
		InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long layoutPlid, LayoutStructure layoutStructure,
		String layoutStructureItemId, long scopeGroupId
		) {

		String collectionFieldId = jsonObject.getString("collectionFieldId");

		if (Validator.isNotNull(collectionFieldId)) {
		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.getAncestor(
				layoutStructureItemId,
				LayoutDataItemTypeConstants.TYPE_COLLECTION, layoutStructure);

			if (!(layoutStructureItem instanceof CollectionStyledLayoutStructureItem)) {
				return null;
			}

			CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem) layoutStructureItem;

			JSONObject collectionJSONObject =
				collectionStyledLayoutStructureItem.getCollectionJSONObject();

			String itemType = collectionJSONObject.getString("itemType");

			if (itemType == null) {
				return null;
			}

			return _toExternalUniqueId(
				itemType,
				infoItemServiceRegistry,
				collectionFieldId,
				collectionJSONObject.getString("itemSubtype"), scopeGroupId);
		}

		InfoItemReference infoItemReference = null;

		if (Validator.isNotNull(jsonObject.getString("fieldId"))) {

			int classPK = GetterUtil.getInteger
				(jsonObject.getString("classPK"));

			if (classPK > 0) {
				infoItemReference = new InfoItemReference(
				jsonObject.getString("className"),
				new ClassPKInfoItemIdentifier(classPK));
			}
			else {
				infoItemReference = new InfoItemReference(
					jsonObject.getString("className"),
					new ERCInfoItemIdentifier(
						jsonObject.getString("externalReferenceCode"),
						jsonObject.getString("scopeExternalReferenceCode")));
			}

			return _toExternalUniqueId(
				jsonObject.getString("className"),
				infoItemServiceRegistry,
				jsonObject.getString("fieldId"), _getInfoItem(
					infoItemReference, infoItemServiceRegistry),
				null, layoutPlid, scopeGroupId);
		}

		String mappedField = jsonObject.getString("mappedField");

		if (Validator.isNotNull(mappedField)) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_getLayoutPageTemplateEntry(layoutPlid);

			if (layoutPageTemplateEntry == null) {
				return null;
			}

			return _toExternalUniqueId(
				layoutPageTemplateEntry.getClassName(),
				infoItemServiceRegistry,
				mappedField, null,
				layoutPageTemplateEntry, layoutPlid, scopeGroupId);
		}

		return null;
	}

	private static LayoutPageTemplateEntry _getLayoutPageTemplateEntry(long layoutPlid) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(layoutPlid);

			if (layout == null) {
				return null;
			}

			if (layout.isDraftLayout()) {
				layout =
					LayoutLocalServiceUtil.fetchLayout(layout.getClassPK());
			}

			if (layout == null) {
				return null;
			}

			return
			LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntryByPlid(
				layout.getPlid());

	}

	private static String _getInfoItemFormVariationKey(
		InfoItemServiceRegistry infoItemServiceRegistry,
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return null;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation == null) {
			return null;
		}

		return infoItemFormVariation.getKey();
	}

	private static Object _getInfoItem(
		InfoItemReference infoItemReference,
		InfoItemServiceRegistry infoItemServiceRegistry) {
		if (infoItemReference == null) {
			return null;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
				infoItemIdentifier.getInfoItemServiceFilter());

		try {
			return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	// To be used on display_page or item mapping

	private static String _toExternalUniqueId(
		String className, InfoItemServiceRegistry infoItemServiceRegistry, String fieldName,
		Object infoItem, LayoutPageTemplateEntry layoutPageTemplateEntry, long layoutPlid, long scopeGroupId) {

		InfoItemFormProvider infoItemFormProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			return fieldName;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(scopeGroupId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		InfoForm infoForm = null;

		if (infoItem != null) {
			try {
				infoForm = infoItemFormProvider.getInfoForm(infoItem);
			}
			finally {
				ServiceContextThreadLocal.popServiceContext();
			}

			// TODO Can we extract this common code?
			InfoField<?> infoField = infoForm.getInfoField(fieldName);

			if (infoField == null) {
				return fieldName;
			}

			return infoField.getExternalUniqueId();
		}

		//

		if (layoutPageTemplateEntry == null) {
			return fieldName;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return null;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation == null) {
			return fieldName;
		}

		try {
			infoForm = infoItemFormProvider.getInfoForm(
				infoItemFormVariation.getKey(), scopeGroupId);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			_log.error(noSuchFormVariationException);
		}finally {
			ServiceContextThreadLocal.popServiceContext();
		}



//		InfoItemFormProvider<?> infoItemFormProvider =
//			_infoItemServiceRegistry.getFirstInfoItemService(
//				InfoItemFormProvider.class, itemClassName);
//
//		if (infoItemFormProvider == null) {
//			if (_log.isWarnEnabled()) {
//				_log.warn(
//					"Unable to get info item form provider for class " +
//						itemClassName);
//			}
//
//			return Collections.emptyList();
//		}
//
//		InfoForm infoForm = infoItemFormProvider.getInfoForm(
//			String.valueOf(formStyledLayoutStructureItem.getClassTypeId()),
//			groupId);

		//

		InfoField<?> infoField = infoForm.getInfoField(fieldName);

		if (infoField == null) {
			return fieldName;
		}

		return infoField.getExternalUniqueId();
	}

	// To be used on collection item mapping

	private static String _toExternalUniqueId(
		String className,
		InfoItemServiceRegistry infoItemServiceRegistry,
		String fieldName, String formVariationKey, long scopeGroupId) {

		InfoItemFormProvider infoItemFormProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			return fieldName;
		}

		InfoField<?> infoField = null;

		try {
			InfoForm infoForm = infoItemFormProvider.getInfoForm(
				formVariationKey, scopeGroupId);

			infoField = infoForm.getInfoField(fieldName);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormVariationException);
			}
		}

		if (infoField == null) {
			return fieldName;
		}

		return infoField.getExternalUniqueId();
	}

	public static FragmentMappedValueItemReference
			getFragmentMappedValueItemReference(
				long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
				JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (!jsonObject.has("collectionFieldId") &&
			!jsonObject.has("mappedField")) {

			return _getFragmentMappedValueItemExternalReference(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		FragmentMappedValueItemContextReference
			fragmentMappedValueItemContextReference =
				new FragmentMappedValueItemContextReference();

		FragmentMappedValueItemContextReference.ContextSource contextSource;

		if (jsonObject.has("collectionFieldId")) {
			contextSource =
				FragmentMappedValueItemContextReference.ContextSource.
					COLLECTION_ITEM;
		}
		else {
			contextSource =
				FragmentMappedValueItemContextReference.ContextSource.
					DISPLAY_PAGE_ITEM;
		}

		fragmentMappedValueItemContextReference.setContextSource(
			() -> contextSource);
		fragmentMappedValueItemContextReference.setType(
			() -> FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE);

		return fragmentMappedValueItemContextReference;
	}

	public static JSONObject getFragmentMappedValueJSONObject(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			Mapping mapping, long scopeGroupId)
		throws PortalException {

		if (mapping == null) {
			return null;
		}

		FragmentMappedValueItemReference fragmentMappedValueItemReference =
			mapping.getItemReference();

		if (fragmentMappedValueItemReference == null) {
			return null;
		}

		String fieldKey = mapping.getFieldKey();

		if (fragmentMappedValueItemReference instanceof
				FragmentMappedValueItemContextReference) {

			if (Validator.isNull(fieldKey)) {
				return null;
			}

			FragmentMappedValueItemContextReference
				fragmentMappedValueItemContextReference =
					(FragmentMappedValueItemContextReference)
						fragmentMappedValueItemReference;

			FragmentMappedValueItemContextReference.ContextSource
				contextSource =
					fragmentMappedValueItemContextReference.getContextSource();

			if (contextSource ==
					FragmentMappedValueItemContextReference.ContextSource.
						COLLECTION_ITEM) {

				return JSONUtil.put("collectionFieldId", fieldKey);
			}

			if (contextSource ==
					FragmentMappedValueItemContextReference.ContextSource.
						DISPLAY_PAGE_ITEM) {

				return JSONUtil.put("mappedField", fieldKey);
			}

			return null;
		}

		if (!(fragmentMappedValueItemReference instanceof
				FragmentMappedValueItemExternalReference)) {

			return null;
		}

		FragmentMappedValueItemExternalReference
			fragmentMappedValueItemExternalReference =
				(FragmentMappedValueItemExternalReference)
					fragmentMappedValueItemReference;

		String className =
			fragmentMappedValueItemExternalReference.getClassName();

		if (Validator.isNull(className) ||
			Validator.isNull(
				fragmentMappedValueItemExternalReference.
					getExternalReferenceCode())) {

			return null;
		}

		if (Objects.equals(className, Layout.class.getName())) {
			return JSONUtil.put(
				"layout",
				LayoutUtil.getMappedLayoutJSONObject(
					companyId,
					fragmentMappedValueItemExternalReference.
						getExternalReferenceCode(),
					fragmentMappedValueItemExternalReference.getScope(),
					scopeGroupId));
		}

		return InfoItemUtil.getMappedItemJSONObject(
			fragmentMappedValueItemExternalReference.getClassName(),
			fragmentMappedValueItemExternalReference.getExternalReferenceCode(),
			fieldKey, infoItemServiceRegistry,
			fragmentMappedValueItemExternalReference.getScope(), scopeGroupId);
	}

	public static boolean isMappedValue(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		if ((jsonObject.has("classNameId") && jsonObject.has("classPK")) ||
			(jsonObject.has("externalReferenceCode") &&
			 jsonObject.has("fieldId"))) {

			return true;
		}

		if (jsonObject.has("collectionFieldId") || jsonObject.has("layout") ||
			jsonObject.has("mappedField")) {

			return true;
		}

		return false;
	}

	public static FragmentMappedValue toFragmentMappedValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long layoutPlid, LayoutStructure layoutStructure,
		String layoutStructureItemId, long scopeGroupId)
		throws Exception {

		FragmentMappedValueItemReference fragmentMappedValueItemReference =
			getFragmentMappedValueItemReference(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentMappedValueItemReference == null) {
			return null;
		}

		FragmentMappedValue fragmentMappedValue = new FragmentMappedValue();

		fragmentMappedValue.setMapping(
			() -> new Mapping() {
				{
					setFieldKey(
						() -> FragmentMappingUtil.getFieldKey(
							infoItemServiceRegistry, jsonObject, layoutPlid,
							layoutStructure, layoutStructureItemId, scopeGroupId));
					setItemReference(() -> fragmentMappedValueItemReference);
				}
			});

		return fragmentMappedValue;
	}

	private static FragmentMappedValueItemExternalReference
			_getFragmentMappedValueItemExternalReference(
				long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
				JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		String fieldId = jsonObject.getString("fieldId");
		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");

		if (Validator.isNull(fieldId) && (layoutJSONObject == null)) {
			return null;
		}

		if (layoutJSONObject != null) {
			return _toLayoutFragmentMappedValueItemExternalReference(
				companyId, layoutJSONObject, scopeGroupId);
		}

		String className = _toItemClassName(jsonObject);

		if (className == null) {
			return null;
		}

		FragmentMappedValueItemExternalReference
			fragmentMappedValueItemExternalReference =
				new FragmentMappedValueItemExternalReference();

		fragmentMappedValueItemExternalReference.setClassName(() -> className);
		fragmentMappedValueItemExternalReference.setType(
			() ->
				FragmentMappedValueItemReference.Type.ITEM_EXTERNAL_REFERENCE);

		if (jsonObject.has("classPK")) {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				InfoItemUtil.getERCInfoItemIdentifier(//
					className, jsonObject.getLong("classPK"),
					infoItemServiceRegistry, scopeGroupId);

			if (ercInfoItemIdentifier != null) {
				fragmentMappedValueItemExternalReference.
					setExternalReferenceCode(
						ercInfoItemIdentifier::getExternalReferenceCode);
				fragmentMappedValueItemExternalReference.setScope(
					() -> ItemScopeUtil.getItemScope(
						companyId,
						ercInfoItemIdentifier.getScopeExternalReferenceCode(),
						scopeGroupId));

				return fragmentMappedValueItemExternalReference;
			}
		}

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		fragmentMappedValueItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		fragmentMappedValueItemExternalReference.setScope(
			() -> ItemScopeUtil.getItemScope(
				companyId, jsonObject.getString("scopeExternalReferenceCode"),
				scopeGroupId));

		return fragmentMappedValueItemExternalReference;
	}

	private static String _getLayoutExternalReferenceCode(
		Layout layout, JSONObject layoutJSONObject) {

		if (layout != null) {
			return layout.getExternalReferenceCode();
		}

		return layoutJSONObject.getString("externalReferenceCode");
	}

	private static Scope _getLayoutScope(
			long companyId, Layout layout, JSONObject layoutJSONObject,
			long scopeGroupId)
		throws Exception {

		if (layout != null) {
			return ItemScopeUtil.getItemScope(
				layout.getGroupId(), scopeGroupId);
		}

		return ItemScopeUtil.getItemScope(
			companyId, layoutJSONObject.getString("scopeExternalReferenceCode"),
			scopeGroupId);
	}

	private static String _toItemClassName(JSONObject jsonObject) {
		String classNameIdString = jsonObject.getString("classNameId");

		if (Validator.isNull(classNameIdString)) {
			return null;
		}

		long classNameId = 0;

		try {
			classNameId = Long.parseLong(classNameIdString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class name could not be set since class name " +
							"ID %s could not be parsed to a long",
						classNameIdString),
					numberFormatException);
			}

			return null;
		}

		String className = null;

		try {
			className = PortalUtil.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item class name could not be set since no class name " +
						"could be obtained for class name ID " + classNameId,
					exception);
			}

			return null;
		}

		return className;
	}

	private static FragmentMappedValueItemExternalReference
			_toLayoutFragmentMappedValueItemExternalReference(
				long companyId, JSONObject layoutJSONObject, long scopeGroupId)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			layoutJSONObject.getLong("groupId"),
			layoutJSONObject.getBoolean("privateLayout"),
			layoutJSONObject.getLong("layoutId"));

		String externalReferenceCode = _getLayoutExternalReferenceCode(
			layout, layoutJSONObject);

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		FragmentMappedValueItemExternalReference
			fragmentMappedValueItemExternalReference =
				new FragmentMappedValueItemExternalReference();

		fragmentMappedValueItemExternalReference.setClassName(
			Layout.class::getName);
		fragmentMappedValueItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		fragmentMappedValueItemExternalReference.setScope(
			() -> _getLayoutScope(
				companyId, layout, layoutJSONObject, scopeGroupId));
		fragmentMappedValueItemExternalReference.setType(
			() ->
				FragmentMappedValueItemReference.Type.ITEM_EXTERNAL_REFERENCE);

		return fragmentMappedValueItemExternalReference;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentMappingUtil.class);

}