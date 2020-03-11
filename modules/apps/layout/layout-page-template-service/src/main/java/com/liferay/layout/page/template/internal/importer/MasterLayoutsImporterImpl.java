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

package com.liferay.layout.page.template.internal.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.exception.PageDefinitionValidatorException;
import com.liferay.layout.page.template.importer.MasterLayoutImportEntry;
import com.liferay.layout.page.template.importer.MasterLayoutsImporter;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelper;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelperFactory;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.validator.PageDefinitionValidator;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = MasterLayoutsImporter.class)
public class MasterLayoutsImporterImpl implements MasterLayoutsImporter {

	@Override
	public List<MasterLayoutImportEntry> importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		_masterLayoutImportEntries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			_processMasterLayoutEntries(
				groupId, _getMasterLayoutEntries(zipFile), overwrite);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);

				throw portalException;
			}
		}

		return _masterLayoutImportEntries;
	}

//	private List<FragmentEntryLink> _getFragmentEntryLinks(
//			LayoutStructure layoutStructure, List<String> childrenItemIds)
//		throws PortalException {
//
//		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>();
//
//		for (String childItemId : childrenItemIds) {
//			LayoutStructureItem layoutStructureItem =
//				layoutStructure.getLayoutStructureItem(childItemId);
//
//			if (layoutStructureItem instanceof FragmentLayoutStructureItem) {
//				FragmentLayoutStructureItem fragmentLayoutStructureItem =
//					(FragmentLayoutStructureItem)layoutStructureItem;
//
//				fragmentEntryLinks.add(
//					_fragmentEntryLinkLocalService.getFragmentEntryLink(
//						fragmentLayoutStructureItem.getFragmentEntryLinkId()));
//			}
//
//			List<String> currentChildrenItemIds =
//				layoutStructureItem.getChildrenItemIds();
//
//			fragmentEntryLinks.addAll(
//				_getFragmentEntryLinks(
//					layoutStructure, currentChildrenItemIds));
//		}
//
//		return fragmentEntryLinks;
//	}

	private List<MasterLayoutEntry> _getMasterLayoutEntries(ZipFile zipFile)
		throws IOException {

		List<MasterLayoutEntry> masterLayoutEntries = new ArrayList<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isMasterLayoutFile(zipEntry.getName())) {

				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			MasterPage masterPage = _objectMapper.readValue(
				content, MasterPage.class);

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				PageDefinition pageDefinition = _objectMapper.readValue(
					pageDefinitionJSON, PageDefinition.class);

				masterLayoutEntries.add(
					new MasterLayoutEntry(masterPage, pageDefinition));
			}
			catch (PageDefinitionValidatorException
						pageDefinitionValidatorException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " + masterPage.getName());
				}

				_masterLayoutImportEntries.add(
					new MasterLayoutImportEntry(
						zipEntry.getName(),
						MasterLayoutImportEntry.Status.INVALID));
			}
		}

		return masterLayoutEntries;
	}

	private String _getPageDefinitionJSON(String fileName, ZipFile zipFile)
		throws IOException {

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH) + 1);

		ZipEntry zipEntry = zipFile.getEntry(
			path +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_DEFINITION);

		if (zipEntry == null) {
			return null;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

//	private String _getPageTemplateEntryKey(
//		MasterPage masterPage, ZipEntry zipEntry) {
//
//		String[] pathParts = StringUtil.split(
//			zipEntry.getName(), CharPool.SLASH);
//
//		String pageTemplateEntryKey = _DEFAULT_MASTER_LAYOUT_ENTRY_KEY;
//
//		if (Validator.isNotNull(masterPage.getName())) {
//			pageTemplateEntryKey = masterPage.getName();
//		}
//
//		if (pathParts.length > 1) {
//			pageTemplateEntryKey = pathParts[pathParts.length - 2];
//		}
//
//		pageTemplateEntryKey = StringUtil.toLowerCase(pageTemplateEntryKey);
//
//		pageTemplateEntryKey = StringUtil.replace(
//			pageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);
//
//		return pageTemplateEntryKey;
//	}

	private boolean _isMasterLayoutFile(String fileName) {
		if (fileName.endsWith(
				CharPool.SLASH +
					LayoutPageTemplateExportImportConstants.
						FILE_NAME_MASTER_LAYOUT)) {

			return true;
		}

		return false;
	}

	private void _processMasterLayoutEntries(
			long groupId, List<MasterLayoutEntry> masterLayoutEntries,
			boolean overwrite)
		throws Exception {

		for (MasterLayoutEntry masterLayoutEntry : masterLayoutEntries) {
			MasterPage masterPage = masterLayoutEntry.getMasterPage();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(groupId, masterPage.getName());

			try {
				boolean added = false;

				if (layoutPageTemplateEntry == null) {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							addLayoutPageTemplateEntry(
								groupId, 0, masterPage.getName(),
								LayoutPageTemplateEntryTypeConstants.
									TYPE_MASTER_LAYOUT,
								0, WorkflowConstants.STATUS_APPROVED,
								ServiceContextThreadLocal.getServiceContext());

					added = true;
				}
				else if (overwrite) {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							updateLayoutPageTemplateEntry(
								layoutPageTemplateEntry.
									getLayoutPageTemplateEntryId(),
								masterPage.getName());

					added = true;
				}

				if (added) {
					_processPageDefinition(
						layoutPageTemplateEntry,
						masterLayoutEntry.getPageDefinition());

					_masterLayoutImportEntries.add(
						new MasterLayoutImportEntry(
							layoutPageTemplateEntry.getName(),
							MasterLayoutImportEntry.Status.IMPORTED));
				}
				else {
					_masterLayoutImportEntries.add(
						new MasterLayoutImportEntry(
							layoutPageTemplateEntry.getName(),
							MasterLayoutImportEntry.Status.IGNORED));
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}

				_masterLayoutImportEntries.add(
					new MasterLayoutImportEntry(
						masterPage.getName(),
						MasterLayoutImportEntry.Status.INVALID));
			}
		}
	}

	private void _processPageDefinition(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			PageDefinition pageDefinition)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		if (pageDefinition != null) {
			PageElement pageElement = pageDefinition.getPageElement();

			if ((pageElement.getType() == PageElement.Type.ROOT) &&
				(pageElement.getPageElements() != null)) {

				int position = 0;

				for (PageElement childPageElement :
						pageElement.getPageElements()) {

					_processPageElement(
						layout, layoutStructure, childPageElement,
						rootLayoutStructureItem.getItemId(), position);

					position++;
				}
			}
		}

		_updateLayoutPageTemplateStructure(layout, layoutStructure);

		_updateLayouts(layoutPageTemplateEntry);
	}

	private void _processPageElement(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position)
		throws Exception {

		LayoutStructureItemHelperFactory layoutStructureItemHelperFactory =
			LayoutStructureItemHelperFactory.getInstance();

		LayoutStructureItemHelper layoutStructureItemHelper =
			layoutStructureItemHelperFactory.getLayoutStructureItemHelper(
				pageElement.getType());

		if (layoutStructureItemHelper == null) {
			return;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructureItemHelper.addLayoutStructureItem(
				_fragmentCollectionContributorTracker,
				_fragmentEntryProcessorRegistry, _fragmentEntryValidator,
				layout, layoutStructure, pageElement, parentItemId, position);

		if ((layoutStructureItem == null) ||
			(pageElement.getPageElements() == null)) {

			return;
		}

		int childPosition = 0;

		for (PageElement childPageElement : pageElement.getPageElements()) {
			_processPageElement(
				layout, layoutStructure, childPageElement,
				layoutStructureItem.getItemId(), childPosition);

			childPosition++;
		}
	}

	private void _updateLayoutPageTemplateStructure(
			Layout layout, LayoutStructure layoutStructure)
		throws PortalException {

		long classNameId = _portal.getClassNameId(Layout.class.getName());

		JSONObject jsonObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), classNameId, layout.getPlid());

		if (layoutPageTemplateStructure != null) {
			_layoutPageTemplateStructureLocalService.
				deleteLayoutPageTemplateStructure(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());
		}

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			layout.getUserId(), layout.getGroupId(), classNameId,
			layout.getPlid(), jsonObject.toString(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private void _updateLayouts(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class.getName()), layout.getPlid());

		_layoutCopyHelper.copyLayout(layout, draftLayout);
	}

	private static final String _DEFAULT_MASTER_LAYOUT_ENTRY_KEY = "imported";

	private static final Log _log = LogFactoryUtil.getLog(
		MasterLayoutsImporterImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private List<MasterLayoutImportEntry> _masterLayoutImportEntries;

	@Reference
	private Portal _portal;

	private class MasterLayoutEntry {

		public MasterLayoutEntry(
			MasterPage masterPage, PageDefinition pageDefinition) {

			_masterPage = masterPage;
			_pageDefinition = pageDefinition;
		}

		public MasterPage getMasterPage() {
			return _masterPage;
		}

		public PageDefinition getPageDefinition() {
			return _pageDefinition;
		}

		private final MasterPage _masterPage;
		private final PageDefinition _pageDefinition;

	}

}