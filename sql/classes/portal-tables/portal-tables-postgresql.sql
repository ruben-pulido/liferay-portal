create table Address (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	addressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	countryId bigint,
	regionId bigint,
	typeId bigint,
	city varchar(75) null,
	description text null,
	latitude double precision,
	longitude double precision,
	mailing bool,
	name varchar(255) null,
	primary_ bool,
	street1 varchar(255) null,
	street2 varchar(255) null,
	street3 varchar(255) null,
	validationDate timestamp null,
	validationStatus integer,
	zip varchar(75) null
);

create table AnnouncementsDelivery (
	mvccVersion bigint default 0 not null,
	deliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	email bool,
	sms bool,
	website bool
);

create table AnnouncementsEntry (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	title varchar(75) null,
	content text null,
	url text null,
	type_ varchar(75) null,
	displayDate timestamp null,
	expirationDate timestamp null,
	priority integer,
	alert bool
);

create table AnnouncementsFlag (
	mvccVersion bigint default 0 not null,
	flagId bigint not null primary key,
	companyId bigint,
	userId bigint,
	createDate timestamp null,
	entryId bigint,
	value integer
);

create table AssetCategory (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	categoryId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentCategoryId bigint,
	treePath text null,
	name varchar(255) null,
	title text null,
	description text null,
	vocabularyId bigint,
	lastPublishDate timestamp null,
	primary key (categoryId, ctCollectionId)
);

create table AssetEntries_AssetTags (
	companyId bigint not null,
	entryId bigint not null,
	tagId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (entryId, tagId, ctCollectionId)
);

create table AssetEntry (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	entryId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	classTypeId bigint,
	listable bool,
	visible bool,
	startDate timestamp null,
	endDate timestamp null,
	publishDate timestamp null,
	expirationDate timestamp null,
	mimeType varchar(75) null,
	title text null,
	description text null,
	summary text null,
	url text null,
	layoutUuid varchar(75) null,
	height integer,
	width integer,
	priority double precision,
	primary key (entryId, ctCollectionId)
);

create table AssetLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	linkId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	entryId1 bigint,
	entryId2 bigint,
	type_ integer,
	weight integer,
	primary key (linkId, ctCollectionId)
);

create table AssetTag (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	tagId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	assetCount integer,
	lastPublishDate timestamp null,
	primary key (tagId, ctCollectionId)
);

create table AssetVocabulary (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	vocabularyId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	title text null,
	description text null,
	settings_ text null,
	visibilityType integer,
	lastPublishDate timestamp null,
	primary key (vocabularyId, ctCollectionId)
);

create table BrowserTracker (
	mvccVersion bigint default 0 not null,
	browserTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	browserKey bigint
);

create table ClassName_ (
	mvccVersion bigint default 0 not null,
	classNameId bigint not null primary key,
	value varchar(200) null
);

create table Company (
	mvccVersion bigint default 0 not null,
	companyId bigint not null primary key,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	webId varchar(75) null,
	mx varchar(200) null,
	homeURL text null,
	logoId bigint,
	system_ bool,
	maxUsers integer,
	active_ bool,
	name varchar(75) null,
	legalName varchar(75) null,
	legalId varchar(75) null,
	legalType varchar(75) null,
	sicCode varchar(75) null,
	tickerSymbol varchar(75) null,
	industry varchar(75) null,
	type_ varchar(75) null,
	size_ varchar(75) null
);

create table CompanyInfo (
	mvccVersion bigint default 0 not null,
	companyInfoId bigint not null primary key,
	companyId bigint,
	key_ text null
);

create table Contact_ (
	mvccVersion bigint default 0 not null,
	contactId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	parentContactId bigint,
	emailAddress varchar(254) null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	prefixId bigint,
	suffixId bigint,
	male bool,
	birthday timestamp null,
	smsSn varchar(75) null,
	facebookSn varchar(75) null,
	jabberSn varchar(75) null,
	skypeSn varchar(75) null,
	twitterSn varchar(75) null,
	employeeStatusId varchar(75) null,
	employeeNumber varchar(75) null,
	jobTitle varchar(100) null,
	jobClass varchar(75) null,
	hoursOfOperation varchar(75) null
);

create table Counter (
	name varchar(150) not null primary key,
	currentId bigint
);

