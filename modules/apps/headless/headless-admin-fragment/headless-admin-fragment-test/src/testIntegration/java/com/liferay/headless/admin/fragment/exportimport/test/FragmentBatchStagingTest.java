/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.fragment.dto.v1_0.ResourceFolder;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentBatchStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo({"LPD-A", "LPD-B", "LPD-D"})
	public void testPublish() throws Exception {
		FragmentCollection liveFragmentCollection = _addFragmentCollection(
			_liveGroup);

		Folder liveFolder = _addFolder(
			_liveGroup, "Folder1",
			liveFragmentCollection.getResourcesFolderId());

		FileEntry liveFileEntry = _addFileEntry(
			liveFolder.getFolderId(), liveFragmentCollection, "Image1");

		FragmentEntry liveFragmentEntry = _addFragmentEntry(
			liveFragmentCollection, "<div>Live</div>");

		Group stagingGroup = _enableLocalStaging();

		FragmentCollection stagingFragmentCollection =
			_fragmentCollectionLocalService.
				getFragmentCollectionByExternalReferenceCode(
					liveFragmentCollection.getExternalReferenceCode(),
					stagingGroup.getGroupId());

		Assert.assertEquals(
			liveFragmentCollection.getUuid(),
			stagingFragmentCollection.getUuid());

		Map<String, FileEntry> resourcesMap =
			stagingFragmentCollection.getResourcesMap();

		_assertFileEntry(liveFileEntry, resourcesMap.get("Folder1/Image1"));

		FragmentEntry stagingFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				liveFragmentEntry.getExternalReferenceCode(),
				stagingGroup.getGroupId());

		Assert.assertEquals(
			liveFragmentEntry.getUuid(), stagingFragmentEntry.getUuid());

		FragmentEntry draftFragmentEntry = _fragmentEntryLocalService.getDraft(
			stagingFragmentEntry.getFragmentEntryId());

		draftFragmentEntry.setHtml("<div>Staging</div>");

		_fragmentEntryLocalService.publishDraft(
			_fragmentEntryLocalService.updateDraft(draftFragmentEntry));

		Folder stagingFolder =
			_dlAppLocalService.getFolderByExternalReferenceCode(
				liveFolder.getExternalReferenceCode(),
				stagingGroup.getGroupId());

		FileEntry stagingFileEntry = _addFileEntry(
			_addFolder(
				stagingGroup, "Folder2", stagingFolder.getFolderId()
			).getFolderId(),
			stagingFragmentCollection, "Image2");

		FragmentEntry newStagingFragmentEntry = _addFragmentEntry(
			stagingFragmentCollection, "<div>New</div>");

		ExportImportTestUtil.publishLayoutsRangeFromLastPublishedDate(
			stagingGroup, _liveGroup);

		liveFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				liveFragmentEntry.getExternalReferenceCode(),
				_liveGroup.getGroupId());

		Assert.assertEquals("<div>Staging</div>", liveFragmentEntry.getHtml());

		FragmentEntry newLiveFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				newStagingFragmentEntry.getExternalReferenceCode(),
				_liveGroup.getGroupId());

		Assert.assertEquals(
			newStagingFragmentEntry.getUuid(), newLiveFragmentEntry.getUuid());

		resourcesMap = liveFragmentCollection.getResourcesMap();

		_assertFileEntry(
			stagingFileEntry, resourcesMap.get("Folder1/Folder2/Image2"));

		_fragmentEntryLocalService.deleteFragmentEntry(newStagingFragmentEntry);

		ExportImportTestUtil.publishLayoutsRangeFromLastPublishedDate(
			stagingGroup, _liveGroup);

		Assert.assertNull(
			_fragmentEntryLocalService.
				fetchFragmentEntryByExternalReferenceCode(
					newStagingFragmentEntry.getExternalReferenceCode(),
					_liveGroup.getGroupId()));
	}

	@Test
	@TestInfo({"LPD-C", "LPD-D"})
	public void testPublishResourceDeletions() throws Exception {
		FragmentCollection liveFragmentCollection = _addFragmentCollection(
			_liveGroup);

		FileEntry liveFileEntry1 = _addFileEntry(
			liveFragmentCollection.getResourcesFolderId(),
			liveFragmentCollection, "Image1");

		Folder liveFolder1 = _addFolder(
			_liveGroup, "Folder1",
			liveFragmentCollection.getResourcesFolderId());

		Folder liveFolder2 = _addFolder(
			_liveGroup, "Folder2", liveFolder1.getFolderId());

		FileEntry liveFileEntry2 = _addFileEntry(
			liveFolder2.getFolderId(), liveFragmentCollection, "Image2");

		Group stagingGroup = _enableLocalStaging();

		FileEntry stagingFileEntry1 =
			_dlAppLocalService.getFileEntryByExternalReferenceCode(
				liveFileEntry1.getExternalReferenceCode(),
				stagingGroup.getGroupId());

		_dlAppLocalService.deleteFileEntry(stagingFileEntry1.getFileEntryId());

		_assertSystemEventReferrerClassName(
			FragmentCollection.class.getName(), DLFileEntry.class.getName(),
			stagingFileEntry1.getFileEntryId(), stagingGroup.getGroupId());

		Folder stagingFolder1 =
			_dlAppLocalService.getFolderByExternalReferenceCode(
				liveFolder1.getExternalReferenceCode(),
				stagingGroup.getGroupId());

		_dlAppLocalService.deleteFolder(stagingFolder1.getFolderId());

		_assertSystemEventType(
			ResourceFolder.class.getName(), DLFolder.class.getName(),
			stagingFolder1.getFolderId(), stagingGroup.getGroupId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomBytes(), null, null, null,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());

		_assertSystemEventReferrerClassName(
			StringPool.BLANK, DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), stagingGroup.getGroupId());

		ExportImportTestUtil.publishLayoutsRangeFromLastPublishedDate(
			stagingGroup, _liveGroup);

		Map<String, FileEntry> resourcesMap =
			liveFragmentCollection.getResourcesMap();

		Assert.assertTrue(resourcesMap.toString(), resourcesMap.isEmpty());

		Assert.assertNull(
			_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
				_liveGroup.getGroupId(),
				liveFileEntry2.getExternalReferenceCode()));
		Assert.assertNull(
			_dlFolderLocalService.fetchDLFolderByExternalReferenceCode(
				liveFolder1.getExternalReferenceCode(),
				_liveGroup.getGroupId()));
		Assert.assertNull(
			_dlFolderLocalService.fetchDLFolderByExternalReferenceCode(
				liveFolder2.getExternalReferenceCode(),
				_liveGroup.getGroupId()));
	}

	private FileEntry _addFileEntry(
			long folderId, FragmentCollection fragmentCollection, String name)
		throws Exception {

		return PortletFileRepositoryUtil.addPortletFileEntry(
			null, fragmentCollection.getGroupId(), TestPropsValues.getUserId(),
			FragmentCollection.class.getName(),
			fragmentCollection.getFragmentCollectionId(),
			FragmentPortletKeys.FRAGMENT, folderId,
			new UnsyncByteArrayInputStream(RandomTestUtil.randomBytes()), name,
			ContentTypes.IMAGE_PNG, false);
	}

	private Folder _addFolder(Group group, String name, long parentFolderId)
		throws Exception {

		return PortletFileRepositoryUtil.addPortletFolder(
			group.getGroupId(), TestPropsValues.getUserId(),
			FragmentPortletKeys.FRAGMENT, parentFolderId, name,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private FragmentCollection _addFragmentCollection(Group group)
		throws Exception {

		return _fragmentCollectionLocalService.addFragmentCollection(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private FragmentEntry _addFragmentEntry(
			FragmentCollection fragmentCollection, String html)
		throws Exception {

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), fragmentCollection.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, html, StringPool.BLANK, false, StringPool.BLANK,
			null, 0, false, false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertFileEntry(
			FileEntry expectedFileEntry, FileEntry actualFileEntry)
		throws Exception {

		Assert.assertNotNull(actualFileEntry);

		Assert.assertArrayEquals(
			FileUtil.getBytes(expectedFileEntry.getContentStream()),
			FileUtil.getBytes(actualFileEntry.getContentStream()));
		Assert.assertEquals(
			expectedFileEntry.getExternalReferenceCode(),
			actualFileEntry.getExternalReferenceCode());
		Assert.assertEquals(
			expectedFileEntry.getUuid(), actualFileEntry.getUuid());
	}

	private void _assertSystemEventReferrerClassName(
			String expectedReferrerClassName, String className, long classPK,
			long groupId)
		throws Exception {

		List<SystemEvent> systemEvents =
			_systemEventLocalService.getSystemEvents(
				groupId, PortalUtil.getClassNameId(className), classPK,
				SystemEventConstants.TYPE_DELETE);

		Assert.assertEquals(systemEvents.toString(), 1, systemEvents.size());

		SystemEvent systemEvent = systemEvents.get(0);

		Assert.assertEquals(
			expectedReferrerClassName, systemEvent.getReferrerClassName());
	}

	private void _assertSystemEventType(
			String expectedType, String className, long classPK, long groupId)
		throws Exception {

		List<SystemEvent> systemEvents =
			_systemEventLocalService.getSystemEvents(
				groupId, PortalUtil.getClassNameId(className), classPK,
				SystemEventConstants.TYPE_DELETE);

		Assert.assertEquals(systemEvents.toString(), 1, systemEvents.size());

		SystemEvent systemEvent = systemEvents.get(0);

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			systemEvent.getExtraData());

		Assert.assertEquals(
			expectedType, extraDataJSONObject.getString("type", null));
	}

	private Group _enableLocalStaging() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_liveGroup.getGroupId());

		for (String key :
				new String[] {
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
					PortletDataHandlerKeys.PORTLET_DATA_ALL,
					PortletDataHandlerKeys.PORTLET_SETUP_ALL
				}) {

			serviceContext.setAttribute(
				StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
				Boolean.TRUE.toString());
		}

		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _liveGroup, false, false,
			serviceContext);

		_liveGroup = _groupLocalService.getGroup(_liveGroup.getGroupId());

		return _liveGroup.getStagingGroup();
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@Inject
	private StagingLocalService _stagingLocalService;

	@Inject
	private SystemEventLocalService _systemEventLocalService;

}