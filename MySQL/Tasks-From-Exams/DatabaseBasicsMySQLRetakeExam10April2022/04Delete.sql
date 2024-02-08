-- SELECT 
--     c.id,
--     (SELECT COUNT(*) FROM movies m WHERE m.country_id = c.id) AS count
-- FROM
--     countries AS c
-- HAVING `count` = 0;

DELETE FROM countries AS c
WHERE (SELECT COUNT(*) FROM movies m WHERE m.country_id = c.id) = 0;