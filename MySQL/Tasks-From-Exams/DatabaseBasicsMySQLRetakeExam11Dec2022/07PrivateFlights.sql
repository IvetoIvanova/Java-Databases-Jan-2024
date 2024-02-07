SELECT 
    CONCAT(SUBSTRING(UPPER(last_name), 1, 2),
            country_id) AS flight_code,
    CONCAT(first_name, ' ', last_name) AS full_name,
    country_id
FROM
    passengers
WHERE
    id NOT IN (SELECT DISTINCT
            passenger_id
        FROM
            flights_passengers)
ORDER BY country_id;

-- SELECT 
--     SUBSTRING(UPPER(last_name), 1, 2)
-- FROM
--     passengers;