USE localizer;

WITH ItemPath AS (
    SELECT
        id,
        name,
		parent_id,
        CAST(name AS VARCHAR(MAX)) AS path,
		ROW_NUMBER() OVER (ORDER BY id) as row
    FROM
        items
    WHERE
        name = 'Hammer'  -- Replace 'Hammer' with the item you're searching for
    UNION ALL
    SELECT
        i.id,
        i.name,
		i.parent_id,
        CONCAT(i.name, ' -> ', p.path),
		p.row + 1 as row
    FROM
        items i
    JOIN
        ItemPath p ON i.id = p.parent_id
	Where p.parent_id is not null
)
SELECT TOP 1
    path
FROM
    ItemPath
ORDER BY row DESC;