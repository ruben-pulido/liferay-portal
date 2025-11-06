/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentElement;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentElementUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class FragmentInstancePageElementDefinitionDTOConverter
	implements DTOConverter
		<FragmentStyledLayoutStructureItem,
		 FragmentInstancePageElementDefinition> {

	@Override
	public String getContentType() {
		return FragmentInstancePageElementDefinition.class.getSimpleName();
	}

	@Override
	public FragmentInstancePageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem)
		throws Exception {

		Long companyId = (Long)dtoConverterContext.getAttribute("companyId");
		Long scopeGroupId = (Long)dtoConverterContext.getAttribute(
			"scopeGroupId");

		if ((companyId == null) || (scopeGroupId == null)) {
			throw new UnsupportedOperationException();
		}

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			throw new UnsupportedOperationException();
		}

		return new FragmentInstancePageElementDefinition() {
			{
				setConfiguration(fragmentEntryLink::getConfiguration);
				setCss(fragmentEntryLink::getCss);
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								fragmentStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							fragmentStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(fragmentStyledLayoutStructureItem::getCustomCSS);
				setDatePropagated(fragmentEntryLink::getLastPropagationDate);
				setDraftFragmentInstanceExternalReferenceCode(
					() -> _getDraftFragmentInstanceExternalReferenceCode(
						fragmentEntryLink));
				setFragmentElements(
					() -> _getFragmentElements(
						companyId, fragmentEntryLink, scopeGroupId));
				setFragmentInstanceExternalReferenceCode(
					fragmentEntryLink::getExternalReferenceCode);
				setFragmentReference(
					() -> {
						FragmentEntry fragmentEntry =
							_fragmentEntryLocalService.
								fetchFragmentEntryByExternalReferenceCode(
									fragmentEntryLink.getFragmentEntryERC(),
									fragmentEntryLink.
										getFragmentEntryGroupId());

						if (fragmentEntry != null) {
							return new FragmentItemExternalReference() {
								{
									setExternalReferenceCode(
										fragmentEntry::
											getExternalReferenceCode);
									setFragmentReferenceType(
										() ->
											FragmentReferenceType.
												FRAGMENT_ITEM_EXTERNAL_REFERENCE);
									setScope(
										() -> {
											if (fragmentEntry.getGroupId() ==
													fragmentEntryLink.
														getGroupId()) {

												return null;
											}

											Group group =
												_groupLocalService.getGroup(
													fragmentEntry.getGroupId());

											return new Scope() {
												{
													setExternalReferenceCode(
														group::
															getExternalReferenceCode);
													setType(() -> Type.SITE);
												}
											};
										});
								}
							};
						}

						Map<String, FragmentEntry> fragmentEntries =
							_fragmentCollectionContributorRegistry.
								getFragmentEntries();

						if (!fragmentEntries.containsKey(
								fragmentEntryLink.getRendererKey())) {

							return null;
						}

						return new DefaultFragmentReference() {
							{
								setDefaultFragmentKey(
									fragmentEntryLink::getRendererKey);
								setFragmentReferenceType(
									() ->
										FragmentReferenceType.
											DEFAULT_FRAGMENT_REFERENCE);
							}
						};
					});
				setFragmentType(
					() -> {
						if (fragmentEntryLink.isTypeComponent()) {
							return FragmentType.BASIC;
						}

						return FragmentType.FORM;
					});
				setHtml(fragmentEntryLink::getHtml);
				setIndexed(fragmentStyledLayoutStructureItem::isIndexed);
				setJs(fragmentEntryLink::getJs);
				setName(fragmentStyledLayoutStructureItem::getName);
				setNamespace(fragmentEntryLink::getNamespace);
				setType(PageElementDefinition.Type.FRAGMENT);
				setUuid(fragmentEntryLink::getUuid);
			}
		};
	}

	private String _getDraftFragmentInstanceExternalReferenceCode(
		FragmentEntryLink fragmentEntryLink) {

		String originalFragmentEntryLinkERC =
			fragmentEntryLink.getOriginalFragmentEntryLinkERC();

		if (Validator.isNull(originalFragmentEntryLinkERC)) {
			return null;
		}

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.
				fetchFragmentEntryLinkByExternalReferenceCode(
					originalFragmentEntryLinkERC,
					fragmentEntryLink.getGroupId());

		if (originalFragmentEntryLink == null) {
			return null;
		}

		return originalFragmentEntryLink.getExternalReferenceCode();
	}

	private FragmentElement[] _getFragmentElements(
		long companyId, FragmentEntryLink fragmentEntryLink,
		long scopeGroupId) {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		if (editableValuesJSONObject == null) {
			return null;
		}

		List<FragmentElement> fragmentElements = new ArrayList<>();

		JSONObject jsonObject = editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (jsonObject == null) {
			return fragmentElements.toArray(new FragmentElement[0]);
		}

		fragmentElements.addAll(
			_getTextFragmentElements(
				companyId,
				EditableFragmentEntryProcessorUtil.getEditableTypes(
					fragmentEntryLink.getHtml()),
				jsonObject, scopeGroupId));

		return fragmentElements.toArray(new FragmentElement[0]);
	}

	private List<FragmentElement> _getTextFragmentElements(
		long companyId, Map<String, String> editableTypes,
		JSONObject jsonObject, long scopeGroupId) {

		return TransformUtil.transform(
			jsonObject.keySet(),
			textId -> new FragmentElement() {
				{
					setId(() -> textId);
					setValue(
						() -> {
							String type = editableTypes.getOrDefault(
								textId, "text");

							if (!Objects.equals(type, "text")) {
								return null;
							}

							return FragmentElementUtil.
								toTextFragmentElementValue(
									companyId, _infoItemServiceRegistry,
									jsonObject.getJSONObject(textId),
									scopeGroupId);
						});
				}
			});
	}

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}