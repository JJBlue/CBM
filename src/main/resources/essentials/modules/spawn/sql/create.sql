
CREATE TABLE IF NOT EXISTS spawn(
	ID int UNIQUE NOT NULL,
	name text UNIQUE NOT NULL,
	location text NOT NULL,
	
	PRIMARY KEY (ID)
);