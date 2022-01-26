create table Address (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	addressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	countryId bigint,
	regionId bigint,
	typeId bigint,
	city varchar(75) null,
	description longtext null,
	latitude double,
	longitude double,
	mailing tinyint,
	name varchar(255) null,
	primary_ tinyint,
	street1 varchar(255) null,
	street2 varchar(255) null,
	street3 varchar(255) null,
	validationDate datetime(6) null,
	validationStatus integer,
	zip varchar(75) null
) engine InnoDB;

create table AnnouncementsDelivery (
	mvccVersion bigint default 0 not null,
	deliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	email tinyint,
	sms tinyint,
	website tinyint
) engine InnoDB;

create table AnnouncementsEntry (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	title varchar(75) null,
	content longtext null,
	url longtext null,
	type_ varchar(75) null,
	displayDate datetime(6) null,
	expirationDate datetime(6) null,
	priority integer,
	alert tinyint
) engine InnoDB;

create table AnnouncementsFlag (
	mvccVersion bigint default 0 not null,
	flagId bigint not null primary key,
	companyId bigint,
	userId bigint,
	createDate datetime(6) null,
	entryId bigint,
	value integer
) engine InnoDB;

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
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	parentCategoryId bigint,
	treePath longtext null,
	name varchar(255) null,
	title longtext null,
	description longtext null,
	vocabularyId bigint,
	lastPublishDate datetime(6) null,
	primary key (categoryId, ctCollectionId)
) engine InnoDB;

create table AssetEntries_AssetTags (
	companyId bigint not null,
	entryId bigint not null,
	tagId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (entryId, tagId, ctCollectionId)
) engine InnoDB;

create table AssetEntry (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	entryId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	classTypeId bigint,
	listable tinyint,
	visible tinyint,
	startDate datetime(6) null,
	endDate datetime(6) null,
	publishDate datetime(6) null,
	expirationDate datetime(6) null,
	mimeType varchar(75) null,
	title longtext null,
	description longtext null,
	summary longtext null,
	url longtext null,
	layoutUuid varchar(75) null,
	height integer,
	width integer,
	priority double,
	primary key (entryId, ctCollectionId)
) engine InnoDB;

create table AssetLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	linkId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	entryId1 bigint,
	entryId2 bigint,
	type_ integer,
	weight integer,
	primary key (linkId, ctCollectionId)
) engine InnoDB;

create table AssetTag (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	tagId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name varchar(75) null,
	assetCount integer,
	lastPublishDate datetime(6) null,
	primary key (tagId, ctCollectionId)
) engine InnoDB;

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
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name varchar(75) null,
	title longtext null,
	description longtext null,
	settings_ longtext null,
	visibilityType integer,
	lastPublishDate datetime(6) null,
	primary key (vocabularyId, ctCollectionId)
) engine InnoDB;

create table BrowserTracker (
	mvccVersion bigint default 0 not null,
	browserTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	browserKey bigint
) engine InnoDB;

create table ClassName_ (
	mvccVersion bigint default 0 not null,
	classNameId bigint not null primary key,
	value varchar(200) null
) engine InnoDB;

create table Company (
	mvccVersion bigint default 0 not null,
	companyId bigint not null primary key,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	webId varchar(75) null,
	mx varchar(200) null,
	homeURL longtext null,
	logoId bigint,
	system_ tinyint,
	maxUsers integer,
	active_ tinyint,
	name varchar(75) null,
	legalName varchar(75) null,
	legalId varchar(75) null,
	legalType varchar(75) null,
	sicCode varchar(75) null,
	tickerSymbol varchar(75) null,
	industry varchar(75) null,
	type_ varchar(75) null,
	size_ varchar(75) null
) engine InnoDB;

create table CompanyInfo (
	mvccVersion bigint default 0 not null,
	companyInfoId bigint not null primary key,
	companyId bigint,
	key_ longtext null
) engine InnoDB;

create table Contact_ (
	mvccVersion bigint default 0 not null,
	contactId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	parentContactId bigint,
	emailAddress varchar(254) null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	prefixId bigint,
	suffixId bigint,
	male tinyint,
	birthday datetime(6) null,
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
) engine InnoDB;

create table Counter (
	name varchar(150) not null primary key,
	currentId bigint
) engine InnoDB;

