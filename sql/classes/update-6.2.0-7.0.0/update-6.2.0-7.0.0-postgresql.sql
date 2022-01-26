alter table BlogsEntry add subtitle text null;
alter table BlogsEntry add coverImageCaption text null;
alter table BlogsEntry add coverImageFileEntryId bigint;
alter table BlogsEntry add coverImageURL text null;
alter table BlogsEntry add smallImageFileEntryId bigint;

alter table Contact_ drop column aimSn;
alter table Contact_ drop column icqSn;
alter table Contact_ drop column msnSn;
alter table Contact_ drop column mySpaceSn;
alter table Contact_ drop column ymSn;

DROP_TABLE_IF_EXISTS(CyrusUser);

DROP_TABLE_IF_EXISTS(CyrusVirtual);

drop index IX_C803899D;

drop index IX_F8E90438;

alter table DLFileEntryMetadata drop column fileEntryTypeId;

alter table DLFolder add restrictionType integer;

update DLFolder set restrictionType = 1 where overrideFileEntryTypes = true;

alter table DLFolder drop column overrideFileEntryTypes;

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

insert into Region (regionId, countryId, regionCode, name, active_) values (33001, 33, 'AT-1', 'Burgenland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33002, 33, 'AT-2', 'Kärnten', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33003, 33, 'AT-3', 'Niederösterreich', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33004, 33, 'AT-4', 'Oberösterreich', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33005, 33, 'AT-5', 'Salzburg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33006, 33, 'AT-6', 'Steiermark', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33007, 33, 'AT-7', 'Tirol', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33008, 33, 'AT-8', 'Vorarlberg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33009, 33, 'AT-9', 'Vienna', true);

update Region set regionCode = 'BB' where regionId = 4004 and regionCode = 'BR';
update Region set name = 'Monza e Brianza', regionCode = 'MB' where regionId = 8060 and regionCode = 'MZ';

alter table ResourcePermission add primKeyId bigint;
alter table ResourcePermission add viewActionId bool;

alter table Subscription add groupId bigint;

alter table Team add uuid_ varchar(75);

alter table User_ add googleUserId varchar(75) null;

alter table UserNotificationEvent add deliveryType integer;
alter table UserNotificationEvent add actionRequired bool;