create table Country (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	defaultLanguageId varchar(75) null,
	countryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	a2 varchar(75) null,
	a3 varchar(75) null,
	active_ bool,
	billingAllowed bool,
	groupFilterEnabled bool,
	idd_ varchar(75) null,
	name varchar(75) null,
	number_ varchar(75) null,
	position double precision,
	shippingAllowed bool,
	subjectToVAT bool,
	zipRequired bool,
	lastPublishDate timestamp null
);

create table CountryLocalization (
	mvccVersion bigint default 0 not null,
	countryLocalizationId bigint not null primary key,
	companyId bigint,
	countryId bigint,
	languageId varchar(75) null,
	title varchar(75) null
);

create table DLFileEntry (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	fileEntryId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	repositoryId bigint,
	folderId bigint,
	treePath text null,
	name varchar(255) null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description text null,
	extraSettings text null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	smallImageId bigint,
	largeImageId bigint,
	custom1ImageId bigint,
	custom2ImageId bigint,
	manualCheckInRequired bool,
	expirationDate timestamp null,
	reviewDate timestamp null,
	lastPublishDate timestamp null,
	primary key (fileEntryId, ctCollectionId)
);

create table DLFileEntryMetadata (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileEntryMetadataId bigint not null,
	companyId bigint,
	DDMStorageId bigint,
	DDMStructureId bigint,
	fileEntryId bigint,
	fileVersionId bigint,
	primary key (fileEntryMetadataId, ctCollectionId)
);

create table DLFileEntryType (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileEntryTypeId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	dataDefinitionId bigint,
	fileEntryTypeKey varchar(75) null,
	name text null,
	description text null,
	scope integer,
	lastPublishDate timestamp null,
	primary key (fileEntryTypeId, ctCollectionId)
);

create table DLFileEntryTypes_DLFolders (
	companyId bigint not null,
	fileEntryTypeId bigint not null,
	folderId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (fileEntryTypeId, folderId, ctCollectionId)
);

create table DLFileShortcut (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileShortcutId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	folderId bigint,
	toFileEntryId bigint,
	treePath text null,
	active_ bool,
	lastPublishDate timestamp null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null,
	primary key (fileShortcutId, ctCollectionId)
);

create table DLFileVersion (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileVersionId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	folderId bigint,
	fileEntryId bigint,
	treePath text null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description text null,
	changeLog varchar(75) null,
	extraSettings text null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	checksum varchar(75) null,
	expirationDate timestamp null,
	reviewDate timestamp null,
	lastPublishDate timestamp null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null,
	primary key (fileVersionId, ctCollectionId)
);

create table DLFolder (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	folderId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	mountPoint bool,
	parentFolderId bigint,
	treePath text null,
	name varchar(255) null,
	description text null,
	lastPostDate timestamp null,
	defaultFileEntryTypeId bigint,
	hidden_ bool,
	restrictionType integer,
	lastPublishDate timestamp null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null,
	primary key (folderId, ctCollectionId)
);

create table EmailAddress (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	emailAddressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	address varchar(254) null,
	typeId bigint,
	primary_ bool
);

create table ExpandoColumn (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	columnId bigint not null,
	companyId bigint,
	tableId bigint,
	name varchar(75) null,
	type_ integer,
	defaultData text null,
	typeSettings text null,
	primary key (columnId, ctCollectionId)
);

create table ExpandoRow (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	rowId_ bigint not null,
	companyId bigint,
	modifiedDate timestamp null,
	tableId bigint,
	classPK bigint,
	primary key (rowId_, ctCollectionId)
);

create table ExpandoTable (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	tableId bigint not null,
	companyId bigint,
	classNameId bigint,
	name varchar(75) null,
	primary key (tableId, ctCollectionId)
);

create table ExpandoValue (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	valueId bigint not null,
	companyId bigint,
	tableId bigint,
	columnId bigint,
	rowId_ bigint,
	classNameId bigint,
	classPK bigint,
	data_ text null,
	primary key (valueId, ctCollectionId)
);

create table ExportImportConfiguration (
	mvccVersion bigint default 0 not null,
	exportImportConfigurationId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(200) null,
	description text null,
	type_ integer,
	settings_ text null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table Group_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	groupId bigint not null,
	companyId bigint,
	creatorUserId bigint,
	classNameId bigint,
	classPK bigint,
	parentGroupId bigint,
	liveGroupId bigint,
	treePath text null,
	groupKey varchar(150) null,
	name text null,
	description text null,
	type_ integer,
	typeSettings text null,
	manualMembership bool,
	membershipRestriction integer,
	friendlyURL varchar(255) null,
	site bool,
	remoteStagingGroupCount integer,
	inheritContent bool,
	active_ bool,
	primary key (groupId, ctCollectionId)
);

