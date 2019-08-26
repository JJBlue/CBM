
CREATE TABLE IF NOT EXISTS players(
	uuid VARCHAR(36) UNIQUE NOT NULL,
	name VARCHAR(20),
	commandSpyOperator BOOLEAN,
	logoutTime TIMESTAMP,
	loginTime TIMESTAMP,
	
	PRIMARY KEY (uuid)
);