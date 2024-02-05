-- SELECT 
--     w.id,
--     (SELECT COUNT(*) FROM orders WHERE waiter_id = w.id) AS order_count
-- FROM
--     weiters AS w
-- HAVING `order_count` = 0;

DELETE FROM waiters AS w
WHERE (SELECT COUNT(*) FROM orders WHERE waiter_id = w.id) = 0;

-- SET SQL_SAFE_UPDATES = 0;