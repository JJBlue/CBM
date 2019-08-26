
CREATE TABLE IF NOT EXISTS players(
	uuid VARCHAR(36) UNIQUE NOT NULL,
	commandSpyOperator BOOL,
	logoutTime TIMESTAMP,
	loginTime TIMESTAMP,
	
	PRIMARY KEY (uuid)
);