create table Groups_Orgs (
	companyId bigint not null,
	groupId bigint not null,
	organizationId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (groupId, organizationId, ctCollectionId)
);

create table Groups_Roles (
	companyId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (groupId, roleId, ctCollectionId)
);

create table Groups_UserGroups (
	companyId bigint not null,
	groupId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (groupId, userGroupId, ctCollectionId)
);

create table Image (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	imageId bigint not null,
	companyId bigint,
	modifiedDate timestamp null,
	type_ varchar(75) null,
	height integer,
	width integer,
	size_ integer,
	primary key (imageId, ctCollectionId)
);

create table Layout (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	plid bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentPlid bigint,
	privateLayout bool,
	layoutId bigint,
	parentLayoutId bigint,
	classNameId bigint,
	classPK bigint,
	name text null,
	title text null,
	description text null,
	keywords text null,
	robots text null,
	type_ varchar(75) null,
	typeSettings text null,
	hidden_ bool,
	system_ bool,
	friendlyURL varchar(255) null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	styleBookEntryId bigint,
	css text null,
	priority integer,
	masterLayoutPlid bigint,
	layoutPrototypeUuid varchar(75) null,
	layoutPrototypeLinkEnabled bool,
	sourcePrototypeLayoutUuid varchar(75) null,
	publishDate timestamp null,
	lastPublishDate timestamp null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null,
	primary key (plid, ctCollectionId)
);

create table LayoutBranch (
	mvccVersion bigint default 0 not null,
	layoutBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	layoutSetBranchId bigint,
	plid bigint,
	name varchar(75) null,
	description text null,
	master bool
);

create table LayoutFriendlyURL (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutFriendlyURLId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	plid bigint,
	privateLayout bool,
	friendlyURL varchar(255) null,
	languageId varchar(75) null,
	lastPublishDate timestamp null,
	primary key (layoutFriendlyURLId, ctCollectionId)
);

create table LayoutPrototype (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name text null,
	description text null,
	settings_ text null,
	active_ bool
);

create table LayoutRevision (
	mvccVersion bigint default 0 not null,
	layoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	layoutSetBranchId bigint,
	layoutBranchId bigint,
	parentLayoutRevisionId bigint,
	head bool,
	major bool,
	plid bigint,
	privateLayout bool,
	name text null,
	title text null,
	description text null,
	keywords text null,
	robots text null,
	typeSettings text null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css text null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table LayoutSet (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	layoutSetId bigint not null,
	groupId bigint,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	privateLayout bool,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css text null,
	settings_ text null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled bool,
	primary key (layoutSetId, ctCollectionId)
);

create table LayoutSetBranch (
	mvccVersion bigint default 0 not null,
	layoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	privateLayout bool,
	name varchar(75) null,
	description text null,
	master bool,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css text null,
	settings_ text null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled bool
);

create table LayoutSetPrototype (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutSetPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name text null,
	description text null,
	settings_ text null,
	active_ bool
);

create table ListType (
	mvccVersion bigint default 0 not null,
	listTypeId bigint not null primary key,
	name varchar(75) null,
	type_ varchar(75) null
);

create table MembershipRequest (
	mvccVersion bigint default 0 not null,
	membershipRequestId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate timestamp null,
	comments text null,
	replyComments text null,
	replyDate timestamp null,
	replierUserId bigint,
	statusId bigint
);

create table Organization_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	organizationId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentOrganizationId bigint,
	treePath text null,
	name varchar(100) null,
	type_ varchar(75) null,
	recursable bool,
	regionId bigint,
	countryId bigint,
	statusId bigint,
	comments text null,
	logoId bigint,
	primary key (organizationId, ctCollectionId)
);

create table OrgGroupRole (
	mvccVersion bigint default 0 not null,
	organizationId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	companyId bigint,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	mvccVersion bigint default 0 not null,
	orgLaborId bigint not null primary key,
	companyId bigint,
	organizationId bigint,
	typeId bigint,
	sunOpen integer,
	sunClose integer,
	monOpen integer,
	monClose integer,
	tueOpen integer,
	tueClose integer,
	wedOpen integer,
	wedClose integer,
	thuOpen integer,
	thuClose integer,
	friOpen integer,
	friClose integer,
	satOpen integer,
	satClose integer
);