create table Country (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	defaultLanguageId varchar(75) null,
	countryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	a2 varchar(75) null,
	a3 varchar(75) null,
	active_ tinyint,
	billingAllowed tinyint,
	groupFilterEnabled tinyint,
	idd_ varchar(75) null,
	name varchar(75) null,
	number_ varchar(75) null,
	position double,
	shippingAllowed tinyint,
	subjectToVAT tinyint,
	zipRequired tinyint,
	lastPublishDate datetime(6) null
) engine InnoDB;

create table CountryLocalization (
	mvccVersion bigint default 0 not null,
	countryLocalizationId bigint not null primary key,
	companyId bigint,
	countryId bigint,
	languageId varchar(75) null,
	title varchar(75) null
) engine InnoDB;

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
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	repositoryId bigint,
	folderId bigint,
	treePath longtext null,
	name varchar(255) null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longtext null,
	extraSettings longtext null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	smallImageId bigint,
	largeImageId bigint,
	custom1ImageId bigint,
	custom2ImageId bigint,
	manualCheckInRequired tinyint,
	expirationDate datetime(6) null,
	reviewDate datetime(6) null,
	lastPublishDate datetime(6) null,
	primary key (fileEntryId, ctCollectionId)
) engine InnoDB;

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
) engine InnoDB;

create table DLFileEntryType (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileEntryTypeId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	dataDefinitionId bigint,
	fileEntryTypeKey varchar(75) null,
	name longtext null,
	description longtext null,
	scope integer,
	lastPublishDate datetime(6) null,
	primary key (fileEntryTypeId, ctCollectionId)
) engine InnoDB;

create table DLFileEntryTypes_DLFolders (
	companyId bigint not null,
	fileEntryTypeId bigint not null,
	folderId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (fileEntryTypeId, folderId, ctCollectionId)
) engine InnoDB;

create table DLFileShortcut (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileShortcutId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	repositoryId bigint,
	folderId bigint,
	toFileEntryId bigint,
	treePath longtext null,
	active_ tinyint,
	lastPublishDate datetime(6) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null,
	primary key (fileShortcutId, ctCollectionId)
) engine InnoDB;

create table DLFileVersion (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	fileVersionId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	repositoryId bigint,
	folderId bigint,
	fileEntryId bigint,
	treePath longtext null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longtext null,
	changeLog varchar(75) null,
	extraSettings longtext null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	checksum varchar(75) null,
	expirationDate datetime(6) null,
	reviewDate datetime(6) null,
	lastPublishDate datetime(6) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null,
	primary key (fileVersionId, ctCollectionId)
) engine InnoDB;

create table DLFolder (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	folderId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	repositoryId bigint,
	mountPoint tinyint,
	parentFolderId bigint,
	treePath longtext null,
	name varchar(255) null,
	description longtext null,
	lastPostDate datetime(6) null,
	defaultFileEntryTypeId bigint,
	hidden_ tinyint,
	restrictionType integer,
	lastPublishDate datetime(6) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null,
	primary key (folderId, ctCollectionId)
) engine InnoDB;

create table EmailAddress (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	emailAddressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	address varchar(254) null,
	typeId bigint,
	primary_ tinyint
) engine InnoDB;

create table ExpandoColumn (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	columnId bigint not null,
	companyId bigint,
	tableId bigint,
	name varchar(75) null,
	type_ integer,
	defaultData longtext null,
	typeSettings longtext null,
	primary key (columnId, ctCollectionId)
) engine InnoDB;

create table ExpandoRow (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	rowId_ bigint not null,
	companyId bigint,
	modifiedDate datetime(6) null,
	tableId bigint,
	classPK bigint,
	primary key (rowId_, ctCollectionId)
) engine InnoDB;

create table ExpandoTable (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	tableId bigint not null,
	companyId bigint,
	classNameId bigint,
	name varchar(75) null,
	primary key (tableId, ctCollectionId)
) engine InnoDB;

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
	data_ longtext null,
	primary key (valueId, ctCollectionId)
) engine InnoDB;

create table ExportImportConfiguration (
	mvccVersion bigint default 0 not null,
	exportImportConfigurationId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name varchar(200) null,
	description longtext null,
	type_ integer,
	settings_ longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null
) engine InnoDB;

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
	treePath longtext null,
	groupKey varchar(150) null,
	name longtext null,
	description longtext null,
	type_ integer,
	typeSettings longtext null,
	manualMembership tinyint,
	membershipRestriction integer,
	friendlyURL varchar(255) null,
	site tinyint,
	remoteStagingGroupCount integer,
	inheritContent tinyint,
	active_ tinyint,
	primary key (groupId, ctCollectionId)
) engine InnoDB;

