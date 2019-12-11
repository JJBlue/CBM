SELECT *
FROM commandOnBlockAction ac JOIN commandOnBlock block ON block.ID = ac.IDCommandOnBlock
WHERE world = ? AND x >= ? AND x <= ? AND y >= ? AND y <= ? AND z >= ? AND z <= ?