SELECT 
    c.name,
    c.currency,
    COUNT(fp.passenger_id) AS `booked_tickets`
FROM
    countries AS c
        JOIN
    flights f ON f.destination_country = c.id
        JOIN
    flights_passengers AS fp ON f.id = fp.flight_id
GROUP BY c.id
HAVING `booked_tickets` >= 20
ORDER BY `booked_tickets` DESC;