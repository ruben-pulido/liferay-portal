create index IX_D2609D72 on ChessGame (groupId);
create index IX_CA0CBA2C on ChessGame (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1195DBAE on ChessGame (uuid_[$COLUMN_LENGTH:75$], groupId);