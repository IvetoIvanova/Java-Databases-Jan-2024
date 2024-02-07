INSERT INTO airplanes(model, passengers_capacity, tank_capacity, cost)
(
SELECT 
	CONCAT(REVERSE(p.first_name), '797'),
    CHAR_LENGTH(p.last_name) * 17,
    (p.id) * 790,
    CHAR_LENGTH(p.first_name) * 50.6
FROM passengers p
WHERE id <= 5
);