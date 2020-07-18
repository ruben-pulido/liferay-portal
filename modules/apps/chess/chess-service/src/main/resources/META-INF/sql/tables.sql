create table ChessGame (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	chessGameId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	whitePlayerId LONG,
	blackPlayerId LONG,
	moves VARCHAR(75) null,
	winnerPlayerId LONG
);