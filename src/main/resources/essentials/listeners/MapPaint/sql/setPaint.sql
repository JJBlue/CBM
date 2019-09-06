INSERT OR REPLACE INTO paintings(mapID, startX, startY, file)
VALUES (?, ?, ?,
(
	SELECT ID
	FROM paintingsFileInformation
	WHERE filePath = ? AND fileName = ?
))