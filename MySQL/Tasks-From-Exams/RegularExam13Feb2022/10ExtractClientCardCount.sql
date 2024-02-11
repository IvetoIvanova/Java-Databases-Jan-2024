DELIMITER $

CREATE FUNCTION udf_customer_products_count(name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE result INT;
    SET result := (SELECT 
				COUNT(op.product_id) AS total_products
				FROM
				customers c
				JOIN orders o ON o.customer_id = c.id
				JOIN orders_products op ON op.order_id = o.id
				WHERE
				c.first_name = name
				GROUP BY c.id);
    RETURN result;
END $
DELIMITER ;

-- SELECT udf_customer_products_count('Shirley') AS total_products;

-- SELECT c.first_name,c.last_name, udf_customer_products_count('Shirley') as `total_products` FROM customers c
-- WHERE c.first_name = 'Shirley';

-- SELECT 
--     COUNT(op.product_id) AS total_products
-- FROM
--     customers c
--         JOIN
--     orders o ON o.customer_id = c.id
--     JOIN orders_products op ON op.order_id = o.id
-- WHERE
--     c.first_name = 'Shirley'
-- GROUP BY c.id;