create table PasswordPolicy (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	passwordPolicyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	defaultPolicy bool,
	name varchar(75) null,
	description text null,
	changeable bool,
	changeRequired bool,
	minAge bigint,
	checkSyntax bool,
	allowDictionaryWords bool,
	minAlphanumeric integer,
	minLength integer,
	minLowerCase integer,
	minNumbers integer,
	minSymbols integer,
	minUpperCase integer,
	regex text null,
	history bool,
	historyCount integer,
	expireable bool,
	maxAge bigint,
	warningTime bigint,
	graceLimit integer,
	lockout bool,
	maxFailure integer,
	lockoutDuration bigint,
	requireUnlock bool,
	resetFailureCount bigint,
	resetTicketMaxAge bigint
);

create table PasswordPolicyRel (
	mvccVersion bigint default 0 not null,
	passwordPolicyRelId bigint not null primary key,
	companyId bigint,
	passwordPolicyId bigint,
	classNameId bigint,
	classPK bigint
);

create table PasswordTracker (
	mvccVersion bigint default 0 not null,
	passwordTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	createDate timestamp null,
	password_ varchar(75) null
);

create table Phone (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	phoneId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	number_ varchar(75) null,
	extension varchar(75) null,
	typeId bigint,
	primary_ bool
);

create table PluginSetting (
	mvccVersion bigint default 0 not null,
	pluginSettingId bigint not null primary key,
	companyId bigint,
	pluginId varchar(75) null,
	pluginType varchar(75) null,
	roles text null,
	active_ bool
);

create table PortalPreferenceValue (
	mvccVersion bigint default 0 not null,
	portalPreferenceValueId bigint not null primary key,
	companyId bigint,
	portalPreferencesId bigint,
	index_ integer,
	key_ varchar(255) null,
	largeValue text null,
	namespace varchar(255) null,
	smallValue varchar(255) null
);

create table PortalPreferences (
	mvccVersion bigint default 0 not null,
	portalPreferencesId bigint not null primary key,
	companyId bigint,
	ownerId bigint,
	ownerType integer
);

create table Portlet (
	mvccVersion bigint default 0 not null,
	id_ bigint not null primary key,
	companyId bigint,
	portletId varchar(200) null,
	roles text null,
	active_ bool
);

create table PortletItem (
	mvccVersion bigint default 0 not null,
	portletItemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	portletId varchar(200) null,
	classNameId bigint
);

create table PortletPreferenceValue (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	portletPreferenceValueId bigint not null,
	companyId bigint,
	portletPreferencesId bigint,
	index_ integer,
	largeValue text null,
	name varchar(255) null,
	readOnly bool,
	smallValue varchar(255) null,
	primary key (portletPreferenceValueId, ctCollectionId)
);

create table PortletPreferences (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	portletPreferencesId bigint not null,
	companyId bigint,
	ownerId bigint,
	ownerType integer,
	plid bigint,
	portletId varchar(200) null,
	primary key (portletPreferencesId, ctCollectionId)
);

create table RatingsEntry (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	entryId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	score double precision,
	primary key (entryId, ctCollectionId)
);

create table RatingsStats (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	statsId bigint not null,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	totalEntries integer,
	totalScore double precision,
	averageScore double precision,
	primary key (statsId, ctCollectionId)
);

create table RecentLayoutBranch (
	mvccVersion bigint default 0 not null,
	recentLayoutBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutBranchId bigint,
	layoutSetBranchId bigint,
	plid bigint
);

create table RecentLayoutRevision (
	mvccVersion bigint default 0 not null,
	recentLayoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutRevisionId bigint,
	layoutSetBranchId bigint,
	plid bigint
);

create table RecentLayoutSetBranch (
	mvccVersion bigint default 0 not null,
	recentLayoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutSetBranchId bigint,
	layoutSetId bigint
);

create table Region (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	defaultLanguageId varchar(75) null,
	regionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	countryId bigint,
	active_ bool,
	name varchar(75) null,
	position double precision,
	regionCode varchar(75) null,
	lastPublishDate timestamp null
);

create table RegionLocalization (
	mvccVersion bigint default 0 not null,
	regionLocalizationId bigint not null primary key,
	companyId bigint,
	regionId bigint,
	languageId varchar(75) null,
	title varchar(75) null
);

