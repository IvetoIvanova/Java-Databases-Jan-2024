DELIMITER $
CREATE FUNCTION udf_offers_from_city_name (cityName VARCHAR(50))
	RETURNS INT
    DETERMINISTIC
BEGIN
	DECLARE offers_count INT;
    SET offers_count := (
		SELECT COUNT(*) FROM property_offers po
        JOIN properties p ON po.property_id = p.id
        JOIN cities c ON c.id = p.city_id
        WHERE cityName = c.name
    );
    RETURN offers_count;
END$
DELIMITER ;

-- SELECT real_estate_db.udf_offers_from_city_name('Vienna');
-- SELECT real_estate_db.udf_offers_from_city_name('Amsterdam');