drop database if exists lportal;
create database lportal character set utf8;
use lportal;

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


##
## List types for account entries
##

insert into ListType (listTypeId, name, type_) values (14000, 'billing', 'com.liferay.account.model.AccountEntry.address');
insert into ListType (listTypeId, name, type_) values (14001, 'billing-and-shipping', 'com.liferay.account.model.AccountEntry.address');
insert into ListType (listTypeId, name, type_) values (14002, 'shipping', 'com.liferay.account.model.AccountEntry.address');

##
## List types for addresses
##

insert into ListType (listTypeId, name, type_) values (13000, 'phone-number', 'com.liferay.portal.kernel.model.Address.phone');

##
## List types for companies
##

insert into ListType (listTypeId, name, type_) values (10000, 'billing', 'com.liferay.portal.kernel.model.Company.address');
insert into ListType (listTypeId, name, type_) values (10001, 'other', 'com.liferay.portal.kernel.model.Company.address');
insert into ListType (listTypeId, name, type_) values (10002, 'p-o-box', 'com.liferay.portal.kernel.model.Company.address');
insert into ListType (listTypeId, name, type_) values (10003, 'shipping', 'com.liferay.portal.kernel.model.Company.address');

insert into ListType (listTypeId, name, type_) values (10004, 'email-address', 'com.liferay.portal.kernel.model.Company.emailAddress');
insert into ListType (listTypeId, name, type_) values (10005, 'email-address-2', 'com.liferay.portal.kernel.model.Company.emailAddress');
insert into ListType (listTypeId, name, type_) values (10006, 'email-address-3', 'com.liferay.portal.kernel.model.Company.emailAddress');

insert into ListType (listTypeId, name, type_) values (10007, 'fax', 'com.liferay.portal.kernel.model.Company.phone');
insert into ListType (listTypeId, name, type_) values (10008, 'local', 'com.liferay.portal.kernel.model.Company.phone');
insert into ListType (listTypeId, name, type_) values (10009, 'other', 'com.liferay.portal.kernel.model.Company.phone');
insert into ListType (listTypeId, name, type_) values (10010, 'toll-free', 'com.liferay.portal.kernel.model.Company.phone');
insert into ListType (listTypeId, name, type_) values (10011, 'tty', 'com.liferay.portal.kernel.model.Company.phone');

insert into ListType (listTypeId, name, type_) values (10012, 'intranet', 'com.liferay.portal.kernel.model.Company.website');
insert into ListType (listTypeId, name, type_) values (10013, 'public', 'com.liferay.portal.kernel.model.Company.website');

##
## List types for contacts
##

insert into ListType (listTypeId, name, type_) values (11000, 'business', 'com.liferay.portal.kernel.model.Contact.address');
insert into ListType (listTypeId, name, type_) values (11001, 'other', 'com.liferay.portal.kernel.model.Contact.address');
insert into ListType (listTypeId, name, type_) values (11002, 'personal', 'com.liferay.portal.kernel.model.Contact.address');

insert into ListType (listTypeId, name, type_) values (11003, 'email-address', 'com.liferay.portal.kernel.model.Contact.emailAddress');
insert into ListType (listTypeId, name, type_) values (11004, 'email-address-2', 'com.liferay.portal.kernel.model.Contact.emailAddress');
insert into ListType (listTypeId, name, type_) values (11005, 'email-address-3', 'com.liferay.portal.kernel.model.Contact.emailAddress');

