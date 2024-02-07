DELIMITER $

CREATE FUNCTION udf_count_flights_from_country(country VARCHAR(50))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE result INT;
    SET result := (SELECT 
		COUNT(f.departure_country) AS flights_count
		FROM flights f
		JOIN countries c ON f.departure_country = c.id
		WHERE c.name = country);
    RETURN result;
END $
DELIMITER ;

-- SELECT udf_count_flights_from_country('Brazil');
-- SELECT udf_count_flights_from_country('Philippines');

-- SELECT 
--     COUNT(f.departure_country) AS flights_count
-- FROM
--     flights f
--         JOIN
--     countries c ON f.departure_country = c.id
-- WHERE
--     c.name = 'Philippines'
-- GROUP BY c.id;
