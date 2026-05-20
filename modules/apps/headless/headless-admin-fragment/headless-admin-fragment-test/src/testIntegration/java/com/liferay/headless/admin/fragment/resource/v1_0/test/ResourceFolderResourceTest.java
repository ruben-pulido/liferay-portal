/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.headless.admin.fragment.client.dto.v1_0.ResourceFolder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-39244")
@RunWith(Arquillian.class)
public class ResourceFolderResourceTest
	extends BaseResourceFolderResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fragmentCollection = _addFragmentCollection();
	}

	@Override
	@Test
	public void testDeleteSiteResourceFolder() throws Exception {
		super.testDeleteSiteResourceFolder();

		_assertDeleteSiteResourceFolderCascadesNestedFolders();
	}

	@Override
	@Test
	public void testPostSiteFragmentSetResourceFolder() throws Exception {
		super.testPostSiteFragmentSetResourceFolder();

		_assertPostSiteFragmentSetResourceFolderHierarchicalNesting();
		_assertPostSiteFragmentSetResourceFolderAutoCreatesFragmentSet();
	}

	@Override
	@Test
	public void testPutSiteResourceFolder() throws Exception {
		super.testPutSiteResourceFolder();

		_assertPutSiteResourceFolderRelocates();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "fragmentSetExternalReferenceCode", "name",
			"parentResourceFolderExternalReferenceCode"
		};
	}

	@Override
	protected ResourceFolder randomResourceFolder() throws Exception {
		ResourceFolder resourceFolder = super.randomResourceFolder();

		resourceFolder.setFragmentSetExternalReferenceCode(
			_fragmentCollection.getExternalReferenceCode());
		resourceFolder.setParentResourceFolderExternalReferenceCode(
			(String)null);

		return resourceFolder;
	}

	@Override
	protected ResourceFolder testDeleteSiteResourceFolder_addResourceFolder()
		throws Exception {

		return _postResourceFolder(randomResourceFolder());
	}

	@Override
	protected ResourceFolder
			testGetSiteFragmentSetResourceFoldersPage_addResourceFolder(
				String siteExternalReferenceCode,
				String fragmentSetExternalReferenceCode,
				ResourceFolder resourceFolder)
		throws Exception {

		return resourceFolderResource.postSiteFragmentSetResourceFolder(
			siteExternalReferenceCode, fragmentSetExternalReferenceCode,
			resourceFolder);
	}

	@Override
	protected String
			testGetSiteFragmentSetResourceFoldersPage_getFragmentSetExternalReferenceCode()
		throws Exception {

		return _fragmentCollection.getExternalReferenceCode();
	}

	@Override
	protected ResourceFolder testGetSiteResourceFolder_addResourceFolder()
		throws Exception {

		return _postResourceFolder(randomResourceFolder());
	}

	@Override
	protected ResourceFolder
			testPostSiteFragmentSetResourceFolder_addResourceFolder(
				ResourceFolder resourceFolder)
		throws Exception {

		return _postResourceFolder(resourceFolder);
	}

	@Override
	protected ResourceFolder testPutSiteResourceFolder_addResourceFolder()
		throws Exception {

		return _postResourceFolder(randomResourceFolder());
	}

	private FragmentCollection _addFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId());

		return _fragmentCollectionLocalService.addFragmentCollection(
			null, serviceContext.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private void _assertDeleteSiteResourceFolderCascadesNestedFolders()
		throws Exception {

		ResourceFolder parentResourceFolder = _postResourceFolder(
			randomResourceFolder());

		ResourceFolder childResourceFolder = randomResourceFolder();

		childResourceFolder.setParentResourceFolderExternalReferenceCode(
			parentResourceFolder.getExternalReferenceCode());

		childResourceFolder = _postResourceFolder(childResourceFolder);

		resourceFolderResource.deleteSiteResourceFolder(
			testGroup.getExternalReferenceCode(),
			parentResourceFolder.getExternalReferenceCode());

		Assert.assertNull(
			_dlAppLocalService.fetchFolderByExternalReferenceCode(
				parentResourceFolder.getExternalReferenceCode(),
				testGroup.getGroupId()));
		Assert.assertNull(
			_dlAppLocalService.fetchFolderByExternalReferenceCode(
				childResourceFolder.getExternalReferenceCode(),
				testGroup.getGroupId()));
	}

	private void _assertPostSiteFragmentSetResourceFolderAutoCreatesFragmentSet()
		throws Exception {

		String fragmentSetExternalReferenceCode = RandomTestUtil.randomString();

		Assert.assertNull(
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					fragmentSetExternalReferenceCode, testGroup.getGroupId()));

		ResourceFolder resourceFolder = randomResourceFolder();

		resourceFolder.setFragmentSetExternalReferenceCode(
			fragmentSetExternalReferenceCode);

		ResourceFolder postResourceFolder =
			resourceFolderResource.postSiteFragmentSetResourceFolder(
				testGroup.getExternalReferenceCode(),
				fragmentSetExternalReferenceCode, resourceFolder);

		Assert.assertEquals(
			fragmentSetExternalReferenceCode,
			postResourceFolder.getFragmentSetExternalReferenceCode());

		Assert.assertNotNull(
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					fragmentSetExternalReferenceCode, testGroup.getGroupId()));
	}

	private void _assertPostSiteFragmentSetResourceFolderHierarchicalNesting()
		throws Exception {

		ResourceFolder parentResourceFolder = _postResourceFolder(
			randomResourceFolder());

		ResourceFolder childResourceFolder = randomResourceFolder();

		childResourceFolder.setParentResourceFolderExternalReferenceCode(
			parentResourceFolder.getExternalReferenceCode());

		ResourceFolder postChildResourceFolder = _postResourceFolder(
			childResourceFolder);

		Assert.assertEquals(
			parentResourceFolder.getExternalReferenceCode(),
			postChildResourceFolder.
				getParentResourceFolderExternalReferenceCode());
	}

	private void _assertPutSiteResourceFolderRelocates() throws Exception {
		ResourceFolder originalParent = _postResourceFolder(
			randomResourceFolder());
		ResourceFolder newParent = _postResourceFolder(randomResourceFolder());

		ResourceFolder child = randomResourceFolder();

		child.setParentResourceFolderExternalReferenceCode(
			originalParent.getExternalReferenceCode());

		child = _postResourceFolder(child);

		ResourceFolder relocated = randomResourceFolder();

		relocated.setExternalReferenceCode(child.getExternalReferenceCode());
		relocated.setParentResourceFolderExternalReferenceCode(
			newParent.getExternalReferenceCode());

		ResourceFolder putResourceFolder =
			resourceFolderResource.putSiteResourceFolder(
				testGroup.getExternalReferenceCode(),
				child.getExternalReferenceCode(), relocated);

		Assert.assertEquals(
			newParent.getExternalReferenceCode(),
			putResourceFolder.getParentResourceFolderExternalReferenceCode());
		Assert.assertEquals(relocated.getName(), putResourceFolder.getName());
	}

	private ResourceFolder _postResourceFolder(ResourceFolder resourceFolder)
		throws Exception {

		return resourceFolderResource.postSiteFragmentSetResourceFolder(
			testGroup.getExternalReferenceCode(),
			_fragmentCollection.getExternalReferenceCode(), resourceFolder);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

}