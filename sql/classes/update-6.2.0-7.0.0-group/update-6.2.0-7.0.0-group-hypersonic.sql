alter table Group_ add groupKey varchar(150) null;

update Group_ set groupKey = name;

alter table Group_ add inheritContent bit;
