
CREATE TABLE IF NOT EXISTS paintingsFileInformation(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	filePath TEXT NOT NULL,
	fileName Text NOT NULL,
	
	UNIQUE (filePath, fileName)
);

CREATE TABLE IF NOT EXISTS paintings(
	mapID INT UNIQUE NOT NULL,
	startX INT NOT NULL,
	startY INT NOT NULL,
	file INT NOT NULL,
	
	PRIMARY KEY (mapID),
	FOREIGN KEY (file) REFERENCES paintingsFileInformation(ID) ON DELETE CASCADE
);