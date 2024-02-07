UPDATE flights f
        JOIN
    countries c ON f.departure_country = c.id 
SET 
    airplane_id = airplane_id + 1
WHERE
    c.name = 'Armenia';
    
    