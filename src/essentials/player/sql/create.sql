
CREATE TABLE IF NOT EXISTS players(
	uuid VARCHAR(36) UNIQUE NOT NULL,
	name VARCHAR(20),
	commandSpyOperator BOOLEAN,
	logoutTime TIMESTAMP,
	loginTime TIMESTAMP,
	
	PRIMARY KEY (uuid)
);

CREATE TABLE IF NOT EXISTS friends(
	friendOne VARCHAR(20) NOT NULL,
	friendTwo VARCHAR(20) NOT NULL,
	accepted BOOLEAN,
	friendstime TIMESTAMP, /* sendRequest or player accepted the request*/
	
	PRIMARY KEY (friendOne, friendTwo)
);