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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.headless.delivery.dto.v1_0.FragmentCollection;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.FragmentCollectionResource;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/fragment-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = FragmentCollectionResource.class
)
public class FragmentCollectionResourceImpl
	extends BaseFragmentCollectionResourceImpl {

	@Override
	public FragmentCollection getFragmentCollection(Long fragmentCollectionId)
		throws Exception {

		return _toFragmentCollection(
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollectionId));
	}

	@Override
	public Page<FragmentCollection> getSiteFragmentCollectionsPage(
		Long siteId, Pagination pagination) {

		return Page.of(
			transform(
				_fragmentCollectionService.getFragmentCollections(
					siteId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toFragmentCollection),
			pagination,
			_fragmentCollectionService.getFragmentCollectionsCount(siteId));
	}

	private FragmentCollection _toFragmentCollection(
			com.liferay.fragment.model.FragmentCollection fragmentCollection)
		throws Exception {

		return new FragmentCollection() {
			{
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(fragmentCollection.getUserId()));
				dateCreated = fragmentCollection.getCreateDate();
				dateModified = fragmentCollection.getModifiedDate();
				description = fragmentCollection.getDescription();
				id = fragmentCollection.getFragmentCollectionId();
				name = fragmentCollection.getName();
				siteId = fragmentCollection.getGroupId();
			}
		};
	}

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}