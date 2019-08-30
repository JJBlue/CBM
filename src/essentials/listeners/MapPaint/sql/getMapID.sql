SELECT mapID
FROM paintings p JOIN paintingsFileInformation pi ON p.file = pi.ID
WHERE startX = ? AND startY = ? AND filePath = ? AND fileName = ?