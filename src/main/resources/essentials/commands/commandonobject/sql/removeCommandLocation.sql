DELETE FROM commandOnBlockAction
WHERE IDCommandOnBlock = (
	SELECT ID
	FROM commandOnBlock
	WHERE world = ? AND x = ? AND y = ? AND z = ?
	LIMIT 1
) AND CoBAction = ? AND Command = ?