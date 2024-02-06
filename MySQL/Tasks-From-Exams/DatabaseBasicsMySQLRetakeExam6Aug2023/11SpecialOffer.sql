DELIMITER $

CREATE PROCEDURE udp_special_offer (first_name VARCHAR(50))
BEGIN
	UPDATE property_offers po
    JOIN agents a ON a.id = po.agent_id
    SET po.price = po.price * 0.9
    WHERE a.first_name = first_name;
END $

DELIMITER ;

-- CALL real_estate_db.udp_special_offer('Hans');

-- SELECT first_name, price
-- FROM agents a
-- JOIN property_offers po ON a.id = po.agent_id
-- WHERE first_name = 'Hans';