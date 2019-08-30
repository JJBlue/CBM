
CREATE TABLE IF NOT EXISTS paintingsFileInformation(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	filePath TEXT,
	fileName Text NOT NULL
);

CREATE TABLE IF NOT EXISTS paintings(
	mapID INT UNIQUE NOT NULL,
	startX INT NOT NULL,
	startY INT NOT NULL,
	file INT NOT NULL,
	
	PRIMARY KEY (mapID),
	FOREIGN KEY (file) REFERENCES paintingsFileInformation(ID) ON DELETE CASCADE
);