DELIMITER $

CREATE PROCEDURE udp_delay_flight(code VARCHAR(50))
BEGIN
	UPDATE flights
    SET has_delay = 1,
    departure = DATE_ADD(departure, INTERVAL 30 MINUTE)
    WHERE flight_code = code;
END $

DELIMITER ;

-- CALL udp_delay_flight('ZP-782');

-- SELECT * FROM flights
-- WHERE flight_code = 'ZP-782';