alter table BlogsEntry add subtitle longtext null;
alter table BlogsEntry add coverImageCaption longtext null;
alter table BlogsEntry add coverImageFileEntryId bigint;
alter table BlogsEntry add coverImageURL longtext null;
alter table BlogsEntry add smallImageFileEntryId bigint;

alter table Contact_ drop column aimSn;
alter table Contact_ drop column icqSn;
alter table Contact_ drop column msnSn;
alter table Contact_ drop column mySpaceSn;
alter table Contact_ drop column ymSn;

DROP_TABLE_IF_EXISTS(CyrusUser);

DROP_TABLE_IF_EXISTS(CyrusVirtual);

drop index IX_C803899D on DDMStructureLink;

drop index IX_F8E90438 on DLFileEntryMetadata;

alter table DLFileEntryMetadata drop column fileEntryTypeId;

alter table DLFolder add restrictionType integer;

update DLFolder set restrictionType = 1 where overrideFileEntryTypes = 1;

alter table DLFolder drop column overrideFileEntryTypes;

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

alter table Layout drop column iconImage;
alter table Layout drop column wapThemeId;
alter table Layout drop column wapColorSchemeId;

alter table LayoutRevision drop column iconImage;
alter table LayoutRevision drop column wapThemeId;
alter table LayoutRevision drop column wapColorSchemeId;

alter table Organization_ add logoId bigint;

alter table RatingsEntry add uuid_ varchar(75) null;

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

insert into Region (regionId, countryId, regionCode, name, active_) values (33001, 33, 'AT-1', 'Burgenland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33002, 33, 'AT-2', 'Kärnten', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33003, 33, 'AT-3', 'Niederösterreich', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33004, 33, 'AT-4', 'Oberösterreich', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33005, 33, 'AT-5', 'Salzburg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33006, 33, 'AT-6', 'Steiermark', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33007, 33, 'AT-7', 'Tirol', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33008, 33, 'AT-8', 'Vorarlberg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (33009, 33, 'AT-9', 'Vienna', 1);

update Region set regionCode = 'BB' where regionId = 4004 and regionCode = 'BR';
update Region set name = 'Monza e Brianza', regionCode = 'MB' where regionId = 8060 and regionCode = 'MZ';

alter table ResourcePermission add primKeyId bigint;
alter table ResourcePermission add viewActionId tinyint;

alter table Subscription add groupId bigint;

alter table Team add uuid_ varchar(75);

alter table User_ add googleUserId varchar(75) null;

alter table UserNotificationEvent add deliveryType integer;
alter table UserNotificationEvent add actionRequired tinyint;
