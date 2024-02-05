SELECT 
    id, `name`, COUNT(op.order_id) AS `count`
FROM
    products AS p
        JOIN
    orders_products AS op ON op.product_id = p.id
GROUP BY p.id
HAVING `count` >= 5
ORDER BY `count` DESC , `name` ASC;