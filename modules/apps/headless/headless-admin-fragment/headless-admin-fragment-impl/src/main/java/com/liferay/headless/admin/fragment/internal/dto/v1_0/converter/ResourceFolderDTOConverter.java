/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.dto.v1_0.converter;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.headless.admin.fragment.dto.v1_0.ResourceFolder;
import com.liferay.headless.admin.user.dto.v1_0.Creator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = DTOConverter.class
)
public class ResourceFolderDTOConverter
	implements DTOConverter<Folder, ResourceFolder> {

	@Override
	public String getContentType() {
		return ResourceFolder.class.getSimpleName();
	}

	@Override
	public ResourceFolder toDTO(
			DTOConverterContext dtoConverterContext, Folder folder)
		throws Exception {

		FragmentCollection fragmentCollection = _findOwningFragmentCollection(
			folder);

		long resourcesFolderId = 0;

		if (fragmentCollection != null) {
			resourcesFolderId = fragmentCollection.getResourcesFolderId(false);
		}

		long parentFolderId = folder.getParentFolderId();

		String parentResourceFolderExternalReferenceCode = null;

		if ((parentFolderId != 0) && (parentFolderId != resourcesFolderId)) {
			Folder parentFolder = _dlAppLocalService.getFolder(parentFolderId);

			parentResourceFolderExternalReferenceCode =
				parentFolder.getExternalReferenceCode();
		}

		String fragmentSetExternalReferenceCode = null;

		if (fragmentCollection != null) {
			fragmentSetExternalReferenceCode =
				fragmentCollection.getExternalReferenceCode();
		}

		String finalFragmentSetExternalReferenceCode =
			fragmentSetExternalReferenceCode;
		String finalParentResourceFolderExternalReferenceCode =
			parentResourceFolderExternalReferenceCode;

		return new ResourceFolder() {
			{
				setCreator(
					() -> {
						User user = _userLocalService.fetchUser(
							folder.getUserId());

						if (user == null) {
							return null;
						}

						return new Creator() {
							{
								setExternalReferenceCode(
									user::getExternalReferenceCode);
							}
						};
					});
				setDateCreated(folder::getCreateDate);
				setDateModified(folder::getModifiedDate);
				setExternalReferenceCode(folder::getExternalReferenceCode);
				setFragmentSetExternalReferenceCode(
					() -> finalFragmentSetExternalReferenceCode);
				setName(folder::getName);
				setParentResourceFolderExternalReferenceCode(
					() -> finalParentResourceFolderExternalReferenceCode);
			}
		};
	}

	private FragmentCollection _findOwningFragmentCollection(Folder folder)
		throws Exception {

		Set<Long> ancestorFolderIds = new HashSet<>();

		ancestorFolderIds.add(folder.getFolderId());

		long parentFolderId = folder.getParentFolderId();

		while (parentFolderId != 0) {
			ancestorFolderIds.add(parentFolderId);

			try {
				Folder parentFolder = _dlAppLocalService.getFolder(
					parentFolderId);

				parentFolderId = parentFolder.getParentFolderId();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				break;
			}
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				folder.getGroupId());

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			long resourcesFolderId = fragmentCollection.getResourcesFolderId(
				false);

			if ((resourcesFolderId > 0) &&
				ancestorFolderIds.contains(resourcesFolderId)) {

				return fragmentCollection;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceFolderDTOConverter.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}