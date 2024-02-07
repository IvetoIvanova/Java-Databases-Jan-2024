SELECT 
    flight_code,
    departure,
    (CASE
        WHEN
            TIME(departure) >= '05:00:00'
                AND TIME(departure) <= '11:59:59'
        THEN
            'Morning'
        WHEN
            TIME(departure) >= '12:00:00'
                AND TIME(departure) <= '16:59:59'
        THEN
            'Afternoon'
        WHEN
            TIME(departure) >= '17:00:00'
                AND TIME(departure) <= '20:59:59'
        THEN
            'Evening'
        ELSE 'Night'
    END) AS day_part
FROM
    flights
ORDER BY flight_code DESC;