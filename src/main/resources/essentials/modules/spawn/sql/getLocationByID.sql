SELECT *
FROM spawn
WHERE ID <= ?
ORDER BY ID DESC
LIMIT 1