alter table AssetEntry add listable tinyint;

alter table AssetTag add uuid_ varchar(75);

commit;

update AssetEntry set listable = 1;

DROP_TABLE_IF_EXISTS(AssetTagProperty);