create table Release_ (
	mvccVersion bigint default 0 not null,
	releaseId bigint not null primary key,
	createDate timestamp null,
	modifiedDate timestamp null,
	servletContextName varchar(75) null,
	schemaVersion varchar(75) null,
	buildNumber integer,
	buildDate timestamp null,
	verified bool,
	state_ integer,
	testString varchar(1024) null
);

create table Repository (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	repositoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	name varchar(200) null,
	description text null,
	portletId varchar(200) null,
	typeSettings text null,
	dlFolderId bigint,
	lastPublishDate timestamp null
);

create table RepositoryEntry (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	repositoryEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	mappedId varchar(255) null,
	manualCheckInRequired bool,
	lastPublishDate timestamp null
);

create table ResourceAction (
	mvccVersion bigint default 0 not null,
	resourceActionId bigint not null primary key,
	name varchar(255) null,
	actionId varchar(75) null,
	bitwiseValue bigint
);

create table ResourcePermission (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	resourcePermissionId bigint not null,
	companyId bigint,
	name varchar(255) null,
	scope integer,
	primKey varchar(255) null,
	primKeyId bigint,
	roleId bigint,
	ownerId bigint,
	actionIds bigint,
	viewActionId bool,
	primary key (resourcePermissionId, ctCollectionId)
);

create table Role_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	roleId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	title text null,
	description text null,
	type_ integer,
	subtype varchar(75) null,
	primary key (roleId, ctCollectionId)
);

create table ServiceComponent (
	mvccVersion bigint default 0 not null,
	serviceComponentId bigint not null primary key,
	buildNamespace varchar(75) null,
	buildNumber bigint,
	buildDate bigint,
	data_ text null
);

create table SocialActivity (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activityId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	activitySetId bigint,
	mirrorActivityId bigint,
	classNameId bigint,
	classPK bigint,
	parentClassNameId bigint,
	parentClassPK bigint,
	type_ integer,
	extraData text null,
	receiverUserId bigint,
	primary key (activityId, ctCollectionId)
);

create table SocialActivityAchievement (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activityAchievementId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	name varchar(75) null,
	firstInGroup bool,
	primary key (activityAchievementId, ctCollectionId)
);

create table SocialActivityCounter (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activityCounterId bigint not null,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	ownerType integer,
	currentValue integer,
	totalValue integer,
	graceValue integer,
	startPeriod integer,
	endPeriod integer,
	active_ bool,
	primary key (activityCounterId, ctCollectionId)
);

create table SocialActivityLimit (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activityLimitId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	classNameId bigint,
	classPK bigint,
	activityType integer,
	activityCounterName varchar(75) null,
	value varchar(75) null,
	primary key (activityLimitId, ctCollectionId)
);

create table SocialActivitySet (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activitySetId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	modifiedDate bigint,
	classNameId bigint,
	classPK bigint,
	type_ integer,
	extraData text null,
	activityCount integer,
	primary key (activitySetId, ctCollectionId)
);

create table SocialActivitySetting (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activitySettingId bigint not null,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	activityType integer,
	name varchar(75) null,
	value varchar(1024) null,
	primary key (activitySettingId, ctCollectionId)
);

create table SocialRelation (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	relationId bigint not null,
	companyId bigint,
	createDate bigint,
	userId1 bigint,
	userId2 bigint,
	type_ integer,
	primary key (relationId, ctCollectionId)
);

create table SocialRequest (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	requestId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	modifiedDate bigint,
	classNameId bigint,
	classPK bigint,
	type_ integer,
	extraData text null,
	receiverUserId bigint,
	status integer,
	primary key (requestId, ctCollectionId)
);

create table SystemEvent (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	systemEventId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	referrerClassNameId bigint,
	parentSystemEventId bigint,
	systemEventSetKey bigint,
	type_ integer,
	extraData text null,
	primary key (systemEventId, ctCollectionId)
);

create table Team (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	teamId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	groupId bigint,
	name varchar(75) null,
	description text null,
	lastPublishDate timestamp null,
	primary key (teamId, ctCollectionId)
);

create table Ticket (
	mvccVersion bigint default 0 not null,
	ticketId bigint not null primary key,
	companyId bigint,
	createDate timestamp null,
	classNameId bigint,
	classPK bigint,
	key_ varchar(75) null,
	type_ integer,
	extraInfo text null,
	expirationDate timestamp null
);

create table UserNotificationDelivery (
	mvccVersion bigint default 0 not null,
	userNotificationDeliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	portletId varchar(200) null,
	classNameId bigint,
	notificationType integer,
	deliveryType integer,
	deliver bool
);

