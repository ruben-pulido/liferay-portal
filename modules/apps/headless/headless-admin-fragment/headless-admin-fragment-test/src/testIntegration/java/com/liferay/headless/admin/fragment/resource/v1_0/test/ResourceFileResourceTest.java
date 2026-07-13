/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.LazyReferencingTestUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FileURLReference;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FragmentSet;
import com.liferay.headless.admin.fragment.client.dto.v1_0.ResourceFile;
import com.liferay.headless.admin.fragment.client.dto.v1_0.ResourceFolder;
import com.liferay.headless.admin.fragment.client.pagination.Page;
import com.liferay.headless.admin.fragment.client.pagination.Pagination;
import com.liferay.headless.admin.fragment.client.problem.Problem;
import com.liferay.headless.admin.fragment.client.resource.v1_0.ResourceFileResource;
import com.liferay.headless.admin.fragment.client.resource.v1_0.ResourceFolderResource;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-39244")
@RunWith(Arquillian.class)
public class ResourceFileResourceTest extends BaseResourceFileResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseResourceFileResourceTestCase.setUpClass();

		_httpServer = HttpServer.create(
			new InetSocketAddress("127.0.0.1", 0), 0);

		_content1Bytes = RandomTestUtil.randomBytes();
		_content2Bytes = RandomTestUtil.randomBytes();

		_httpServer.createContext(
			"/content_1.txt",
			httpExchange -> _writeBytes(httpExchange, _content1Bytes));
		_httpServer.createContext(
			"/content_2.txt",
			httpExchange -> _writeBytes(httpExchange, _content2Bytes));

		_httpServer.start();

		_content1Base64 = Base64.encode(_content1Bytes);

		InetSocketAddress inetSocketAddress = _httpServer.getAddress();

		String baseURL = "http://127.0.0.1:" + inetSocketAddress.getPort();

		_content1URL = baseURL + "/content_1.txt";
		_content2URL = baseURL + "/content_2.txt";
	}

	@AfterClass
	public static void tearDownClass() {
		if (_httpServer != null) {
			_httpServer.stop(0);
		}
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(testCompany, password);

		_resourceFileResource = ResourceFileResource.builder(
		).authentication(
			user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testDeleteSiteResourceFile() throws Exception {
		super.testDeleteSiteResourceFile();

		_testDeleteSiteResourceFilePortletFileProblemException();
		_testDeleteSiteResourceFileWithoutPermissionsProblemException();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testGetSiteFragmentSetResourceFilesPage() throws Exception {
		super.testGetSiteFragmentSetResourceFilesPage();

		_testGetSiteFragmentSetResourceFilesPage();
		_testGetSiteFragmentSetResourceFilesPageEmpty();
		_testGetSiteFragmentSetResourceFilesPageFragmentSetNonexistentProblemException();
		_testGetSiteFragmentSetResourceFilesPageWithoutPermissions();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testGetSiteResourceFile() throws Exception {
		super.testGetSiteResourceFile();

		_testGetSiteResourceFileFileBase64();
		_testGetSiteResourceFileFileURLReferenceURL();
		_testGetSiteResourceFileFragmentSet();
		_testGetSiteResourceFileParentResourceFolder();
		_testGetSiteResourceFilePortletFileProblemException();
		_testGetSiteResourceFileResourceFileNonexistentProblemException();
		_testGetSiteResourceFileWithoutPermissionsProblemException();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testGetSiteResourceFilesPage() throws Exception {
		super.testGetSiteResourceFilesPage();

		_testGetSiteResourceFilesPage();
		_testGetSiteResourceFilesPagePortletFile();
		_testGetSiteResourceFilesPageWithoutPermissions();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testGetSiteResourceFolderResourceFilesPage() throws Exception {
		super.testGetSiteResourceFolderResourceFilesPage();

		_testGetSiteResourceFolderResourceFilesPage();
		_testGetSiteResourceFolderResourceFilesPageEmpty();
		_testGetSiteResourceFolderResourceFilesPagePortletFolderProblemException();
		_testGetSiteResourceFolderResourceFilesPageResourceFolderNonexistentProblemException();
		_testGetSiteResourceFolderResourceFilesPageWithoutPermissionsProblemException();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testPostSiteFragmentSetResourceFile() throws Exception {
		super.testPostSiteFragmentSetResourceFile();

		_testPostSiteFragmentSetResourceFileWithoutPermissionsProblemException();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testPostSiteResourceFile() throws Exception {
		super.testPostSiteResourceFile();

		_testPostSiteResourceFile();
		_testPostSiteResourceFileBatch();
		_testPostSiteResourceFileDuplicateExternalReferenceCodeProblemException();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCode();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCodeAndFileBase64();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCodeEmptyAndFileBase64();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentAndFileBase64();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentAndURL();
		_testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentProblemException();
		_testPostSiteResourceFileFileURLReferenceFileBase64AndURL();
		_testPostSiteResourceFileFileURLReferenceNullProblemException();
		_testPostSiteResourceFileFileURLReferenceURL();
		_testPostSiteResourceFileFileURLReferenceURLUnreachableProblemException();
		_testPostSiteResourceFileFileURLReferenceURLUnsupportedProtocolProblemException();
		_testPostSiteResourceFileFragmentSetAndFragmentSetExternalReferenceCode();
		_testPostSiteResourceFileFragmentSetAndFragmentSetExternalReferenceCodeProblemException();
		_testPostSiteResourceFileFragmentSetExternalReferenceCode();
		_testPostSiteResourceFileFragmentSetExternalReferenceCodeNullProblemException();
		_testPostSiteResourceFileFragmentSetNonexistentProblemException();
		_testPostSiteResourceFileParentResourceFolderAndParentResourceFolderExternalReferenceCode();
		_testPostSiteResourceFileParentResourceFolderExternalReferenceCode();
		_testPostSiteResourceFileParentResourceFolderNonexistentProblemException();
		_testPostSiteResourceFileParentResourceFolderPortletFolderProblemException();
		_testPostSiteResourceFileWithoutPermissionsProblemException();
	}

	@Override
	@Test
	@TestInfo("LPD-88395")
	public void testPutSiteResourceFile() throws Exception {
		_testPutSiteResourceFile();
		_testPutSiteResourceFileFileURLReferenceNull();
		_testPutSiteResourceFileNameNull();
		_testPutSiteResourceFileParentResourceFolderExternalReferenceCode();
		_testPutSiteResourceFilePortletFileProblemException();
		_testPutSiteResourceFileWithoutPermissionsProblemException();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "fragmentSetExternalReferenceCode", "name"
		};
	}

	@Override
	protected ResourceFile randomResourceFile() throws Exception {
		return _randomResourceFile(_getFragmentSetExternalReferenceCode());
	}

	@Override
	protected ResourceFile
			testGetSiteFragmentSetResourceFilesPage_addResourceFile(
				String siteExternalReferenceCode,
				String fragmentSetExternalReferenceCode,
				ResourceFile resourceFile)
		throws Exception {

		return resourceFileResource.postSiteFragmentSetResourceFile(
			siteExternalReferenceCode, fragmentSetExternalReferenceCode,
			resourceFile);
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetSiteFragmentSetResourceFilesPage_getExpectedActions(
				String siteExternalReferenceCode,
				String fragmentSetExternalReferenceCode)
		throws Exception {

		return new HashMap<>();
	}

	@Override
	protected String
			testGetSiteFragmentSetResourceFilesPage_getFragmentSetExternalReferenceCode()
		throws Exception {

		return _getFragmentSetExternalReferenceCode();
	}

	@Override
	protected ResourceFile testGetSiteResourceFilesPage_addResourceFile(
			String siteExternalReferenceCode, ResourceFile resourceFile)
		throws Exception {

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, testCompany.getCompanyId());

		FragmentCollection fragmentCollection = _addFragmentCollection(
			group.getGroupId());

		resourceFile.setFragmentSetExternalReferenceCode(
			fragmentCollection.getExternalReferenceCode());

		return resourceFileResource.postSiteResourceFile(
			siteExternalReferenceCode, resourceFile);
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetSiteResourceFilesPage_getExpectedActions(
				String siteExternalReferenceCode)
		throws Exception {

		return new HashMap<>();
	}

	@Override
	protected ResourceFile
			testGetSiteResourceFolderResourceFilesPage_addResourceFile(
				String siteExternalReferenceCode,
				String resourceFolderExternalReferenceCode,
				ResourceFile resourceFile)
		throws Exception {

		resourceFile.setParentResourceFolderExternalReferenceCode(
			resourceFolderExternalReferenceCode);

		return resourceFileResource.postSiteResourceFile(
			siteExternalReferenceCode, resourceFile);
	}

	@Override
	protected String
			testGetSiteResourceFolderResourceFilesPage_getResourceFolderExternalReferenceCode()
		throws Exception {

		ResourceFolder resourceFolder = _postSiteResourceFolder(
			_getFragmentSetExternalReferenceCode());

		return resourceFolder.getExternalReferenceCode();
	}

	@Override
	protected ResourceFile testPostSiteFragmentSetResourceFile_addResourceFile(
			ResourceFile resourceFile)
		throws Exception {

		return resourceFileResource.postSiteFragmentSetResourceFile(
			testGroup.getExternalReferenceCode(),
			resourceFile.getFragmentSetExternalReferenceCode(), resourceFile);
	}

	@Override
	protected ResourceFile testPostSiteResourceFile_addResourceFile(
			ResourceFile resourceFile)
		throws Exception {

		return resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(), resourceFile);
	}

	private static void _writeBytes(HttpExchange httpExchange, byte[] bytes)
		throws IOException {

		Headers responseHeaders = httpExchange.getResponseHeaders();

		responseHeaders.set("Content-Type", ContentTypes.TEXT_PLAIN);

		httpExchange.sendResponseHeaders(200, bytes.length);

		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(bytes);
		}
	}

	private FragmentCollection _addFragmentCollection(long groupId)
		throws Exception {

		return _fragmentCollectionLocalService.addFragmentCollection(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), groupId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			false, ServiceContextTestUtil.getServiceContext(groupId));
	}

	private FileEntry _addPortletFileEntry() throws Exception {
		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));

		return PortletFileRepositoryUtil.addPortletFileEntry(
			testGroup.getGroupId(), TestPropsValues.getUserId(), null, 0,
			repository.getPortletId(), repository.getDlFolderId(),
			RandomTestUtil.randomBytes(), RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, false);
	}

	private Folder _addPortletFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId());

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			serviceContext);

		return PortletFileRepositoryUtil.addPortletFolder(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			repository.getDlFolderId(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private void _assertContent(
			byte[] expectedBytes, String externalReferenceCode, long groupId)
		throws Exception {

		FileEntry fileEntry =
			PortletFileRepositoryUtil.
				fetchPortletFileEntryByExternalReferenceCode(
					externalReferenceCode, groupId);

		Assert.assertArrayEquals(
			expectedBytes, FileUtil.getBytes(fileEntry.getContentStream()));
	}

	private void _assertNotContains(
		FileEntry fileEntry, List<ResourceFile> resourceFiles) {

		String externalReferenceCode = fileEntry.getExternalReferenceCode();

		for (ResourceFile resourceFile : resourceFiles) {
			Assert.assertNotEquals(
				resourceFiles + " contains " + fileEntry, externalReferenceCode,
				resourceFile.getExternalReferenceCode());
		}
	}

	private void _assertProblemException(
			String status, String titleKey,
			UnsafeRunnable<Exception> unsafeRunnable, Object... titleArguments)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertEquals(
				_language.format(
					LocaleUtil.getDefault(), titleKey, titleArguments),
				problem.getTitle());
		}
	}

	private void _assertProblemException(
			String titleKey, UnsafeRunnable<Exception> unsafeRunnable,
			Object... titleArguments)
		throws Exception {

		_assertProblemException(
			"BAD_REQUEST", titleKey, unsafeRunnable, titleArguments);
	}

	private String _exportResourceFilesToJSON(String siteExternalReferenceCode)
		throws Exception {

		JSONObject exportTaskJSONObject = _waitForFinish(
			"COMPLETED", false,
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"headless-admin-fragment/v1.0/sites/",
					siteExternalReferenceCode,
					"/resource-files/export-batch?contentType=JSON",
					"&batchNestedFields=fileBase64,fragmentSet,",
					"parentResourceFolder"),
				Http.Method.POST));

		try (InputStream inputStream = HTTPTestUtil.invokeToInputStream(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/export-task",
					"/by-external-reference-code/",
					exportTaskJSONObject.getString("externalReferenceCode"),
					"/content"),
				HashMapBuilder.put(
					HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
				).build(),
				Http.Method.GET)) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			return StringUtil.read(zipInputStream);
		}
	}

	private String _getFragmentSetExternalReferenceCode() throws Exception {
		if (_fragmentSetExternalReferenceCode == null) {
			FragmentCollection fragmentCollection = _addFragmentCollection(
				testGroup.getGroupId());

			_fragmentSetExternalReferenceCode =
				fragmentCollection.getExternalReferenceCode();
		}

		return _fragmentSetExternalReferenceCode;
	}

	private ResourceFileResource _getResourceFileResource(String nestedFields)
		throws Exception {

		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		return ResourceFileResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", nestedFields
		).build();
	}

	private ResourceFolderResource _getResourceFolderResource()
		throws Exception {

		if (_resourceFolderResource != null) {
			return _resourceFolderResource;
		}

		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		_resourceFolderResource = ResourceFolderResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		return _resourceFolderResource;
	}

	private ResourceFile _getSiteResourceFile(String externalReferenceCode)
		throws Exception {

		return _getSiteResourceFile(
			externalReferenceCode, testGroup.getExternalReferenceCode());
	}

	private ResourceFile _getSiteResourceFile(
			String externalReferenceCode, String siteExternalReferenceCode)
		throws Exception {

		ResourceFileResource resourceFileResource = _getResourceFileResource(
			"fragmentSet,parentResourceFolder");

		return resourceFileResource.getSiteResourceFile(
			siteExternalReferenceCode, externalReferenceCode);
	}

	private ResourceFile _postSiteResourceFile(
			ResourceFolder parentResourceFolder)
		throws Exception {

		return resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			_randomResourceFile(parentResourceFolder));
	}

	private void _postSiteResourceFileAndAssertContent(
			byte[] expectedBytes, FileURLReference fileURLReference)
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		resourceFile.setFileURLReference(fileURLReference);

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		_assertContent(
			expectedBytes, postResourceFile.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private ResourceFolder _postSiteResourceFolder(
			ResourceFolder parentResourceFolder)
		throws Exception {

		ResourceFolderResource resourceFolderResource =
			_getResourceFolderResource();

		return resourceFolderResource.postSiteResourceFolder(
			testGroup.getExternalReferenceCode(),
			_randomResourceFolder(parentResourceFolder));
	}

	private ResourceFolder _postSiteResourceFolder(
			String fragmentSetExternalReferenceCode)
		throws Exception {

		ResourceFolderResource resourceFolderResource =
			_getResourceFolderResource();

		return resourceFolderResource.postSiteResourceFolder(
			testGroup.getExternalReferenceCode(),
			_randomResourceFolder(fragmentSetExternalReferenceCode));
	}

	private ResourceFile _putSiteResourceFile(
			ResourceFile resourceFile, String resourceFileExternalReferenceCode)
		throws Exception {

		ResourceFileResource resourceFileResource = _getResourceFileResource(
			"fragmentSet,parentResourceFolder");

		return resourceFileResource.putSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			resourceFileExternalReferenceCode, resourceFile);
	}

	private ResourceFile _randomResourceFile(
			ResourceFolder parentResourceFolder)
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			parentResourceFolder.getFragmentSetExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			parentResourceFolder.getExternalReferenceCode());

		return resourceFile;
	}

	private ResourceFile _randomResourceFile(
			String fragmentSetExternalReferenceCode)
		throws Exception {

		ResourceFile resourceFile = super.randomResourceFile();

		resourceFile.setDateCreated(new Date());
		resourceFile.setDateModified(new Date());
		resourceFile.setFileURLReference(
			_toFileURLReference(RandomTestUtil.randomBytes()));
		resourceFile.setFragmentSetExternalReferenceCode(
			fragmentSetExternalReferenceCode);
		resourceFile.setParentResourceFolderExternalReferenceCode((String)null);

		return resourceFile;
	}

	private ResourceFolder _randomResourceFolder(
			ResourceFolder parentResourceFolder)
		throws Exception {

		ResourceFolder resourceFolder = _randomResourceFolder(
			parentResourceFolder.getFragmentSetExternalReferenceCode());

		resourceFolder.setParentResourceFolderExternalReferenceCode(
			parentResourceFolder.getExternalReferenceCode());

		return resourceFolder;
	}

	private ResourceFolder _randomResourceFolder(
			String fragmentSetExternalReferenceCode)
		throws Exception {

		ResourceFolder resourceFolder = new ResourceFolder();

		resourceFolder.setExternalReferenceCode(RandomTestUtil.randomString());
		resourceFolder.setFragmentSetExternalReferenceCode(
			fragmentSetExternalReferenceCode);
		resourceFolder.setName(RandomTestUtil.randomString());

		return resourceFolder;
	}

	private void _testDeleteSiteResourceFilePortletFileProblemException()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry();

		try {
			resourceFileResource.deleteSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				fileEntry.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testDeleteSiteResourceFileWithoutPermissionsProblemException()
		throws Exception {

		ResourceFile resourceFile = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(), randomResourceFile());

		try {
			_resourceFileResource.deleteSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				resourceFile.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("FORBIDDEN", problem.getStatus());
		}
	}

	private void _testGetSiteFragmentSetResourceFilesPage() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile =
			resourceFileResource.postSiteFragmentSetResourceFile(
				testGroup.getExternalReferenceCode(),
				fragmentCollection.getExternalReferenceCode(),
				_randomResourceFile(
					fragmentCollection.getExternalReferenceCode()));

		ResourceFolder resourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		_postSiteResourceFile(resourceFolder);

		Page<ResourceFile> page =
			resourceFileResource.getSiteFragmentSetResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				fragmentCollection.getExternalReferenceCode(),
				Pagination.of(1, 10));

		assertContains(resourceFile, (List<ResourceFile>)page.getItems());
		Assert.assertEquals(1, page.getTotalCount());
	}

	private void _testGetSiteFragmentSetResourceFilesPageEmpty()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		Page<ResourceFile> page =
			resourceFileResource.getSiteFragmentSetResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				fragmentCollection.getExternalReferenceCode(),
				Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());
	}

	private void _testGetSiteFragmentSetResourceFilesPageFragmentSetNonexistentProblemException()
		throws Exception {

		try {
			resourceFileResource.getSiteFragmentSetResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), Pagination.of(1, 10));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteFragmentSetResourceFilesPageWithoutPermissions()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		resourceFileResource.postSiteFragmentSetResourceFile(
			testGroup.getExternalReferenceCode(),
			fragmentCollection.getExternalReferenceCode(),
			_randomResourceFile(fragmentCollection.getExternalReferenceCode()));

		Page<ResourceFile> page =
			_resourceFileResource.getSiteFragmentSetResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				fragmentCollection.getExternalReferenceCode(),
				Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());
	}

	private void _testGetSiteResourceFileFileBase64() throws Exception {
		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		resourceFile.setFileURLReference(_toFileURLReference(bytes));

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = resourceFileResource.getSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode());

		FileURLReference fileURLReference =
			getResourceFile.getFileURLReference();

		Assert.assertNull(fileURLReference.getFileBase64());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-admin-fragment/v1.0/sites/",
				testGroup.getExternalReferenceCode(), "/resource-files/",
				postResourceFile.getExternalReferenceCode(),
				"?nestedFields=fileBase64"),
			Http.Method.GET);

		JSONObject fileURLReferenceJSONObject = jsonObject.getJSONObject(
			"fileURLReference");

		Assert.assertArrayEquals(
			bytes,
			Base64.decode(fileURLReferenceJSONObject.getString("fileBase64")));
	}

	private void _testGetSiteResourceFileFileURLReferenceURL()
		throws Exception {

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), randomResourceFile());

		ResourceFile getResourceFile = resourceFileResource.getSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode());

		FileURLReference fileURLReference =
			getResourceFile.getFileURLReference();

		String url = fileURLReference.getUrl();

		Assert.assertTrue(url, url.startsWith("http"));
	}

	private void _testGetSiteResourceFileFragmentSet() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				_randomResourceFile(
					fragmentCollection.getExternalReferenceCode()));

		ResourceFile getResourceFile = resourceFileResource.getSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode());

		Assert.assertNull(getResourceFile.getFragmentSet());

		getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		FragmentSet fragmentSet = getResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode());
	}

	private void _testGetSiteResourceFileParentResourceFolder()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFolder postParentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			postParentResourceFolder.getExternalReferenceCode());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = resourceFileResource.getSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode());

		Assert.assertNull(getResourceFile.getParentResourceFolder());

		getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		ResourceFolder getParentResourceFolder =
			getResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getParentResourceFolder.getExternalReferenceCode());
	}

	private void _testGetSiteResourceFilePortletFileProblemException()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry();

		try {
			resourceFileResource.getSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				fileEntry.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteResourceFileResourceFileNonexistentProblemException()
		throws Exception {

		try {
			resourceFileResource.getSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteResourceFilesPage() throws Exception {
		FragmentCollection fragmentCollection1 = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile1 = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			_randomResourceFile(
				fragmentCollection1.getExternalReferenceCode()));

		ResourceFolder resourceFolder1 = _postSiteResourceFolder(
			fragmentCollection1.getExternalReferenceCode());

		ResourceFile childResourceFile1 = _postSiteResourceFile(
			resourceFolder1);

		FragmentCollection fragmentCollection2 = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile2 = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			_randomResourceFile(
				fragmentCollection2.getExternalReferenceCode()));

		ResourceFolder resourceFolder2 = _postSiteResourceFolder(
			fragmentCollection2.getExternalReferenceCode());

		ResourceFile childResourceFile2 = _postSiteResourceFile(
			resourceFolder2);

		Page<ResourceFile> page = resourceFileResource.getSiteResourceFilesPage(
			testGroup.getExternalReferenceCode(), null, Pagination.of(1, 1));

		long totalCount = page.getTotalCount();

		page = resourceFileResource.getSiteResourceFilesPage(
			testGroup.getExternalReferenceCode(), null,
			Pagination.of(1, (int)totalCount));

		List<ResourceFile> resourceFiles = (List<ResourceFile>)page.getItems();

		assertContains(childResourceFile1, resourceFiles);
		assertContains(childResourceFile2, resourceFiles);
		assertContains(resourceFile1, resourceFiles);
		assertContains(resourceFile2, resourceFiles);
	}

	private void _testGetSiteResourceFilesPagePortletFile() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			_randomResourceFile(fragmentCollection.getExternalReferenceCode()));

		Page<ResourceFile> page = resourceFileResource.getSiteResourceFilesPage(
			testGroup.getExternalReferenceCode(), null, Pagination.of(1, 1));

		long totalCount = page.getTotalCount();

		FileEntry fileEntry = _addPortletFileEntry();

		page = resourceFileResource.getSiteResourceFilesPage(
			testGroup.getExternalReferenceCode(), null,
			Pagination.of(1, (int)totalCount + 1));

		Assert.assertEquals(totalCount, page.getTotalCount());

		List<ResourceFile> resourceFiles = (List<ResourceFile>)page.getItems();

		assertContains(resourceFile, resourceFiles);
		_assertNotContains(fileEntry, resourceFiles);
	}

	private void _testGetSiteResourceFilesPageWithoutPermissions()
		throws Exception {

		resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(), randomResourceFile());

		Page<ResourceFile> page =
			_resourceFileResource.getSiteResourceFilesPage(
				testGroup.getExternalReferenceCode(), null,
				Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());
	}

	private void _testGetSiteResourceFileWithoutPermissionsProblemException()
		throws Exception {

		ResourceFile resourceFile = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(), randomResourceFile());

		try {
			_resourceFileResource.getSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				resourceFile.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteResourceFolderResourceFilesPage()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFolder parentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		ResourceFile resourceFile = _postSiteResourceFile(parentResourceFolder);

		ResourceFolder childResourceFolder = _postSiteResourceFolder(
			parentResourceFolder);

		_postSiteResourceFile(childResourceFolder);

		Page<ResourceFile> page =
			resourceFileResource.getSiteResourceFolderResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				parentResourceFolder.getExternalReferenceCode(),
				Pagination.of(1, 10));

		assertContains(resourceFile, (List<ResourceFile>)page.getItems());
		Assert.assertEquals(1, page.getTotalCount());
	}

	private void _testGetSiteResourceFolderResourceFilesPageEmpty()
		throws Exception {

		ResourceFolder resourceFolder = _postSiteResourceFolder(
			_getFragmentSetExternalReferenceCode());

		Page<ResourceFile> page =
			resourceFileResource.getSiteResourceFolderResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				resourceFolder.getExternalReferenceCode(),
				Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());
	}

	private void _testGetSiteResourceFolderResourceFilesPagePortletFolderProblemException()
		throws Exception {

		Folder folder = _addPortletFolder();

		try {
			resourceFileResource.getSiteResourceFolderResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				folder.getExternalReferenceCode(), Pagination.of(1, 10));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteResourceFolderResourceFilesPageResourceFolderNonexistentProblemException()
		throws Exception {

		try {
			resourceFileResource.getSiteResourceFolderResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), Pagination.of(1, 10));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testGetSiteResourceFolderResourceFilesPageWithoutPermissionsProblemException()
		throws Exception {

		ResourceFolder resourceFolder = _postSiteResourceFolder(
			_getFragmentSetExternalReferenceCode());

		try {
			_resourceFileResource.getSiteResourceFolderResourceFilesPage(
				testGroup.getExternalReferenceCode(),
				resourceFolder.getExternalReferenceCode(),
				Pagination.of(1, 10));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testPostSiteFragmentSetResourceFileWithoutPermissionsProblemException()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		try {
			_resourceFileResource.postSiteFragmentSetResourceFile(
				testGroup.getExternalReferenceCode(),
				fragmentCollection.getExternalReferenceCode(),
				_randomResourceFile(
					fragmentCollection.getExternalReferenceCode()));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("FORBIDDEN", problem.getStatus());
		}
	}

	private void _testPostSiteResourceFile() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFolder postParentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				_randomResourceFile(postParentResourceFolder));

		ResourceFile getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		FragmentSet getFragmentSet = getResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			getFragmentSet.getExternalReferenceCode());

		ResourceFolder getParentResourceFolder =
			getResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getParentResourceFolder.getExternalReferenceCode());
	}

	private void _testPostSiteResourceFileBatch() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFolder postParentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile resourceFile = _randomResourceFile(
			postParentResourceFolder);

		resourceFile.setFileURLReference(_toFileURLReference(bytes));

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		try (SafeCloseable safeCloseable =
				LazyReferencingTestUtil.setLazyReferencingWithSafeCloseable(
					true)) {

			waitForFinish(
				"COMPLETED",
				HTTPTestUtil.invokeToJSONObject(
					_exportResourceFilesToJSON(
						testGroup.getExternalReferenceCode()),
					"headless-admin-fragment/v1.0/sites/" +
						irrelevantGroup.getExternalReferenceCode() +
							"/resource-files/batch?createStrategy=INSERT",
					Http.Method.POST));
		}

		ResourceFile importedResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode(),
			irrelevantGroup.getExternalReferenceCode());

		FragmentSet importedFragmentSet = importedResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			importedFragmentSet.getExternalReferenceCode());

		ResourceFolder importedParentResourceFolder =
			importedResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			importedParentResourceFolder.getExternalReferenceCode());

		Assert.assertNotNull(
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					fragmentCollection.getExternalReferenceCode(),
					irrelevantGroup.getGroupId()));

		_assertContent(
			bytes, postResourceFile.getExternalReferenceCode(),
			irrelevantGroup.getGroupId());
	}

	private void _testPostSiteResourceFileDuplicateExternalReferenceCodeProblemException()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				_randomResourceFile(
					fragmentCollection.getExternalReferenceCode()));

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setExternalReferenceCode(
			postResourceFile.getExternalReferenceCode());

		_assertProblemException(
			"CONFLICT", "this-external-reference-code-is-already-in-use",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile));
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCode()
		throws Exception {

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile sourceResourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		sourceResourceFile.setFileURLReference(_toFileURLReference(bytes));

		ResourceFile postSourceResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), sourceResourceFile);

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(
			postSourceResourceFile.getExternalReferenceCode());

		resourceFile.setFileURLReference(fileURLReference);

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		_assertContent(
			bytes, postResourceFile.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCodeAndFileBase64()
		throws Exception {

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile sourceResourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		sourceResourceFile.setFileURLReference(_toFileURLReference(bytes));

		ResourceFile postSourceResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), sourceResourceFile);

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(
			postSourceResourceFile.getExternalReferenceCode());

		fileURLReference.setFileBase64(_content1Base64);

		_postSiteResourceFileAndAssertContent(bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCodeEmptyAndFileBase64()
		throws Exception {

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(StringPool.BLANK);

		fileURLReference.setFileBase64(_content1Base64);

		_postSiteResourceFileAndAssertContent(_content1Bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentAndFileBase64()
		throws Exception {

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(
			RandomTestUtil.randomString());

		fileURLReference.setFileBase64(_content1Base64);

		_postSiteResourceFileAndAssertContent(_content1Bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentAndURL()
		throws Exception {

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(
			RandomTestUtil.randomString());

		fileURLReference.setUrl(_content1URL);

		_postSiteResourceFileAndAssertContent(_content1Bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceExternalReferenceCodeNonexistentProblemException()
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setExternalReferenceCode(
			RandomTestUtil.randomString());

		resourceFile.setFileURLReference(fileURLReference);

		_assertProblemException(
			"content-is-empty",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile));
	}

	private void _testPostSiteResourceFileFileURLReferenceFileBase64AndURL()
		throws Exception {

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setFileBase64(_content1Base64);

		fileURLReference.setUrl(_content2URL);

		_postSiteResourceFileAndAssertContent(_content1Bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceNullProblemException()
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		resourceFile.setFileURLReference((FileURLReference)null);

		_assertProblemException(
			"content-is-empty",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile));
	}

	private void _testPostSiteResourceFileFileURLReferenceURL()
		throws Exception {

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setUrl(_content1URL);

		_postSiteResourceFileAndAssertContent(_content1Bytes, fileURLReference);
	}

	private void _testPostSiteResourceFileFileURLReferenceURLProblemException(
			String expectedTitle, String url)
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setUrl(url);

		resourceFile.setFileURLReference(fileURLReference);

		try {
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

	private void _testPostSiteResourceFileFileURLReferenceURLUnreachableProblemException()
		throws Exception {

		String url = "http://127.0.0.1:1/" + RandomTestUtil.randomString();

		_testPostSiteResourceFileFileURLReferenceURLProblemException(
			"Unable to download file from " + url, url);
	}

	private void _testPostSiteResourceFileFileURLReferenceURLUnsupportedProtocolProblemException()
		throws Exception {

		String url =
			"ftp://invalid.example.test/" + RandomTestUtil.randomString();

		_testPostSiteResourceFileFileURLReferenceURLProblemException(
			"Unable to download file from " + url +
				" because of unsupported protocol ftp",
			url);
	}

	private void _testPostSiteResourceFileFragmentSetAndFragmentSetExternalReferenceCode()
		throws Exception {

		FragmentCollection fragmentCollection1 = _addFragmentCollection(
			testGroup.getGroupId());

		FragmentCollection fragmentCollection2 = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection2.getExternalReferenceCode());

		resourceFile.setFragmentSet(
			_toFragmentSet(fragmentCollection1.getExternalReferenceCode()));

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		FragmentSet getFragmentSet = getResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection2.getExternalReferenceCode(),
			getFragmentSet.getExternalReferenceCode());

		Assert.assertEquals(
			fragmentCollection2.getExternalReferenceCode(),
			getResourceFile.getFragmentSetExternalReferenceCode());
	}

	private void _testPostSiteResourceFileFragmentSetAndFragmentSetExternalReferenceCodeProblemException()
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile(
			RandomTestUtil.randomString());

		resourceFile.setFragmentSet(
			_toFragmentSet(RandomTestUtil.randomString()));

		_assertProblemException(
			"the-fragment-set-external-reference-codes-do-not-match",
			() -> {
				try (SafeCloseable safeCloseable =
						LazyReferencingTestUtil.
							setLazyReferencingWithSafeCloseable(true)) {

					resourceFileResource.postSiteResourceFile(
						testGroup.getExternalReferenceCode(), resourceFile);
				}
			});
	}

	private void _testPostSiteResourceFileFragmentSetExternalReferenceCode()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				_randomResourceFile(
					fragmentCollection.getExternalReferenceCode()));

		ResourceFile getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		FragmentSet getFragmentSet = getResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			getFragmentSet.getExternalReferenceCode());

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			getResourceFile.getFragmentSetExternalReferenceCode());
	}

	private void _testPostSiteResourceFileFragmentSetExternalReferenceCodeNullProblemException()
		throws Exception {

		ResourceFile resourceFile = _randomResourceFile((String)null);

		_assertProblemException(
			"a-fragment-set-external-reference-code-is-required-to-create-a-" +
				"new-resource-file",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile));
	}

	private void _testPostSiteResourceFileFragmentSetNonexistentProblemException()
		throws Exception {

		String fragmentSetExternalReferenceCode = RandomTestUtil.randomString();

		ResourceFile resourceFile = _randomResourceFile(
			fragmentSetExternalReferenceCode);

		_assertProblemException(
			"no-fragment-set-was-found-with-external-reference-code-x",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile),
			fragmentSetExternalReferenceCode);
	}

	private void _testPostSiteResourceFileParentResourceFolderAndParentResourceFolderExternalReferenceCode()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		ResourceFolder postParentResourceFolder1 = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());
		ResourceFolder postParentResourceFolder2 = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolder(postParentResourceFolder1);
		resourceFile.setParentResourceFolderExternalReferenceCode(
			postParentResourceFolder2.getExternalReferenceCode());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		ResourceFolder getParentResourceFolder =
			getResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder2.getExternalReferenceCode(),
			getParentResourceFolder.getExternalReferenceCode());
	}

	private void _testPostSiteResourceFileParentResourceFolderExternalReferenceCode()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		ResourceFolder postParentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			postParentResourceFolder.getExternalReferenceCode());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = _getSiteResourceFile(
			postResourceFile.getExternalReferenceCode());

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getResourceFile.getParentResourceFolderExternalReferenceCode());

		ResourceFolder getParentResourceFolder =
			getResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getParentResourceFolder.getExternalReferenceCode());
	}

	private void _testPostSiteResourceFileParentResourceFolderNonexistentProblemException()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		String parentResourceFolderExternalReferenceCode =
			RandomTestUtil.randomString();

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			parentResourceFolderExternalReferenceCode);

		_assertProblemException(
			"no-resource-folder-was-found-with-external-reference-code-x",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile),
			parentResourceFolderExternalReferenceCode);
	}

	private void _testPostSiteResourceFileParentResourceFolderPortletFolderProblemException()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		FileEntry fileEntry = _addPortletFileEntry();

		String parentResourceFolderExternalReferenceCode =
			fileEntry.getExternalReferenceCode();

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			parentResourceFolderExternalReferenceCode);

		_assertProblemException(
			"no-resource-folder-was-found-with-external-reference-code-x",
			() -> resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile),
			parentResourceFolderExternalReferenceCode);
	}

	private void _testPostSiteResourceFileWithoutPermissionsProblemException()
		throws Exception {

		try {
			_resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), randomResourceFile());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("FORBIDDEN", problem.getStatus());
		}
	}

	private void _testPutSiteResourceFile() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFile originalResourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		ResourceFile putResourceFile = resourceFileResource.putSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			originalResourceFile.getExternalReferenceCode(),
			originalResourceFile);

		assertEquals(originalResourceFile, putResourceFile);
		assertValid(putResourceFile);

		FragmentCollection irrelevantFragmentCollection =
			_addFragmentCollection(testGroup.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile updatedResourceFile = _randomResourceFile(
			_postSiteResourceFolder(
				irrelevantFragmentCollection.getExternalReferenceCode()));

		updatedResourceFile.setFileURLReference(_toFileURLReference(bytes));

		putResourceFile = _putSiteResourceFile(
			updatedResourceFile,
			originalResourceFile.getExternalReferenceCode());

		Assert.assertEquals(
			originalResourceFile.getExternalReferenceCode(),
			putResourceFile.getExternalReferenceCode());
		Assert.assertEquals(
			updatedResourceFile.getName(), putResourceFile.getName());
		Assert.assertNull(putResourceFile.getParentResourceFolder());
		Assert.assertNull(
			putResourceFile.getParentResourceFolderExternalReferenceCode());

		FragmentSet fragmentSet = putResourceFile.getFragmentSet();

		Assert.assertEquals(
			fragmentCollection.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode());

		_assertContent(
			bytes, originalResourceFile.getExternalReferenceCode(),
			testGroup.getGroupId());

		ResourceFile getResourceFile = resourceFileResource.getSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			originalResourceFile.getExternalReferenceCode());

		Assert.assertEquals(
			originalResourceFile.getExternalReferenceCode(),
			getResourceFile.getExternalReferenceCode());
		Assert.assertEquals(
			updatedResourceFile.getName(), getResourceFile.getName());
	}

	private void _testPutSiteResourceFileFileURLReferenceNull()
		throws Exception {

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		resourceFile.setFileURLReference(_toFileURLReference(bytes));

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		ResourceFile updatedResourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		updatedResourceFile.setFileURLReference((FileURLReference)null);

		ResourceFile putResourceFile = resourceFileResource.putSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode(), updatedResourceFile);

		Assert.assertEquals(
			updatedResourceFile.getName(), putResourceFile.getName());

		_assertContent(
			bytes, postResourceFile.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private void _testPutSiteResourceFileNameNull() throws Exception {
		ResourceFile resourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		ResourceFile postResourceFile =
			resourceFileResource.postSiteResourceFile(
				testGroup.getExternalReferenceCode(), resourceFile);

		byte[] bytes = RandomTestUtil.randomBytes();

		ResourceFile updatedResourceFile = _randomResourceFile(
			_getFragmentSetExternalReferenceCode());

		updatedResourceFile.setFileURLReference(_toFileURLReference(bytes));

		updatedResourceFile.setName((String)null);

		ResourceFile putResourceFile = resourceFileResource.putSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			postResourceFile.getExternalReferenceCode(), updatedResourceFile);

		Assert.assertEquals(resourceFile.getName(), putResourceFile.getName());

		_assertContent(
			bytes, postResourceFile.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private void _testPutSiteResourceFileParentResourceFolderExternalReferenceCode()
		throws Exception {

		FragmentCollection fragmentCollection = _addFragmentCollection(
			testGroup.getGroupId());

		ResourceFolder postParentResourceFolder = _postSiteResourceFolder(
			fragmentCollection.getExternalReferenceCode());

		ResourceFile resourceFile = _randomResourceFile(
			fragmentCollection.getExternalReferenceCode());

		resourceFile.setParentResourceFolderExternalReferenceCode(
			postParentResourceFolder.getExternalReferenceCode());

		ResourceFile putResourceFile = resourceFileResource.putSiteResourceFile(
			testGroup.getExternalReferenceCode(),
			resourceFile.getExternalReferenceCode(), resourceFile);

		ResourceFile getResourceFile = _getSiteResourceFile(
			putResourceFile.getExternalReferenceCode());

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getResourceFile.getParentResourceFolderExternalReferenceCode());

		ResourceFolder getParentResourceFolder =
			getResourceFile.getParentResourceFolder();

		Assert.assertEquals(
			postParentResourceFolder.getExternalReferenceCode(),
			getParentResourceFolder.getExternalReferenceCode());
	}

	private void _testPutSiteResourceFilePortletFileProblemException()
		throws Exception {

		ResourceFile resourceFile = randomResourceFile();

		FileEntry fileEntry = _addPortletFileEntry();

		resourceFile.setExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		try {
			resourceFileResource.putSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				fileEntry.getExternalReferenceCode(), resourceFile);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	private void _testPutSiteResourceFileWithoutPermissionsProblemException()
		throws Exception {

		ResourceFile resourceFile = resourceFileResource.postSiteResourceFile(
			testGroup.getExternalReferenceCode(), randomResourceFile());

		resourceFile.setName(RandomTestUtil.randomString());

		try {
			_resourceFileResource.putSiteResourceFile(
				testGroup.getExternalReferenceCode(),
				resourceFile.getExternalReferenceCode(), resourceFile);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("FORBIDDEN", problem.getStatus());
		}
	}

	private FileURLReference _toFileURLReference(byte[] bytes) {
		FileURLReference fileURLReference = new FileURLReference();

		fileURLReference.setFileBase64(Base64.encode(bytes));

		return fileURLReference;
	}

	private FragmentSet _toFragmentSet(String externalReferenceCode) {
		FragmentSet fragmentSet = new FragmentSet();

		fragmentSet.setExternalReferenceCode(externalReferenceCode);

		return fragmentSet;
	}

	private JSONObject _waitForFinish(
			String expectedExecuteStatus, boolean importTask,
			JSONObject jsonObject)
		throws Exception {

		String endpoint = StringBundler.concat(
			"headless-batch-engine/v1.0/",
			importTask ? "import-task" : "export-task",
			"/by-external-reference-code/");

		while (true) {
			jsonObject = HTTPTestUtil.invokeToJSONObject(
				null, endpoint + jsonObject.getString("externalReferenceCode"),
				Http.Method.GET);

			String executeStatus = jsonObject.getString("executeStatus");

			if (StringUtil.equals(executeStatus, "COMPLETED") ||
				StringUtil.equals(executeStatus, "FAILED")) {

				Assert.assertEquals(expectedExecuteStatus, executeStatus);

				return jsonObject;
			}
		}
	}

	private static String _content1Base64;
	private static byte[] _content1Bytes;
	private static String _content1URL;
	private static byte[] _content2Bytes;
	private static String _content2URL;
	private static HttpServer _httpServer;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	private String _fragmentSetExternalReferenceCode;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	private ResourceFileResource _resourceFileResource;
	private ResourceFolderResource _resourceFolderResource;

}