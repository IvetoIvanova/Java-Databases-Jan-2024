DELIMITER $

CREATE PROCEDURE udp_reduce_price(category_name VARCHAR(50))
BEGIN
	UPDATE products p
    JOIN categories c ON p.category_id = c.id
    JOIN reviews r ON p.review_id = r.id
    SET p.price = p.price - (p.price * 0.30)
    WHERE r.rating < 4 AND
    c.name = category_name;
END $

DELIMITER ;

-- CALL udp_reduce_price('Phones and tablets');

-- SELECT 
--     *
-- FROM
--     products p
--         JOIN
--     categories c ON p.category_id = c.id
--         JOIN
--     reviews r ON p.review_id = r.id
-- WHERE
--     r.rating < 4
--         AND c.name = 'Phones and tablets';