insert into ListType (listTypeId, name, type_) values (11006, 'business', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11007, 'business-fax', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11008, 'mobile-phone', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11009, 'other', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11010, 'pager', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11011, 'personal', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11012, 'personal-fax', 'com.liferay.portal.kernel.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11013, 'tty', 'com.liferay.portal.kernel.model.Contact.phone');

insert into ListType (listTypeId, name, type_) values (11014, 'dr', 'com.liferay.portal.kernel.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11015, 'mr', 'com.liferay.portal.kernel.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11016, 'mrs', 'com.liferay.portal.kernel.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11017, 'ms', 'com.liferay.portal.kernel.model.Contact.prefix');

insert into ListType (listTypeId, name, type_) values (11020, 'ii', 'com.liferay.portal.kernel.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11021, 'iii', 'com.liferay.portal.kernel.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11022, 'iv', 'com.liferay.portal.kernel.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11023, 'jr', 'com.liferay.portal.kernel.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11024, 'phd', 'com.liferay.portal.kernel.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11025, 'sr', 'com.liferay.portal.kernel.model.Contact.suffix');

insert into ListType (listTypeId, name, type_) values (11026, 'blog', 'com.liferay.portal.kernel.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11027, 'business', 'com.liferay.portal.kernel.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11028, 'other', 'com.liferay.portal.kernel.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11029, 'personal', 'com.liferay.portal.kernel.model.Contact.website');

##
## List types for organizations
##

insert into ListType (listTypeId, name, type_) values (12000, 'billing', 'com.liferay.portal.kernel.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12001, 'other', 'com.liferay.portal.kernel.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12002, 'p-o-box', 'com.liferay.portal.kernel.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12003, 'shipping', 'com.liferay.portal.kernel.model.Organization.address');

insert into ListType (listTypeId, name, type_) values (12004, 'email-address', 'com.liferay.portal.kernel.model.Organization.emailAddress');
insert into ListType (listTypeId, name, type_) values (12005, 'email-address-2', 'com.liferay.portal.kernel.model.Organization.emailAddress');
insert into ListType (listTypeId, name, type_) values (12006, 'email-address-3', 'com.liferay.portal.kernel.model.Organization.emailAddress');

insert into ListType (listTypeId, name, type_) values (12007, 'fax', 'com.liferay.portal.kernel.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12008, 'local', 'com.liferay.portal.kernel.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12009, 'other', 'com.liferay.portal.kernel.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12010, 'toll-free', 'com.liferay.portal.kernel.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12011, 'tty', 'com.liferay.portal.kernel.model.Organization.phone');

insert into ListType (listTypeId, name, type_) values (12012, 'administrative', 'com.liferay.portal.kernel.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12013, 'contracts', 'com.liferay.portal.kernel.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12014, 'donation', 'com.liferay.portal.kernel.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12015, 'retail', 'com.liferay.portal.kernel.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12016, 'training', 'com.liferay.portal.kernel.model.Organization.service');

insert into ListType (listTypeId, name, type_) values (12017, 'full-member', 'com.liferay.portal.kernel.model.Organization.status');
insert into ListType (listTypeId, name, type_) values (12018, 'provisional-member', 'com.liferay.portal.kernel.model.Organization.status');

insert into ListType (listTypeId, name, type_) values (12019, 'intranet', 'com.liferay.portal.kernel.model.Organization.website');
insert into ListType (listTypeId, name, type_) values (12020, 'public', 'com.liferay.portal.kernel.model.Organization.website');


insert into Counter (name, currentId) values ('com.liferay.counter.kernel.model.Counter', 20000);



commit;


create index IX_923BD178 on Address (companyId, classNameId, classPK, mailing);
create index IX_9226DBB4 on Address (companyId, classNameId, classPK, primary_);
create index IX_58D4EA4C on Address (companyId, classNameId, classPK, typeId);
create index IX_CBAD282F on Address (companyId, externalReferenceCode);
create index IX_5A2093E7 on Address (countryId);
create index IX_C8E3E87D on Address (regionId);
create index IX_5BC8B0D4 on Address (userId);
create index IX_8FCB620E on Address (uuid_, companyId);

create index IX_37B0A8A2 on AnnouncementsDelivery (companyId);
create unique index IX_BA4413D5 on AnnouncementsDelivery (userId, type_);

create index IX_14F06A6B on AnnouncementsEntry (classNameId, classPK, alert);
create index IX_459BE01B on AnnouncementsEntry (companyId, classNameId, classPK, alert);
create index IX_D49C2E66 on AnnouncementsEntry (userId);
create index IX_F2949120 on AnnouncementsEntry (uuid_, companyId);

create index IX_EF1F022A on AnnouncementsFlag (companyId);
create index IX_9C7EB9F on AnnouncementsFlag (entryId);
create unique index IX_4539A99C on AnnouncementsFlag (userId, entryId, value);

create index IX_AE8DFA7 on AssetCategory (companyId, externalReferenceCode, ctCollectionId);
create index IX_1757FA92 on AssetCategory (ctCollectionId);
create index IX_62DC0D54 on AssetCategory (groupId, ctCollectionId);
create index IX_3E49A228 on AssetCategory (groupId, name, vocabularyId, ctCollectionId);
create index IX_5159C90B on AssetCategory (groupId, parentCategoryId, ctCollectionId);
create index IX_852EA801 on AssetCategory (groupId, parentCategoryId, name, vocabularyId);
create index IX_51264AA0 on AssetCategory (groupId, parentCategoryId, vocabularyId, ctCollectionId);
create index IX_7EF2DB29 on AssetCategory (groupId, vocabularyId, ctCollectionId);
create index IX_8F988466 on AssetCategory (name, vocabularyId, ctCollectionId);
create index IX_88D822C9 on AssetCategory (parentCategoryId, ctCollectionId);
create index IX_83C2D848 on AssetCategory (parentCategoryId, name, ctCollectionId);
create unique index IX_DC516B1D on AssetCategory (parentCategoryId, name, vocabularyId, ctCollectionId);
create index IX_8CEDBFDE on AssetCategory (parentCategoryId, vocabularyId, ctCollectionId);
create index IX_59B2EF86 on AssetCategory (uuid_, companyId, ctCollectionId);
create index IX_A9CC915E on AssetCategory (uuid_, ctCollectionId);
create unique index IX_5B65C08 on AssetCategory (uuid_, groupId, ctCollectionId);
create index IX_24AFC3E7 on AssetCategory (vocabularyId, ctCollectionId);

create index IX_112337B8 on AssetEntries_AssetTags (companyId);
create index IX_B2A61B55 on AssetEntries_AssetTags (tagId);

create unique index IX_7BF8337B on AssetEntry (classNameId, classPK, ctCollectionId);
create index IX_25F682BE on AssetEntry (companyId, ctCollectionId);
create index IX_E504D126 on AssetEntry (ctCollectionId);
create index IX_8839F457 on AssetEntry (expirationDate, ctCollectionId);
create index IX_B516ADB0 on AssetEntry (groupId, classNameId, publishDate, expirationDate, ctCollectionId);
create index IX_A62EE954 on AssetEntry (groupId, classNameId, visible, ctCollectionId);
create index IX_683ADC7F on AssetEntry (groupId, classUuid, ctCollectionId);
create index IX_D5B55D40 on AssetEntry (groupId, ctCollectionId);
create index IX_5B55565F on AssetEntry (layoutUuid, ctCollectionId);
create index IX_788964E3 on AssetEntry (publishDate, ctCollectionId);
create index IX_5B6AC3B8 on AssetEntry (visible, ctCollectionId);

create index IX_5D969E8E on AssetLink (ctCollectionId);
create index IX_9BB95D26 on AssetLink (entryId1, ctCollectionId);
create index IX_97B1F7F on AssetLink (entryId1, entryId2, ctCollectionId);
create unique index IX_7FC555F2 on AssetLink (entryId1, entryId2, type_, ctCollectionId);
create index IX_F75CBE6B on AssetLink (entryId1, type_, ctCollectionId);
create index IX_6963BEE7 on AssetLink (entryId2, ctCollectionId);
create index IX_F936118A on AssetLink (entryId2, type_, ctCollectionId);

create index IX_E534924E on AssetTag (ctCollectionId);
create index IX_24286918 on AssetTag (groupId, ctCollectionId);
create unique index IX_AA52E757 on AssetTag (groupId, name, ctCollectionId);
create index IX_7A6CD00D on AssetTag (name, ctCollectionId);
create index IX_71579042 on AssetTag (uuid_, companyId, ctCollectionId);
create index IX_E7450E22 on AssetTag (uuid_, ctCollectionId);
create unique index IX_A43FBC4 on AssetTag (uuid_, groupId, ctCollectionId);

create index IX_F75DCEEA on AssetVocabulary (companyId, ctCollectionId);
create index IX_6496D38F on AssetVocabulary (companyId, externalReferenceCode, ctCollectionId);
create index IX_49B3687A on AssetVocabulary (ctCollectionId);
create index IX_4E99C46C on AssetVocabulary (groupId, ctCollectionId);
create index IX_9181CBCD on AssetVocabulary (groupId, externalReferenceCode, ctCollectionId);
create unique index IX_AE9F73AB on AssetVocabulary (groupId, name, ctCollectionId);
create index IX_2C944C4C on AssetVocabulary (groupId, visibilityType, ctCollectionId);
create index IX_B955B36E on AssetVocabulary (uuid_, companyId, ctCollectionId);
create index IX_2F3D2E76 on AssetVocabulary (uuid_, ctCollectionId);
create unique index IX_8F88F9F0 on AssetVocabulary (uuid_, groupId, ctCollectionId);

create unique index IX_E7B95510 on BrowserTracker (userId);

create unique index IX_B27A301F on ClassName_ (value);

create index IX_38EFE3FD on Company (logoId);
create index IX_12566EC2 on Company (mx);
create index IX_8699D9BD on Company (system_);
create unique index IX_EC00543C on Company (webId);

create unique index IX_85C63FD7 on CompanyInfo (companyId);

create index IX_791914FA on Contact_ (classNameId, classPK);
create index IX_66D496A3 on Contact_ (companyId);

create index IX_25D734CD on Country (active_);
create unique index IX_742FFB11 on Country (companyId, a2);
create unique index IX_742FFED2 on Country (companyId, a3);
create index IX_F9CD867E on Country (companyId, active_, billingAllowed);
create index IX_54E98CCD on Country (companyId, active_, shippingAllowed);
create unique index IX_410257AB on Country (companyId, name);
create unique index IX_4B78E87A on Country (companyId, number_);
create index IX_BEAF8B0 on Country (uuid_, companyId);

create unique index IX_518948B3 on CountryLocalization (countryId, languageId);

create index IX_33E8A112 on DLFileEntry (companyId, ctCollectionId);
create index IX_5444C427 on DLFileEntry (companyId, fileEntryTypeId);
create index IX_9B56081C on DLFileEntry (custom1ImageId, ctCollectionId);
create index IX_9D2F5B3B on DLFileEntry (custom2ImageId, ctCollectionId);
create index IX_C0A6F645 on DLFileEntry (fileEntryTypeId, ctCollectionId);
create index IX_F951AC2E on DLFileEntry (folderId, name, ctCollectionId);
create index IX_60830094 on DLFileEntry (groupId, ctCollectionId);
create index IX_273362A5 on DLFileEntry (groupId, externalReferenceCode, ctCollectionId);
create index IX_BAF654E5 on DLFileEntry (groupId, fileEntryTypeId);
create index IX_95A2D1F1 on DLFileEntry (groupId, folderId, ctCollectionId);
create index IX_D8883586 on DLFileEntry (groupId, folderId, fileEntryTypeId, ctCollectionId);
create unique index IX_A256938C on DLFileEntry (groupId, folderId, fileName, ctCollectionId);
create unique index IX_F7878970 on DLFileEntry (groupId, folderId, name, ctCollectionId);
create unique index IX_EAAB273 on DLFileEntry (groupId, folderId, title, ctCollectionId);
create index IX_3B20ECE on DLFileEntry (groupId, userId, ctCollectionId);
create index IX_87A8DFAB on DLFileEntry (groupId, userId, folderId, ctCollectionId);
create index IX_863591A1 on DLFileEntry (largeImageId, ctCollectionId);
create index IX_72175754 on DLFileEntry (mimeType, ctCollectionId);
create index IX_6EC7490B on DLFileEntry (repositoryId, ctCollectionId);
create index IX_277C31A8 on DLFileEntry (repositoryId, folderId, ctCollectionId);
create index IX_A8521555 on DLFileEntry (smallImageId, ctCollectionId);
create index IX_854E0F17 on DLFileEntry (smallImageId, largeImageId, custom1ImageId, custom2ImageId, ctCollectionId);
create index IX_1F89A446 on DLFileEntry (uuid_, companyId, ctCollectionId);
create index IX_CF17549E on DLFileEntry (uuid_, ctCollectionId);
create unique index IX_373340C8 on DLFileEntry (uuid_, groupId, ctCollectionId);

create unique index IX_B9210CAD on DLFileEntryMetadata (DDMStructureId, fileVersionId, ctCollectionId);
create index IX_8D4F58BC on DLFileEntryMetadata (fileEntryId, ctCollectionId);
create index IX_A158EA62 on DLFileEntryMetadata (fileVersionId, ctCollectionId);
create index IX_EABA15 on DLFileEntryMetadata (uuid_, companyId, ctCollectionId);
create index IX_EAA9CA2F on DLFileEntryMetadata (uuid_, ctCollectionId);

create index IX_C0561BFA on DLFileEntryType (groupId, ctCollectionId);
create unique index IX_B6F21286 on DLFileEntryType (groupId, dataDefinitionId, ctCollectionId);
create unique index IX_402227BD on DLFileEntryType (groupId, fileEntryTypeKey, ctCollectionId);
create index IX_F147FBA0 on DLFileEntryType (uuid_, companyId, ctCollectionId);
create index IX_17A61184 on DLFileEntryType (uuid_, ctCollectionId);
create unique index IX_1773A6A2 on DLFileEntryType (uuid_, groupId, ctCollectionId);

create index IX_2E64D9F9 on DLFileEntryTypes_DLFolders (companyId);
create index IX_6E00A2EC on DLFileEntryTypes_DLFolders (folderId);

create index IX_A46E54B6 on DLFileShortcut (companyId, ctCollectionId);
create index IX_80362F9C on DLFileShortcut (companyId, status, ctCollectionId);
create index IX_8A2EF610 on DLFileShortcut (groupId, folderId, active_, ctCollectionId);
create index IX_CFD4D6F6 on DLFileShortcut (groupId, folderId, active_, status, ctCollectionId);
create index IX_869CA195 on DLFileShortcut (groupId, folderId, ctCollectionId);
create index IX_5CAA7254 on DLFileShortcut (toFileEntryId, ctCollectionId);
create index IX_FE055022 on DLFileShortcut (uuid_, companyId, ctCollectionId);
create index IX_21B07A42 on DLFileShortcut (uuid_, ctCollectionId);
create unique index IX_DD2033A4 on DLFileShortcut (uuid_, groupId, ctCollectionId);

create index IX_97782D6C on DLFileVersion (companyId, ctCollectionId);
create index IX_808EF252 on DLFileVersion (companyId, status, ctCollectionId);
create index IX_759EF1C5 on DLFileVersion (fileEntryId, ctCollectionId);
create index IX_C97C4DAB on DLFileVersion (fileEntryId, status, ctCollectionId);
create unique index IX_10E504DF on DLFileVersion (fileEntryId, version, ctCollectionId);
create index IX_3A12DA31 on DLFileVersion (groupId, folderId, status, ctCollectionId);
create index IX_DCA2C64B on DLFileVersion (groupId, folderId, title, version, ctCollectionId);
create index IX_9E97D7BA on DLFileVersion (mimeType, ctCollectionId);
create index IX_16CE5EAC on DLFileVersion (uuid_, companyId, ctCollectionId);
create index IX_48BF1DF8 on DLFileVersion (uuid_, ctCollectionId);
create unique index IX_350F5CAE on DLFileVersion (uuid_, groupId, ctCollectionId);

create index IX_67A46FAA on DLFolder (companyId, ctCollectionId);
create index IX_F1EC1690 on DLFolder (companyId, status, ctCollectionId);
create index IX_9D91952C on DLFolder (groupId, ctCollectionId);
create index IX_4B18B17E on DLFolder (groupId, mountPoint, parentFolderId, ctCollectionId);
create index IX_45D93323 on DLFolder (groupId, mountPoint, parentFolderId, hidden_, ctCollectionId);
create index IX_91065109 on DLFolder (groupId, mountPoint, parentFolderId, hidden_, status, ctCollectionId);
create index IX_CF68C0D3 on DLFolder (groupId, parentFolderId, ctCollectionId);
create index IX_7663654 on DLFolder (groupId, parentFolderId, hidden_, status, ctCollectionId);
create unique index IX_C7E346D2 on DLFolder (groupId, parentFolderId, name, ctCollectionId);
create index IX_4642F2E0 on DLFolder (parentFolderId, name, ctCollectionId);
create index IX_BB15D373 on DLFolder (repositoryId, ctCollectionId);
create index IX_F344479E on DLFolder (repositoryId, mountPoint, ctCollectionId);
create index IX_E7CD911A on DLFolder (repositoryId, parentFolderId, ctCollectionId);
create index IX_333CBAAE on DLFolder (uuid_, companyId, ctCollectionId);
create index IX_B7722F36 on DLFolder (uuid_, ctCollectionId);
create unique index IX_AA08D130 on DLFolder (uuid_, groupId, ctCollectionId);

create index IX_2A2CB130 on EmailAddress (companyId, classNameId, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);
create index IX_F74AB912 on EmailAddress (uuid_, companyId);

create index IX_8B26D246 on ExpandoColumn (tableId, ctCollectionId);
create unique index IX_4A7D3605 on ExpandoColumn (tableId, name, ctCollectionId);

create index IX_BCC0D776 on ExpandoRow (classPK, ctCollectionId);
create unique index IX_488E0C53 on ExpandoRow (tableId, classPK, ctCollectionId);
create index IX_5C47920C on ExpandoRow (tableId, ctCollectionId);

create index IX_A905B6E3 on ExpandoTable (companyId, classNameId, ctCollectionId);
create unique index IX_87D370E2 on ExpandoTable (companyId, classNameId, name, ctCollectionId);

create index IX_FF8FB775 on ExpandoValue (classNameId, classPK, ctCollectionId);
create index IX_B5A9F1E5 on ExpandoValue (columnId, ctCollectionId);
create unique index IX_E6D98E43 on ExpandoValue (columnId, rowId_, ctCollectionId);
create index IX_FC7A3DFE on ExpandoValue (rowId_, ctCollectionId);
create index IX_3D37FDAA on ExpandoValue (tableId, classPK, ctCollectionId);
create unique index IX_D8C72C45 on ExpandoValue (tableId, columnId, classPK, ctCollectionId);
create index IX_8AF759DA on ExpandoValue (tableId, columnId, ctCollectionId);
create index IX_EEA372D5 on ExpandoValue (tableId, ctCollectionId);
create index IX_4E7B1F33 on ExpandoValue (tableId, rowId_, ctCollectionId);

create index IX_1827A2E5 on ExportImportConfiguration (companyId);
create index IX_38FA468D on ExportImportConfiguration (groupId, status);
create index IX_47CC6234 on ExportImportConfiguration (groupId, type_, status);

create index IX_EB3A63D9 on Group_ (classNameId, classPK, ctCollectionId);
create index IX_BD3CB13A on Group_ (classNameId, groupId, companyId, parentGroupId);
create index IX_8B5402E5 on Group_ (companyId, active_, ctCollectionId);
create unique index IX_504CABF5 on Group_ (companyId, classNameId, classPK, ctCollectionId);
create index IX_2442742A on Group_ (companyId, classNameId, ctCollectionId);
create index IX_B7EBDBB2 on Group_ (companyId, classNameId, parentGroupId, ctCollectionId);
create index IX_A67A0AA5 on Group_ (companyId, classNameId, site, ctCollectionId);
create index IX_286EE120 on Group_ (companyId, ctCollectionId);
create unique index IX_9A7D6AD0 on Group_ (companyId, friendlyURL, ctCollectionId);
create unique index IX_BE219CF4 on Group_ (companyId, groupKey, ctCollectionId);
create index IX_A20523FC on Group_ (companyId, parentGroupId, ctCollectionId);
create index IX_121A14F7 on Group_ (companyId, parentGroupId, site, ctCollectionId);
create index IX_162053E9 on Group_ (companyId, parentGroupId, site, inheritContent, ctCollectionId);
create index IX_4108074A on Group_ (companyId, site, active_, ctCollectionId);
create index IX_CFE2671B on Group_ (companyId, site, ctCollectionId);
create index IX_8060F096 on Group_ (liveGroupId, ctCollectionId);
create index IX_5263ACD8 on Group_ (type_, active_, ctCollectionId);
create index IX_21CBD878 on Group_ (uuid_, companyId, ctCollectionId);
create index IX_BFEBCBAC on Group_ (uuid_, ctCollectionId);

create index IX_8BFD4548 on Groups_Orgs (companyId);
create index IX_6BBB7682 on Groups_Orgs (organizationId);

create index IX_557D8550 on Groups_Roles (companyId);
create index IX_3103EF3D on Groups_Roles (roleId);

create index IX_676FC818 on Groups_UserGroups (companyId);
create index IX_3B69160F on Groups_UserGroups (userGroupId);

create index IX_9720F6AB on Image (size_, ctCollectionId);

create index IX_31B45343 on Layout (classNameId, classPK, ctCollectionId);
create index IX_4B906FF6 on Layout (companyId, ctCollectionId);
create index IX_8F868C29 on Layout (companyId, layoutPrototypeUuid, ctCollectionId);
create index IX_FD5AF6EE on Layout (ctCollectionId);
create index IX_34D93878 on Layout (groupId, ctCollectionId);
create index IX_12770E8F on Layout (groupId, masterLayoutPlid, ctCollectionId);
create index IX_7BFE8B01 on Layout (groupId, privateLayout, ctCollectionId);
create unique index IX_B556968F on Layout (groupId, privateLayout, friendlyURL, ctCollectionId);
create unique index IX_CF5120DA on Layout (groupId, privateLayout, layoutId, ctCollectionId);
create index IX_52D89564 on Layout (groupId, privateLayout, parentLayoutId, ctCollectionId);
create index IX_1E4451FD on Layout (groupId, privateLayout, parentLayoutId, hidden_, ctCollectionId);
create index IX_989E917C on Layout (groupId, privateLayout, parentLayoutId, priority, ctCollectionId);
create index IX_66125D58 on Layout (groupId, privateLayout, parentLayoutId, system_, ctCollectionId);
create index IX_18D0C537 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid, ctCollectionId);
create index IX_A73CEAE7 on Layout (groupId, privateLayout, status, ctCollectionId);
create index IX_A1FC5430 on Layout (groupId, privateLayout, type_, ctCollectionId);
create index IX_94E0E2D9 on Layout (groupId, type_, ctCollectionId);
create index IX_E7B06BDB on Layout (iconImageId, ctCollectionId);
create index IX_11389031 on Layout (layoutPrototypeUuid, ctCollectionId);
create index IX_7F60B703 on Layout (parentPlid, ctCollectionId);
create index IX_C95F601E on Layout (privateLayout, iconImageId, ctCollectionId);
create index IX_ED8D4D2A on Layout (sourcePrototypeLayoutUuid, ctCollectionId);
create index IX_24AA0CE2 on Layout (uuid_, companyId, ctCollectionId);
create index IX_5AA23582 on Layout (uuid_, ctCollectionId);
create unique index IX_52D84D95 on Layout (uuid_, groupId, privateLayout, ctCollectionId);

create index IX_A705FF94 on LayoutBranch (layoutSetBranchId, plid, master);
create unique index IX_FD57097D on LayoutBranch (layoutSetBranchId, plid, name);

create index IX_1C55E26 on LayoutFriendlyURL (companyId, ctCollectionId);
create index IX_7ED3F2A8 on LayoutFriendlyURL (groupId, ctCollectionId);
create index IX_6F5128BF on LayoutFriendlyURL (groupId, privateLayout, friendlyURL, ctCollectionId);
create unique index IX_E73BB186 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL, languageId, ctCollectionId);
create index IX_EF247709 on LayoutFriendlyURL (plid, ctCollectionId);
create index IX_CB1E7787 on LayoutFriendlyURL (plid, friendlyURL, ctCollectionId);
create unique index IX_2069E0D0 on LayoutFriendlyURL (plid, languageId, ctCollectionId);
create index IX_9DE5C8B2 on LayoutFriendlyURL (uuid_, companyId, ctCollectionId);
create index IX_C765BBB2 on LayoutFriendlyURL (uuid_, ctCollectionId);
create unique index IX_58E59034 on LayoutFriendlyURL (uuid_, groupId, ctCollectionId);

create index IX_557A639F on LayoutPrototype (companyId, active_);
create index IX_63ED2532 on LayoutPrototype (uuid_, companyId);

create index IX_43E8286A on LayoutRevision (head, plid);
create index IX_E10AC39 on LayoutRevision (layoutSetBranchId, head, plid);
create index IX_9EC9F954 on LayoutRevision (layoutSetBranchId, head, status);
create index IX_38C5DF14 on LayoutRevision (layoutSetBranchId, layoutBranchId, head, plid);
create index IX_13984800 on LayoutRevision (layoutSetBranchId, layoutBranchId, plid);
create index IX_4A84AF43 on LayoutRevision (layoutSetBranchId, parentLayoutRevisionId, plid);
create index IX_70DA9ECB on LayoutRevision (layoutSetBranchId, plid, status);
create index IX_7FFAE700 on LayoutRevision (layoutSetBranchId, status);
create index IX_8EC3D2BC on LayoutRevision (plid, status);
create index IX_421223B1 on LayoutRevision (status);

create index IX_20615181 on LayoutSet (companyId, layoutSetPrototypeUuid, ctCollectionId);
create index IX_5B990A4A on LayoutSet (groupId, ctCollectionId);
create unique index IX_3F2A9AEF on LayoutSet (groupId, privateLayout, ctCollectionId);
create index IX_55443115 on LayoutSet (layoutSetPrototypeUuid, ctCollectionId);
create index IX_A6EE9D37 on LayoutSet (privateLayout, logoId, ctCollectionId);

create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master);
create unique index IX_5FF18552 on LayoutSetBranch (groupId, privateLayout, name);

create index IX_9178FC71 on LayoutSetPrototype (companyId, active_);
create index IX_D9FFCA84 on LayoutSetPrototype (uuid_, companyId);

create unique index IX_77729718 on ListType (name, type_);
create index IX_2932DD37 on ListType (type_);

create index IX_C28C72EC on MembershipRequest (groupId, statusId);
create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId);
create index IX_66D70879 on MembershipRequest (userId);

create index IX_4A527DD3 on OrgGroupRole (groupId);
create index IX_AB044D1C on OrgGroupRole (roleId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create index IX_2C1E7914 on Organization_ (companyId, ctCollectionId);
create index IX_38F85A25 on Organization_ (companyId, externalReferenceCode, ctCollectionId);
create unique index IX_F1E40A53 on Organization_ (companyId, name, ctCollectionId);
create index IX_7A5E9780 on Organization_ (companyId, parentOrganizationId, ctCollectionId);
create index IX_DC36A7BF on Organization_ (companyId, parentOrganizationId, name, ctCollectionId);
create index IX_3F532604 on Organization_ (uuid_, companyId, ctCollectionId);
create index IX_206D7DA0 on Organization_ (uuid_, ctCollectionId);

create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy);
create unique index IX_3FBFA9F4 on PasswordPolicy (companyId, name);
create index IX_E4D7EF87 on PasswordPolicy (uuid_, companyId);

create unique index IX_C3A17327 on PasswordPolicyRel (classNameId, classPK);
create index IX_CD25266E on PasswordPolicyRel (passwordPolicyId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_);
create index IX_F202B9CE on Phone (userId);
create index IX_B271FA88 on Phone (uuid_, companyId);

create unique index IX_7171B2E8 on PluginSetting (companyId, pluginId, pluginType);

create unique index IX_A6DD0ECF on PortalPreferenceValue (portalPreferencesId, index_, key_, namespace);
create index IX_9FF9CC4E on PortalPreferenceValue (portalPreferencesId, key_, namespace, smallValue);
create index IX_4C7A38C4 on PortalPreferenceValue (portalPreferencesId, namespace);

create index IX_D1F795F1 on PortalPreferences (ownerId, ownerType);

create unique index IX_12B5E51D on Portlet (companyId, portletId);

create index IX_96BDD537 on PortletItem (groupId, classNameId);
create index IX_D699243F on PortletItem (groupId, name, portletId, classNameId);
create index IX_E922D6C0 on PortletItem (groupId, portletId, classNameId);

create index IX_4462FCD on PortletPreferenceValue (portletPreferencesId, ctCollectionId);
create unique index IX_AD38E28D on PortletPreferenceValue (portletPreferencesId, index_, name, ctCollectionId);
create index IX_B94C124C on PortletPreferenceValue (portletPreferencesId, name, ctCollectionId);
create index IX_2E0FE9EA on PortletPreferenceValue (portletPreferencesId, name, smallValue, ctCollectionId);

create index IX_F0B8A3A0 on PortletPreferences (companyId, ownerId, ownerType, portletId, ctCollectionId);
create index IX_31DA3CB8 on PortletPreferences (ownerId, ctCollectionId);
create index IX_451D78CC on PortletPreferences (ownerId, ownerType, plid, ctCollectionId);
create unique index IX_CB778855 on PortletPreferences (ownerId, ownerType, plid, portletId, ctCollectionId);
create index IX_258CCF40 on PortletPreferences (ownerId, ownerType, portletId, ctCollectionId);
create index IX_D48717FF on PortletPreferences (ownerType, plid, portletId, ctCollectionId);
create index IX_D6C3E66A on PortletPreferences (ownerType, portletId, ctCollectionId);
create index IX_C77A74AD on PortletPreferences (plid, ctCollectionId);
create index IX_67E205D4 on PortletPreferences (plid, portletId, ctCollectionId);
create index IX_8D0717FF on PortletPreferences (portletId, ctCollectionId);

create index IX_FD0395B5 on RatingsEntry (classNameId, classPK, ctCollectionId);
create index IX_66592BE9 on RatingsEntry (classNameId, classPK, score, ctCollectionId);
create unique index IX_B938D06F on RatingsEntry (userId, classNameId, classPK, ctCollectionId);
create index IX_CBD05854 on RatingsEntry (uuid_, companyId, ctCollectionId);
create index IX_A4389D50 on RatingsEntry (uuid_, ctCollectionId);

create unique index IX_C286E0E2 on RatingsStats (classNameId, classPK, ctCollectionId);
create index IX_5EC6007D on RatingsStats (classNameId, createDate);
create index IX_11A5584A on RatingsStats (classNameId, modifiedDate);

create index IX_B91F79BD on RecentLayoutBranch (groupId);
create index IX_351E86E8 on RecentLayoutBranch (layoutBranchId);
create unique index IX_C27D6369 on RecentLayoutBranch (userId, layoutSetBranchId, plid);

create index IX_8D8A2724 on RecentLayoutRevision (groupId);
create index IX_DA0788DA on RecentLayoutRevision (layoutRevisionId);
create unique index IX_4C600BD0 on RecentLayoutRevision (userId, layoutSetBranchId, plid);

create index IX_711995A5 on RecentLayoutSetBranch (groupId);
create index IX_23FF0700 on RecentLayoutSetBranch (layoutSetBranchId);
create unique index IX_4654D204 on RecentLayoutSetBranch (userId, layoutSetId);

create index IX_2D9A426F on Region (active_);
create index IX_11FB3E42 on Region (countryId, active_);
create unique index IX_A2635F5C on Region (countryId, regionCode);
create index IX_60C0214E on Region (uuid_, companyId);

create unique index IX_A149763D on RegionLocalization (regionId, languageId);

create unique index IX_8BD6BCA7 on Release_ (servletContextName);

create unique index IX_60C8634C on Repository (groupId, name, portletId);
create index IX_F543EA4 on Repository (uuid_, companyId);
create unique index IX_11641E26 on Repository (uuid_, groupId);

create unique index IX_9BDCF489 on RepositoryEntry (repositoryId, mappedId);
create index IX_D3B9AF62 on RepositoryEntry (uuid_, companyId);
create unique index IX_354AA664 on RepositoryEntry (uuid_, groupId);

create unique index IX_EDB9986E on ResourceAction (name, actionId);

create index IX_9A838EC7 on ResourcePermission (companyId, name, scope, primKey, ctCollectionId);
create unique index IX_A9FF4B2C on ResourcePermission (companyId, name, scope, primKey, roleId, ctCollectionId);
create index IX_B60B5751 on ResourcePermission (companyId, name, scope, primKeyId, roleId, viewActionId, ctCollectionId);
create index IX_829B8423 on ResourcePermission (companyId, name, scope, roleId, ctCollectionId);
create index IX_490017A2 on ResourcePermission (companyId, primKey, ctCollectionId);
create index IX_2FABAAC8 on ResourcePermission (companyId, scope, primKey, ctCollectionId);
create index IX_FABE6981 on ResourcePermission (ctCollectionId);
create index IX_6AD73500 on ResourcePermission (name, ctCollectionId);
create index IX_3078CBE6 on ResourcePermission (roleId, ctCollectionId);
create index IX_F3870DDF on ResourcePermission (scope, ctCollectionId);

create unique index IX_C0F6BCAC on Role_ (companyId, classNameId, classPK, ctCollectionId);
create index IX_4EA65517 on Role_ (companyId, ctCollectionId);
create unique index IX_4CC99816 on Role_ (companyId, name, ctCollectionId);
create index IX_7037255A on Role_ (companyId, type_, ctCollectionId);
create index IX_B482E6EC on Role_ (name, ctCollectionId);
create index IX_18A86359 on Role_ (subtype, ctCollectionId);
create index IX_FFA7B144 on Role_ (type_, ctCollectionId);
create index IX_D8DA3062 on Role_ (type_, subtype, ctCollectionId);
create index IX_411F50A1 on Role_ (uuid_, companyId, ctCollectionId);
create index IX_EFF1D323 on Role_ (uuid_, ctCollectionId);

create unique index IX_4F0315B8 on ServiceComponent (buildNamespace, buildNumber);

create index IX_9E7AC81A on SocialActivity (activitySetId, ctCollectionId);
create index IX_AD0B0FB5 on SocialActivity (classNameId, classPK, ctCollectionId);
create index IX_90E6DCFC on SocialActivity (classNameId, classPK, type_, ctCollectionId);
create index IX_5AD306C4 on SocialActivity (companyId, ctCollectionId);
create index IX_A9CF2AC6 on SocialActivity (groupId, ctCollectionId);
create index IX_9C9CB625 on SocialActivity (groupId, userId, classNameId, classPK, type_, receiverUserId, ctCollectionId);
create unique index IX_24810327 on SocialActivity (groupId, userId, createDate, classNameId, classPK, type_, receiverUserId, ctCollectionId);
create index IX_A57E31D2 on SocialActivity (mirrorActivityId, classNameId, classPK, ctCollectionId);
create index IX_28C22ABD on SocialActivity (mirrorActivityId, ctCollectionId);
create index IX_E948429 on SocialActivity (receiverUserId, ctCollectionId);
create index IX_96BE971A on SocialActivity (userId, ctCollectionId);

create index IX_5DE7864F on SocialActivityAchievement (groupId, ctCollectionId);
create index IX_ADBE078D on SocialActivityAchievement (groupId, firstInGroup, ctCollectionId);
create index IX_CAFEFF4E on SocialActivityAchievement (groupId, name, ctCollectionId);
create index IX_38BEA989 on SocialActivityAchievement (groupId, userId, ctCollectionId);
create index IX_9F91FD47 on SocialActivityAchievement (groupId, userId, firstInGroup, ctCollectionId);
create unique index IX_5ED94F08 on SocialActivityAchievement (groupId, userId, name, ctCollectionId);

create index IX_BDC6A299 on SocialActivityCounter (classNameId, classPK, ctCollectionId);
create unique index IX_8F6B63C5 on SocialActivityCounter (groupId, classNameId, classPK, name, ownerType, endPeriod, ctCollectionId);
create unique index IX_D520F00C on SocialActivityCounter (groupId, classNameId, classPK, name, ownerType, startPeriod, ctCollectionId);
create index IX_B7252B62 on SocialActivityCounter (groupId, classNameId, classPK, ownerType, ctCollectionId);
create index IX_7ACAB562 on SocialActivityCounter (groupId, ctCollectionId);

create index IX_D800658 on SocialActivityLimit (classNameId, classPK, ctCollectionId);
create index IX_197F2743 on SocialActivityLimit (groupId, ctCollectionId);
create unique index IX_4A636E75 on SocialActivityLimit (groupId, userId, classNameId, classPK, activityType, activityCounterName, ctCollectionId);
create index IX_710E96FD on SocialActivityLimit (userId, ctCollectionId);

create index IX_49DD2872 on SocialActivitySet (classNameId, classPK, type_, ctCollectionId);
create index IX_34A94D3C on SocialActivitySet (groupId, ctCollectionId);
create index IX_EF8C463D on SocialActivitySet (groupId, userId, classNameId, type_, ctCollectionId);
create index IX_F4E22E1B on SocialActivitySet (groupId, userId, type_, ctCollectionId);
create index IX_EF377278 on SocialActivitySet (userId, classNameId, classPK, type_, ctCollectionId);
create index IX_A37B4DE4 on SocialActivitySet (userId, ctCollectionId);

create index IX_35A9252B on SocialActivitySetting (groupId, activityType, ctCollectionId);
create index IX_D0E7F399 on SocialActivitySetting (groupId, classNameId, activityType, ctCollectionId);
create index IX_4FC6CD18 on SocialActivitySetting (groupId, classNameId, activityType, name, ctCollectionId);
create index IX_11AAEF7C on SocialActivitySetting (groupId, classNameId, ctCollectionId);
create index IX_6B06688E on SocialActivitySetting (groupId, ctCollectionId);

create index IX_60BA2F7 on SocialRelation (companyId, ctCollectionId);
create index IX_2CB87B7A on SocialRelation (companyId, type_, ctCollectionId);
create index IX_A29EEF24 on SocialRelation (type_, ctCollectionId);
create index IX_BDCE8C2A on SocialRelation (userId1, ctCollectionId);
create index IX_3A6962E7 on SocialRelation (userId1, type_, ctCollectionId);
create index IX_5A757CEE on SocialRelation (userId1, userId2, ctCollectionId);
create unique index IX_D97ACDA3 on SocialRelation (userId1, userId2, type_, ctCollectionId);
create index IX_8B78EDEB on SocialRelation (userId2, ctCollectionId);
create index IX_3C42B606 on SocialRelation (userId2, type_, ctCollectionId);
create index IX_FDA0A6C1 on SocialRelation (uuid_, companyId, ctCollectionId);
create index IX_92E91103 on SocialRelation (uuid_, ctCollectionId);

create index IX_A776F23B on SocialRequest (classNameId, classPK, ctCollectionId);
create index IX_EB193CE5 on SocialRequest (classNameId, classPK, type_, receiverUserId, status, ctCollectionId);
create index IX_9E3B7BFE on SocialRequest (companyId, ctCollectionId);
create index IX_B626432F on SocialRequest (receiverUserId, ctCollectionId);
create index IX_9E789515 on SocialRequest (receiverUserId, status, ctCollectionId);
create unique index IX_EF4BB505 on SocialRequest (userId, classNameId, classPK, type_, receiverUserId, ctCollectionId);
create index IX_D54872A2 on SocialRequest (userId, classNameId, classPK, type_, status, ctCollectionId);
create index IX_8CAABC20 on SocialRequest (userId, ctCollectionId);
create index IX_59718D06 on SocialRequest (userId, status, ctCollectionId);
create index IX_58C2E7DA on SocialRequest (uuid_, companyId, ctCollectionId);
create index IX_684858A on SocialRequest (uuid_, ctCollectionId);
create unique index IX_350595C on SocialRequest (uuid_, groupId, ctCollectionId);

create index IX_46E892C on SystemEvent (groupId, classNameId, classPK, ctCollectionId);
create index IX_6C051FA5 on SystemEvent (groupId, classNameId, classPK, type_, ctCollectionId);
create index IX_E9FA8197 on SystemEvent (groupId, ctCollectionId);
create index IX_C009825D on SystemEvent (groupId, systemEventSetKey, ctCollectionId);

create index IX_713531A3 on Team (companyId, ctCollectionId);
create index IX_622C8165 on Team (groupId, ctCollectionId);
create unique index IX_D424D1E4 on Team (groupId, name, ctCollectionId);
create index IX_14857E95 on Team (uuid_, companyId, ctCollectionId);
create index IX_FC1CD5AF on Team (uuid_, ctCollectionId);
create unique index IX_1AAF62D7 on Team (uuid_, groupId, ctCollectionId);

create index IX_1E8DFB2E on Ticket (classNameId, classPK, type_);
create index IX_8BACD0AA on Ticket (companyId, classNameId, classPK, type_);
create index IX_B2468446 on Ticket (key_);

create index IX_E8AD6A2C on UserGroup (companyId, ctCollectionId);
create index IX_544FAE0D on UserGroup (companyId, externalReferenceCode, ctCollectionId);
create unique index IX_3F4FC96B on UserGroup (companyId, name, ctCollectionId);
create index IX_FFCDFCE5 on UserGroup (companyId, parentUserGroupId, ctCollectionId);
create index IX_9F5F49EC on UserGroup (uuid_, companyId, ctCollectionId);
create index IX_C990BAB8 on UserGroup (uuid_, ctCollectionId);

create index IX_CF59F0C1 on UserGroupGroupRole (groupId, ctCollectionId);
create index IX_61B91326 on UserGroupGroupRole (groupId, roleId, ctCollectionId);
create index IX_92E36EA on UserGroupGroupRole (roleId, ctCollectionId);
create index IX_663FBB6 on UserGroupGroupRole (userGroupId, ctCollectionId);
create index IX_451514B0 on UserGroupGroupRole (userGroupId, groupId, ctCollectionId);
create unique index IX_B384D815 on UserGroupGroupRole (userGroupId, groupId, roleId, ctCollectionId);

create index IX_813D2FD8 on UserGroupRole (groupId, ctCollectionId);
create index IX_AA134B3D on UserGroupRole (groupId, roleId, ctCollectionId);
create index IX_EEB38F3 on UserGroupRole (roleId, ctCollectionId);
create index IX_21D2A7C8 on UserGroupRole (userId, ctCollectionId);
create index IX_AA91DCDE on UserGroupRole (userId, groupId, ctCollectionId);
create unique index IX_F8059243 on UserGroupRole (userId, groupId, roleId, ctCollectionId);

create index IX_2AC5356C on UserGroups_Teams (companyId);
create index IX_7F187E63 on UserGroups_Teams (userGroupId);

create unique index IX_41A32E0D on UserIdMapper (type_, externalUserId);
create unique index IX_D1C44A6E on UserIdMapper (userId, type_);

create unique index IX_8B6E3ACE on UserNotificationDelivery (userId, portletId, classNameId, notificationType, deliveryType);

create index IX_BF29100B on UserNotificationEvent (type_);
create index IX_5CE95F03 on UserNotificationEvent (userId, actionRequired, archived);
create index IX_3DBB361A on UserNotificationEvent (userId, archived);
create index IX_9D34232F on UserNotificationEvent (userId, delivered, actionRequired, archived);
create index IX_BD8BD246 on UserNotificationEvent (userId, delivered, archived);
create index IX_C4EFBD45 on UserNotificationEvent (userId, deliveryType, actionRequired, archived);
create index IX_A87A585C on UserNotificationEvent (userId, deliveryType, archived);
create index IX_4BF3A7AD on UserNotificationEvent (userId, deliveryType, delivered, actionRequired, archived);
create index IX_93012C4 on UserNotificationEvent (userId, deliveryType, delivered, archived);
create index IX_7AFE83D7 on UserNotificationEvent (userId, type_, deliveryType, delivered, archived);
create index IX_A6BAFDFE on UserNotificationEvent (uuid_, companyId);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_46B0AE8E on UserTracker (sessionId);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_51338B6A on User_ (companyId, createDate, ctCollectionId);
create index IX_A09EEAB5 on User_ (companyId, createDate, modifiedDate, ctCollectionId);
create index IX_53E4FDAC on User_ (companyId, ctCollectionId);
create index IX_DBE0B8AC on User_ (companyId, defaultUser, ctCollectionId);
create index IX_16583D92 on User_ (companyId, defaultUser, status, ctCollectionId);
create unique index IX_6C9F41D8 on User_ (companyId, emailAddress, ctCollectionId);
create index IX_210A2A8D on User_ (companyId, externalReferenceCode, ctCollectionId);
create index IX_F0BD8F61 on User_ (companyId, facebookId, ctCollectionId);
create index IX_66712F3F on User_ (companyId, googleUserId, ctCollectionId);
create index IX_79724177 on User_ (companyId, modifiedDate, ctCollectionId);
create index IX_952F78E5 on User_ (companyId, openId(255), ctCollectionId);
create unique index IX_EEC1E477 on User_ (companyId, screenName, ctCollectionId);
create index IX_BC478292 on User_ (companyId, status, ctCollectionId);
create unique index IX_C15FB5CF on User_ (contactId, ctCollectionId);
create index IX_E1D5EE24 on User_ (emailAddress, ctCollectionId);
create index IX_64D54302 on User_ (portraitId, ctCollectionId);
create index IX_B5A2C66C on User_ (uuid_, companyId, ctCollectionId);
create index IX_EA9E0E38 on User_ (uuid_, ctCollectionId);

create index IX_3499B657 on Users_Groups (companyId);
create index IX_F10B6C6B on Users_Groups (userId);

create index IX_5FBB883C on Users_Orgs (companyId);
create index IX_FB646CA6 on Users_Orgs (userId);

create index IX_F987A0DC on Users_Roles (companyId);
create index IX_C1A01806 on Users_Roles (userId);

create index IX_799F8283 on Users_Teams (companyId);
create index IX_A098EFBF on Users_Teams (userId);

create index IX_BB65040C on Users_UserGroups (companyId);
create index IX_66FF2503 on Users_UserGroups (userGroupId);

create index IX_741D01F2 on VirtualHost (companyId, layoutSetId, ctCollectionId);
create index IX_6A3E4238 on VirtualHost (companyId, layoutSetId, defaultVirtualHost, ctCollectionId);
create unique index IX_76A64FBE on VirtualHost (hostname, ctCollectionId);

create unique index IX_97DFA146 on WebDAVProps (classNameId, classPK);

create index IX_1AA07A6D on Website (companyId, classNameId, classPK, primary_);
create index IX_F75690BB on Website (userId);
create index IX_712BCD35 on Website (uuid_, companyId);

create index IX_6483FCD4 on WorkflowDefinitionLink (companyId, ctCollectionId);
create index IX_701BF76D on WorkflowDefinitionLink (companyId, workflowDefinitionName, workflowDefinitionVersion, ctCollectionId);
create index IX_B6C5C563 on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK, ctCollectionId);
create index IX_65327B4C on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK, typePK, ctCollectionId);
create index IX_5E9866FC on WorkflowDefinitionLink (groupId, companyId, classNameId, ctCollectionId);
create index IX_52C09F91 on WorkflowDefinitionLink (groupId, companyId, classPK, ctCollectionId);

create index IX_688A5865 on WorkflowInstanceLink (groupId, companyId, classNameId, classPK, ctCollectionId);
create index IX_6E4C09BA on WorkflowInstanceLink (groupId, companyId, classNameId, ctCollectionId);



