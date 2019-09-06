SELECT *
FROM paintings p JOIN paintingsFileInformation pi ON p.file = pi.ID
WHERE mapID = ?