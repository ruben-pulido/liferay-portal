alter table AssetEntry add listable bool;

alter table AssetTag add uuid_ varchar(75);

commit;

update AssetEntry set listable = true;

DROP_TABLE_IF_EXISTS(AssetTagProperty);
