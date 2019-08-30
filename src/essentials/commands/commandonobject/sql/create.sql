
CREATE TABLE IF NOT EXISTS commandOnBlock(
	world TEXT,
	x int,
	y int,
	z int,
	Commands Text,
	
	PRIMARY KEY (world, x, y, z) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS friends(
	
	
	PRIMARY KEY (friendOne, friendTwo)
);