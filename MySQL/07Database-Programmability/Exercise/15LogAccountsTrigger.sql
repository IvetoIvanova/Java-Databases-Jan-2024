CREATE TABLE logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    old_sum DECIMAL(19 , 4 ),
    new_sum DECIMAL(19 , 4 )
);
 
DELIMITER $
CREATE TRIGGER tr_balance_update_accounts
AFTER UPDATE
ON accounts
FOR EACH ROW
BEGIN
	IF OLD.balance <> NEW.balance
    THEN
	INSERT INTO logs(account_id, old_sum, new_sum)
    VALUE (OLD.id, OLD.balance, NEW.balance);
    END IF;
END $
DELIMITER ;

CALL usp_deposit_money(1,10);
SELECT * FROM logs;