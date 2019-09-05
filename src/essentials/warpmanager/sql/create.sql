
CREATE TABLE IF NOT EXISTS warps(
	name text UNIQUE NOT NULL,
	location text NOT NULL,
	itemStack text,
	tPermission BOOL,
	showWithoutPermission BOOl,
	autoLore BOOL,
	cost INTEGER,
	pos INT DEFAULT 0,
	
	PRIMARY KEY (name)
);