create table Groups_Orgs (
	companyId bigint not null,
	groupId bigint not null,
	organizationId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (groupId, organizationId, ctCollectionId)
) engine InnoDB;

create table Groups_Roles (
	companyId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (groupId, roleId, ctCollectionId)
) engine InnoDB;

create table Groups_UserGroups (
	companyId bigint not null,
	groupId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (groupId, userGroupId, ctCollectionId)
) engine InnoDB;

create table Image (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	imageId bigint not null,
	companyId bigint,
	modifiedDate datetime(6) null,
	type_ varchar(75) null,
	height integer,
	width integer,
	size_ integer,
	primary key (imageId, ctCollectionId)
) engine InnoDB;

create table Layout (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	plid bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	parentPlid bigint,
	privateLayout tinyint,
	layoutId bigint,
	parentLayoutId bigint,
	classNameId bigint,
	classPK bigint,
	name longtext null,
	title longtext null,
	description longtext null,
	keywords longtext null,
	robots longtext null,
	type_ varchar(75) null,
	typeSettings longtext null,
	hidden_ tinyint,
	system_ tinyint,
	friendlyURL varchar(255) null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	styleBookEntryId bigint,
	css longtext null,
	priority integer,
	masterLayoutPlid bigint,
	layoutPrototypeUuid varchar(75) null,
	layoutPrototypeLinkEnabled tinyint,
	sourcePrototypeLayoutUuid varchar(75) null,
	publishDate datetime(6) null,
	lastPublishDate datetime(6) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null,
	primary key (plid, ctCollectionId)
) engine InnoDB;

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
	description longtext null,
	master tinyint
) engine InnoDB;

create table LayoutFriendlyURL (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutFriendlyURLId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	plid bigint,
	privateLayout tinyint,
	friendlyURL varchar(255) null,
	languageId varchar(75) null,
	lastPublishDate datetime(6) null,
	primary key (layoutFriendlyURLId, ctCollectionId)
) engine InnoDB;

create table LayoutPrototype (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name longtext null,
	description longtext null,
	settings_ longtext null,
	active_ tinyint
) engine InnoDB;

create table LayoutRevision (
	mvccVersion bigint default 0 not null,
	layoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	layoutSetBranchId bigint,
	layoutBranchId bigint,
	parentLayoutRevisionId bigint,
	head tinyint,
	major tinyint,
	plid bigint,
	privateLayout tinyint,
	name longtext null,
	title longtext null,
	description longtext null,
	keywords longtext null,
	robots longtext null,
	typeSettings longtext null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime(6) null
) engine InnoDB;

create table LayoutSet (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	layoutSetId bigint not null,
	groupId bigint,
	companyId bigint,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	privateLayout tinyint,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css longtext null,
	settings_ longtext null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled tinyint,
	primary key (layoutSetId, ctCollectionId)
) engine InnoDB;

create table LayoutSetBranch (
	mvccVersion bigint default 0 not null,
	layoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	privateLayout tinyint,
	name varchar(75) null,
	description longtext null,
	master tinyint,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	css longtext null,
	settings_ longtext null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled tinyint
) engine InnoDB;

create table LayoutSetPrototype (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	layoutSetPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name longtext null,
	description longtext null,
	settings_ longtext null,
	active_ tinyint
) engine InnoDB;

create table ListType (
	mvccVersion bigint default 0 not null,
	listTypeId bigint not null primary key,
	name varchar(75) null,
	type_ varchar(75) null
) engine InnoDB;

create table MembershipRequest (
	mvccVersion bigint default 0 not null,
	membershipRequestId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate datetime(6) null,
	comments longtext null,
	replyComments longtext null,
	replyDate datetime(6) null,
	replierUserId bigint,
	statusId bigint
) engine InnoDB;

create table Organization_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	organizationId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	parentOrganizationId bigint,
	treePath longtext null,
	name varchar(100) null,
	type_ varchar(75) null,
	recursable tinyint,
	regionId bigint,
	countryId bigint,
	statusId bigint,
	comments longtext null,
	logoId bigint,
	primary key (organizationId, ctCollectionId)
) engine InnoDB;

