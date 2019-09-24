
CREATE TABLE IF NOT EXISTS spawn(
	ID int UNIQUE NOT NULL
	Name text UNIQUE,
	location text NOT NULL,
	
	PRIMARY KEY (ID)
);