INSERT INTO commandOnBlockAction(IDCommandOnBlock, CoBAction, Command)
VALUES (
(
	SELECT ID
	FROM commandOnBlock
	WHERE world = ? AND x = ? AND y = ? AND z = ?
	LIMIT 1
), ?, ?)