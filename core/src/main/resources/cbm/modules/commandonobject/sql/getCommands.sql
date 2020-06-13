SELECT *
FROM commandOnBlockAction
WHERE IDCommandOnBlock IN (
	SELECT ID
	FROM commandOnBlock
	WHERE world = ? AND x = ? AND y = ? AND z = ?
	LIMIT 1
)