create table OrgGroupRole (
	mvccVersion bigint default 0 not null,
	organizationId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	companyId bigint,
	primary key (organizationId, groupId, roleId)
) engine InnoDB;

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
) engine InnoDB;

create table PasswordPolicy (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	passwordPolicyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	defaultPolicy tinyint,
	name varchar(75) null,
	description longtext null,
	changeable tinyint,
	changeRequired tinyint,
	minAge bigint,
	checkSyntax tinyint,
	allowDictionaryWords tinyint,
	minAlphanumeric integer,
	minLength integer,
	minLowerCase integer,
	minNumbers integer,
	minSymbols integer,
	minUpperCase integer,
	regex longtext null,
	history tinyint,
	historyCount integer,
	expireable tinyint,
	maxAge bigint,
	warningTime bigint,
	graceLimit integer,
	lockout tinyint,
	maxFailure integer,
	lockoutDuration bigint,
	requireUnlock tinyint,
	resetFailureCount bigint,
	resetTicketMaxAge bigint
) engine InnoDB;

create table PasswordPolicyRel (
	mvccVersion bigint default 0 not null,
	passwordPolicyRelId bigint not null primary key,
	companyId bigint,
	passwordPolicyId bigint,
	classNameId bigint,
	classPK bigint
) engine InnoDB;

create table PasswordTracker (
	mvccVersion bigint default 0 not null,
	passwordTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	createDate datetime(6) null,
	password_ varchar(75) null
) engine InnoDB;

create table Phone (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	phoneId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	number_ varchar(75) null,
	extension varchar(75) null,
	typeId bigint,
	primary_ tinyint
) engine InnoDB;

create table PluginSetting (
	mvccVersion bigint default 0 not null,
	pluginSettingId bigint not null primary key,
	companyId bigint,
	pluginId varchar(75) null,
	pluginType varchar(75) null,
	roles longtext null,
	active_ tinyint
) engine InnoDB;

create table PortalPreferenceValue (
	mvccVersion bigint default 0 not null,
	portalPreferenceValueId bigint not null primary key,
	companyId bigint,
	portalPreferencesId bigint,
	index_ integer,
	key_ varchar(255) null,
	largeValue longtext null,
	namespace varchar(255) null,
	smallValue varchar(255) null
) engine InnoDB;

create table PortalPreferences (
	mvccVersion bigint default 0 not null,
	portalPreferencesId bigint not null primary key,
	companyId bigint,
	ownerId bigint,
	ownerType integer
) engine InnoDB;

create table Portlet (
	mvccVersion bigint default 0 not null,
	id_ bigint not null primary key,
	companyId bigint,
	portletId varchar(200) null,
	roles longtext null,
	active_ tinyint
) engine InnoDB;

create table PortletItem (
	mvccVersion bigint default 0 not null,
	portletItemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	name varchar(75) null,
	portletId varchar(200) null,
	classNameId bigint
) engine InnoDB;

create table PortletPreferenceValue (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	portletPreferenceValueId bigint not null,
	companyId bigint,
	portletPreferencesId bigint,
	index_ integer,
	largeValue longtext null,
	name varchar(255) null,
	readOnly tinyint,
	smallValue varchar(255) null,
	primary key (portletPreferenceValueId, ctCollectionId)
) engine InnoDB;

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
) engine InnoDB;

create table RatingsEntry (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	entryId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	score double,
	primary key (entryId, ctCollectionId)
) engine InnoDB;

create table RatingsStats (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	statsId bigint not null,
	companyId bigint,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	totalEntries integer,
	totalScore double,
	averageScore double,
	primary key (statsId, ctCollectionId)
) engine InnoDB;

create table RecentLayoutBranch (
	mvccVersion bigint default 0 not null,
	recentLayoutBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutBranchId bigint,
	layoutSetBranchId bigint,
	plid bigint
) engine InnoDB;

create table RecentLayoutRevision (
	mvccVersion bigint default 0 not null,
	recentLayoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutRevisionId bigint,
	layoutSetBranchId bigint,
	plid bigint
) engine InnoDB;

create table RecentLayoutSetBranch (
	mvccVersion bigint default 0 not null,
	recentLayoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	layoutSetBranchId bigint,
	layoutSetId bigint
) engine InnoDB;