create table User_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	userId bigint not null,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	defaultUser bool,
	contactId bigint,
	password_ varchar(75) null,
	passwordEncrypted bool,
	passwordReset bool,
	passwordModifiedDate timestamp null,
	digest varchar(255) null,
	reminderQueryQuestion varchar(75) null,
	reminderQueryAnswer varchar(75) null,
	graceLoginCount integer,
	screenName varchar(75) null,
	emailAddress varchar(254) null,
	facebookId bigint,
	googleUserId varchar(75) null,
	ldapServerId bigint,
	openId varchar(1024) null,
	portraitId bigint,
	languageId varchar(75) null,
	timeZoneId varchar(75) null,
	greeting varchar(255) null,
	comments text null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	jobTitle varchar(100) null,
	loginDate timestamp null,
	loginIP varchar(75) null,
	lastLoginDate timestamp null,
	lastLoginIP varchar(75) null,
	lastFailedLoginDate timestamp null,
	failedLoginAttempts integer,
	lockout bool,
	lockoutDate timestamp null,
	agreedToTermsOfUse bool,
	emailAddressVerified bool,
	status integer,
	primary key (userId, ctCollectionId)
);

create table UserGroup (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	userGroupId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentUserGroupId bigint,
	name varchar(255) null,
	description text null,
	addedByLDAPImport bool,
	primary key (userGroupId, ctCollectionId)
);

create table UserGroupGroupRole (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	userGroupGroupRoleId bigint not null,
	companyId bigint,
	userGroupId bigint,
	groupId bigint,
	roleId bigint,
	primary key (userGroupGroupRoleId, ctCollectionId)
);

create table UserGroupRole (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	userGroupRoleId bigint not null,
	companyId bigint,
	userId bigint,
	groupId bigint,
	roleId bigint,
	primary key (userGroupRoleId, ctCollectionId)
);

create table UserGroups_Teams (
	companyId bigint not null,
	teamId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (teamId, userGroupId, ctCollectionId)
);

create table UserIdMapper (
	mvccVersion bigint default 0 not null,
	userIdMapperId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	description varchar(75) null,
	externalUserId varchar(75) null
);

create table UserNotificationEvent (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	userNotificationEventId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(200) null,
	timestamp bigint,
	deliveryType integer,
	deliverBy bigint,
	delivered bool,
	payload text null,
	actionRequired bool,
	archived bool
);

create table Users_Groups (
	companyId bigint not null,
	groupId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (groupId, userId, ctCollectionId)
);

create table Users_Orgs (
	companyId bigint not null,
	organizationId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (organizationId, userId, ctCollectionId)
);

create table Users_Roles (
	companyId bigint not null,
	roleId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (roleId, userId, ctCollectionId)
);

create table Users_Teams (
	companyId bigint not null,
	teamId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (teamId, userId, ctCollectionId)
);

create table Users_UserGroups (
	companyId bigint not null,
	userId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType bool,
	primary key (userId, userGroupId, ctCollectionId)
);

create table UserTracker (
	mvccVersion bigint default 0 not null,
	userTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	modifiedDate timestamp null,
	sessionId varchar(200) null,
	remoteAddr varchar(75) null,
	remoteHost varchar(75) null,
	userAgent varchar(200) null
);

create table UserTrackerPath (
	mvccVersion bigint default 0 not null,
	userTrackerPathId bigint not null primary key,
	companyId bigint,
	userTrackerId bigint,
	path_ text null,
	pathDate timestamp null
);

create table VirtualHost (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	virtualHostId bigint not null,
	companyId bigint,
	layoutSetId bigint,
	hostname varchar(200) null,
	defaultVirtualHost bool,
	languageId varchar(75) null,
	primary key (virtualHostId, ctCollectionId)
);

create table WebDAVProps (
	mvccVersion bigint default 0 not null,
	webDavPropsId bigint not null primary key,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	props text null
);

create table Website (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	websiteId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	url text null,
	typeId bigint,
	primary_ bool,
	lastPublishDate timestamp null
);

create table WorkflowDefinitionLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	workflowDefinitionLinkId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	typePK bigint,
	workflowDefinitionName varchar(75) null,
	workflowDefinitionVersion integer,
	primary key (workflowDefinitionLinkId, ctCollectionId)
);

create table WorkflowInstanceLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	workflowInstanceLinkId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	workflowInstanceId bigint,
	primary key (workflowInstanceLinkId, ctCollectionId)
);
