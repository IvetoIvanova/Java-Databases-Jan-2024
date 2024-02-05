-- SELECT COUNT(*) FROM products;

INSERT INTO products(name, type, price)
(
SELECT 
	CONCAT(last_name, ' specialty'),
	'Cocktail',
    CEILING(0.01 * salary)
FROM waiters
WHERE id > 6
);