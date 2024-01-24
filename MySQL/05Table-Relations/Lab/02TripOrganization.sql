SELECT 
    driver_id AS 'driver_id',
    vehicles.vehicle_type,
    CONCAT(campers.first_name,
            ' ',
            campers.last_name) AS 'driver_name'
FROM
    vehicles
        JOIN
    campers ON campers.id = vehicles.driver_id;