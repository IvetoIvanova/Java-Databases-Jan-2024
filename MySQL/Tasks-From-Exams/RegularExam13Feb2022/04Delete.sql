DELETE FROM customers 
WHERE
    id NOT IN (SELECT DISTINCT
        customer_id
    FROM
        orders);
        
-- SET SQL_SAFE_UPDATES = 0;