create table Region (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	defaultLanguageId varchar(75) null,
	regionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	countryId bigint,
	active_ tinyint,
	name varchar(75) null,
	position double,
	regionCode varchar(75) null,
	lastPublishDate datetime(6) null
) engine InnoDB;

create table RegionLocalization (
	mvccVersion bigint default 0 not null,
	regionLocalizationId bigint not null primary key,
	companyId bigint,
	regionId bigint,
	languageId varchar(75) null,
	title varchar(75) null
) engine InnoDB;

create table Release_ (
	mvccVersion bigint default 0 not null,
	releaseId bigint not null primary key,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	servletContextName varchar(75) null,
	schemaVersion varchar(75) null,
	buildNumber integer,
	buildDate datetime(6) null,
	verified tinyint,
	state_ integer,
	testString varchar(1024) null
) engine InnoDB;

create table Repository (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	repositoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	name varchar(200) null,
	description longtext null,
	portletId varchar(200) null,
	typeSettings longtext null,
	dlFolderId bigint,
	lastPublishDate datetime(6) null
) engine InnoDB;

create table RepositoryEntry (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	repositoryEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	repositoryId bigint,
	mappedId varchar(255) null,
	manualCheckInRequired tinyint,
	lastPublishDate datetime(6) null
) engine InnoDB;

create table ResourceAction (
	mvccVersion bigint default 0 not null,
	resourceActionId bigint not null primary key,
	name varchar(255) null,
	actionId varchar(75) null,
	bitwiseValue bigint
) engine InnoDB;

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
	viewActionId tinyint,
	primary key (resourcePermissionId, ctCollectionId)
) engine InnoDB;

create table Role_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	roleId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	title longtext null,
	description longtext null,
	type_ integer,
	subtype varchar(75) null,
	primary key (roleId, ctCollectionId)
) engine InnoDB;

create table ServiceComponent (
	mvccVersion bigint default 0 not null,
	serviceComponentId bigint not null primary key,
	buildNamespace varchar(75) null,
	buildNumber bigint,
	buildDate bigint,
	data_ longtext null
) engine InnoDB;

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
	extraData longtext null,
	receiverUserId bigint,
	primary key (activityId, ctCollectionId)
) engine InnoDB;

create table SocialActivityAchievement (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	activityAchievementId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	name varchar(75) null,
	firstInGroup tinyint,
	primary key (activityAchievementId, ctCollectionId)
) engine InnoDB;

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
	active_ tinyint,
	primary key (activityCounterId, ctCollectionId)
) engine InnoDB;

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
) engine InnoDB;

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
	extraData longtext null,
	activityCount integer,
	primary key (activitySetId, ctCollectionId)
) engine InnoDB;

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
) engine InnoDB;

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
) engine InnoDB;

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
	extraData longtext null,
	receiverUserId bigint,
	status integer,
	primary key (requestId, ctCollectionId)
) engine InnoDB;

create table SystemEvent (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	systemEventId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	referrerClassNameId bigint,
	parentSystemEventId bigint,
	systemEventSetKey bigint,
	type_ integer,
	extraData longtext null,
	primary key (systemEventId, ctCollectionId)
) engine InnoDB;

create table Team (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	teamId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	groupId bigint,
	name varchar(75) null,
	description longtext null,
	lastPublishDate datetime(6) null,
	primary key (teamId, ctCollectionId)
) engine InnoDB;

create table Ticket (
	mvccVersion bigint default 0 not null,
	ticketId bigint not null primary key,
	companyId bigint,
	createDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	key_ varchar(75) null,
	type_ integer,
	extraInfo longtext null,
	expirationDate datetime(6) null
) engine InnoDB;

create table UserNotificationDelivery (
	mvccVersion bigint default 0 not null,
	userNotificationDeliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	portletId varchar(200) null,
	classNameId bigint,
	notificationType integer,
	deliveryType integer,
	deliver tinyint
) engine InnoDB;

create table User_ (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	userId bigint not null,
	companyId bigint,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	defaultUser tinyint,
	contactId bigint,
	password_ varchar(75) null,
	passwordEncrypted tinyint,
	passwordReset tinyint,
	passwordModifiedDate datetime(6) null,
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
	comments longtext null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	jobTitle varchar(100) null,
	loginDate datetime(6) null,
	loginIP varchar(75) null,
	lastLoginDate datetime(6) null,
	lastLoginIP varchar(75) null,
	lastFailedLoginDate datetime(6) null,
	failedLoginAttempts integer,
	lockout tinyint,
	lockoutDate datetime(6) null,
	agreedToTermsOfUse tinyint,
	emailAddressVerified tinyint,
	status integer,
	primary key (userId, ctCollectionId)
) engine InnoDB;

