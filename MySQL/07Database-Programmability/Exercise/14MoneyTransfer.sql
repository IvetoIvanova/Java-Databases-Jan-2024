DELIMITER $
CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, amount DECIMAL(19,4))
BEGIN
	START TRANSACTION;
    IF ((SELECT COUNT(*) FROM accounts WHERE id = from_account_id) <> 1 OR 
		(SELECT COUNT(*) FROM accounts WHERE id = to_account_id) <> 1 OR
        (from_account_id = to_account_id) OR
    ((SELECT balance FROM accounts WHERE id = from_account_id) < amount) OR
    (amount < 0)) THEN
    ROLLBACK;
    ELSE
    UPDATE accounts SET balance = balance - amount WHERE id = from_account_id;
	UPDATE accounts SET balance = balance + amount WHERE id = to_account_id;
    COMMIT;
    END IF;
END $
DELIMITER ;

CALL usp_transfer_money(1, 2, 150);
