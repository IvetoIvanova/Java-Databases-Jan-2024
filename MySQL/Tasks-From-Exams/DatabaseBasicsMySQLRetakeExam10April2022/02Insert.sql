-- SELECT * FROM actors
-- WHERE id <= 10;

SELECT * FROM actors;

INSERT INTO actors(first_name, last_name, birthdate, height, awards, country_id)
(
SELECT
	REVERSE(a.first_name),
	REVERSE(a.last_name),
    DATE_SUB(a.birthdate, INTERVAL 2 DAY),
    a.height + 10,
    a.country_id,
	(SELECT id FROM countries c WHERE c.name = 'Armenia')
FROM actors a 
WHERE a.id <= 10
);