create table UserGroup (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	uuid_ varchar(75) null,
	externalReferenceCode varchar(75) null,
	userGroupId bigint not null,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	parentUserGroupId bigint,
	name varchar(255) null,
	description longtext null,
	addedByLDAPImport tinyint,
	primary key (userGroupId, ctCollectionId)
) engine InnoDB;

create table UserGroupGroupRole (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	userGroupGroupRoleId bigint not null,
	companyId bigint,
	userGroupId bigint,
	groupId bigint,
	roleId bigint,
	primary key (userGroupGroupRoleId, ctCollectionId)
) engine InnoDB;

create table UserGroupRole (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	userGroupRoleId bigint not null,
	companyId bigint,
	userId bigint,
	groupId bigint,
	roleId bigint,
	primary key (userGroupRoleId, ctCollectionId)
) engine InnoDB;

create table UserGroups_Teams (
	companyId bigint not null,
	teamId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (teamId, userGroupId, ctCollectionId)
) engine InnoDB;

create table UserIdMapper (
	mvccVersion bigint default 0 not null,
	userIdMapperId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	description varchar(75) null,
	externalUserId varchar(75) null
) engine InnoDB;

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
	delivered tinyint,
	payload longtext null,
	actionRequired tinyint,
	archived tinyint
) engine InnoDB;

create table Users_Groups (
	companyId bigint not null,
	groupId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (groupId, userId, ctCollectionId)
) engine InnoDB;

create table Users_Orgs (
	companyId bigint not null,
	organizationId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (organizationId, userId, ctCollectionId)
) engine InnoDB;

create table Users_Roles (
	companyId bigint not null,
	roleId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (roleId, userId, ctCollectionId)
) engine InnoDB;

create table Users_Teams (
	companyId bigint not null,
	teamId bigint not null,
	userId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (teamId, userId, ctCollectionId)
) engine InnoDB;

create table Users_UserGroups (
	companyId bigint not null,
	userId bigint not null,
	userGroupId bigint not null,
	ctCollectionId bigint default 0 not null,
	ctChangeType tinyint,
	primary key (userId, userGroupId, ctCollectionId)
) engine InnoDB;

create table UserTracker (
	mvccVersion bigint default 0 not null,
	userTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	modifiedDate datetime(6) null,
	sessionId varchar(200) null,
	remoteAddr varchar(75) null,
	remoteHost varchar(75) null,
	userAgent varchar(200) null
) engine InnoDB;

create table UserTrackerPath (
	mvccVersion bigint default 0 not null,
	userTrackerPathId bigint not null primary key,
	companyId bigint,
	userTrackerId bigint,
	path_ longtext null,
	pathDate datetime(6) null
) engine InnoDB;

create table VirtualHost (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	virtualHostId bigint not null,
	companyId bigint,
	layoutSetId bigint,
	hostname varchar(200) null,
	defaultVirtualHost tinyint,
	languageId varchar(75) null,
	primary key (virtualHostId, ctCollectionId)
) engine InnoDB;

create table WebDAVProps (
	mvccVersion bigint default 0 not null,
	webDavPropsId bigint not null primary key,
	companyId bigint,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	props longtext null
) engine InnoDB;

create table Website (
	mvccVersion bigint default 0 not null,
	uuid_ varchar(75) null,
	websiteId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	url longtext null,
	typeId bigint,
	primary_ tinyint,
	lastPublishDate datetime(6) null
) engine InnoDB;

create table WorkflowDefinitionLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	workflowDefinitionLinkId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	typePK bigint,
	workflowDefinitionName varchar(75) null,
	workflowDefinitionVersion integer,
	primary key (workflowDefinitionLinkId, ctCollectionId)
) engine InnoDB;

create table WorkflowInstanceLink (
	mvccVersion bigint default 0 not null,
	ctCollectionId bigint default 0 not null,
	workflowInstanceLinkId bigint not null,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime(6) null,
	modifiedDate datetime(6) null,
	classNameId bigint,
	classPK bigint,
	workflowInstanceId bigint,
	primary key (workflowInstanceLinkId, ctCollectionId)
) engine InnoDB;
