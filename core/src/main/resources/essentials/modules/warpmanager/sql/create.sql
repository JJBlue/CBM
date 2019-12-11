
CREATE TABLE IF NOT EXISTS warps(
	name text UNIQUE NOT NULL,
	location text NOT NULL,
	itemStack text,
	tPermission BOOL,
	showWithoutPermission BOOl,
	autoLore BOOL,
	pos INT DEFAULT 0,
	condition text,
	executes text,
	
	PRIMARY KEY (name)
);