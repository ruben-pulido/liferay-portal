/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.headless.admin.fragment.dto.v1_0.ResourceFolder;
import com.liferay.headless.admin.fragment.internal.util.EnabledUtil;
import com.liferay.headless.admin.fragment.resource.v1_0.ResourceFolderResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.common.spi.util.GroupUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/resource-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = ResourceFolderResource.class
)
public class ResourceFolderResourceImpl extends BaseResourceFolderResourceImpl {

	@Override
	public void deleteSiteResourceFolder(
			String siteExternalReferenceCode,
			String resourceFolderExternalReferenceCode)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getStagingAwareGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		Folder folder = _dlAppLocalService.getFolderByExternalReferenceCode(
			resourceFolderExternalReferenceCode, groupId);

		_dlAppService.deleteFolder(folder.getFolderId());
	}

	@Override
	public Page<ResourceFolder> getSiteFragmentSetResourceFoldersPage(
			String siteExternalReferenceCode,
			String fragmentSetExternalReferenceCode, Pagination pagination)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.
				getFragmentCollectionByExternalReferenceCode(
					fragmentSetExternalReferenceCode,
					GroupUtil.getGroupId(
						true, true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		long resourcesFolderId = fragmentCollection.getResourcesFolderId(false);

		List<ResourceFolder> resourceFolders = new ArrayList<>();

		if (resourcesFolderId > 0) {
			Folder resourcesFolder = _dlAppLocalService.getFolder(
				resourcesFolderId);

			_collectResourceFolders(
				resourceFolders, resourcesFolder.getRepositoryId(),
				resourcesFolderId);
		}

		int totalCount = resourceFolders.size();

		if (pagination == null) {
			return Page.of(resourceFolders);
		}

		int startPosition = Math.min(pagination.getStartPosition(), totalCount);
		int endPosition = Math.min(pagination.getEndPosition(), totalCount);

		return Page.of(
			resourceFolders.subList(startPosition, endPosition), pagination,
			totalCount);
	}

	@Override
	public ResourceFolder getSiteResourceFolder(
			String siteExternalReferenceCode,
			String resourceFolderExternalReferenceCode)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		Folder folder = _dlAppLocalService.getFolderByExternalReferenceCode(
			resourceFolderExternalReferenceCode,
			GroupUtil.getGroupId(
				true, true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		return _toResourceFolder(folder);
	}

	@Override
	public ResourceFolder postSiteFragmentSetResourceFolder(
			String siteExternalReferenceCode,
			String fragmentSetExternalReferenceCode,
			ResourceFolder resourceFolder)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getStagingAwareGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		FragmentCollection fragmentCollection = _getOrCreateFragmentCollection(
			fragmentSetExternalReferenceCode, groupId);

		return _addResourceFolder(
			fragmentCollection, groupId,
			resourceFolder.getExternalReferenceCode(), resourceFolder);
	}

	@Override
	public ResourceFolder putSiteResourceFolder(
			String siteExternalReferenceCode,
			String resourceFolderExternalReferenceCode,
			ResourceFolder resourceFolder)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getStagingAwareGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		Folder existingFolder =
			_dlAppLocalService.fetchFolderByExternalReferenceCode(
				resourceFolderExternalReferenceCode, groupId);

		if (existingFolder == null) {
			if (Validator.isNull(
					resourceFolder.getFragmentSetExternalReferenceCode())) {

				throw new IllegalArgumentException(
					"A fragment set external reference code is required to " +
						"create a new resource folder");
			}

			FragmentCollection fragmentCollection =
				_getOrCreateFragmentCollection(
					resourceFolder.getFragmentSetExternalReferenceCode(),
					groupId);

			return _addResourceFolder(
				fragmentCollection, groupId,
				resourceFolderExternalReferenceCode, resourceFolder);
		}

		long parentFolderId = _resolveParentFolderId(
			existingFolder.getGroupId(), existingFolder, resourceFolder);

		Folder updatedFolder = existingFolder;

		if (parentFolderId != existingFolder.getParentFolderId()) {
			updatedFolder = _dlAppService.moveFolder(
				existingFolder.getFolderId(), parentFolderId,
				_getServiceContext(groupId));
		}

		updatedFolder = _dlAppService.updateFolder(
			updatedFolder.getFolderId(), resourceFolder.getName(),
			updatedFolder.getDescription(), _getServiceContext(groupId));

		return _toResourceFolder(updatedFolder);
	}

	private ResourceFolder _addResourceFolder(
			FragmentCollection fragmentCollection, long groupId,
			String externalReferenceCode, ResourceFolder resourceFolder)
		throws Exception {

		long resourcesFolderId = fragmentCollection.getResourcesFolderId(true);

		Folder resourcesFolder = _dlAppLocalService.getFolder(
			resourcesFolderId);

		long parentFolderId = resourcesFolderId;

		String parentResourceFolderExternalReferenceCode =
			resourceFolder.getParentResourceFolderExternalReferenceCode();

		if (Validator.isNotNull(parentResourceFolderExternalReferenceCode)) {
			Folder parentFolder =
				_dlAppLocalService.getFolderByExternalReferenceCode(
					parentResourceFolderExternalReferenceCode, groupId);

			parentFolderId = parentFolder.getFolderId();
		}

		Folder folder = _dlAppService.addFolder(
			externalReferenceCode, resourcesFolder.getRepositoryId(),
			parentFolderId, resourceFolder.getName(), StringPool.BLANK,
			_getServiceContext(groupId));

		return _toResourceFolder(folder);
	}

	private void _collectResourceFolders(
			List<ResourceFolder> resourceFolders, long repositoryId,
			long parentFolderId)
		throws Exception {

		List<Folder> childFolders = _dlAppService.getFolders(
			repositoryId, parentFolderId, false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (Folder childFolder : childFolders) {
			resourceFolders.add(_toResourceFolder(childFolder));

			_collectResourceFolders(
				resourceFolders, repositoryId, childFolder.getFolderId());
		}
	}

	private FragmentCollection _getOrCreateFragmentCollection(
			String fragmentSetExternalReferenceCode, long groupId)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					fragmentSetExternalReferenceCode, groupId);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		ServiceContext serviceContext = _getServiceContext(groupId);

		return _fragmentCollectionService.addFragmentCollection(
			fragmentSetExternalReferenceCode, groupId, null,
			fragmentSetExternalReferenceCode, StringPool.BLANK, false,
			serviceContext);
	}

	private ServiceContext _getServiceContext(long groupId) {
		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, null
		).build();

		serviceContext.setCompanyId(contextCompany.getCompanyId());

		return serviceContext;
	}

	private long _resolveParentFolderId(
			long groupId, Folder existingFolder, ResourceFolder resourceFolder)
		throws Exception {

		String parentResourceFolderExternalReferenceCode =
			resourceFolder.getParentResourceFolderExternalReferenceCode();

		if (Validator.isNull(parentResourceFolderExternalReferenceCode)) {
			return existingFolder.getParentFolderId();
		}

		Folder parentFolder =
			_dlAppLocalService.getFolderByExternalReferenceCode(
				parentResourceFolderExternalReferenceCode, groupId);

		return parentFolder.getFolderId();
	}

	private ResourceFolder _toResourceFolder(Folder folder) throws Exception {
		return _resourceFolderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false, null, _dtoConverterRegistry, contextHttpServletRequest,
				folder.getFolderId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			folder);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.fragment.internal.dto.v1_0.converter.ResourceFolderDTOConverter)"
	)
	private DTOConverter<Folder, ResourceFolder> _resourceFolderDTOConverter;

}