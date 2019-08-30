
CREATE TABLE IF NOT EXISTS commandOnBlock(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	world TEXT NOT NULL,
	x int NOT NULL,
	y int NOT NULL,
	z int NOT NULL,
	
	UNIQUE (world, x, y, z)
);

CREATE TABLE IF NOT EXISTS commandOnBlockAction (
	IDCommandOnBlock INT NOT NULL,
	CoBAction Text NOT NULL, /* Leftclick, Rightclick, Boths, ...*/
	Command TEXT NOT NULL,
	
	FOREIGN KEY (IDCommandOnBlock) REFERENCES commandOnBlock(ID) ON DELETE CASCADE
);