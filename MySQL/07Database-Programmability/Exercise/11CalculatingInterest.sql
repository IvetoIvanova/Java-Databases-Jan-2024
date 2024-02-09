DELIMITER $
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(10,4), yearly_interest_rate DECIMAL(10,4),
num_of_years INT)
RETURNS DECIMAL(10,4)
READS SQL DATA
BEGIN
	RETURN initial_sum * POW(1 + yearly_interest_rate, num_of_years);
END $

CREATE PROCEDURE usp_calculate_future_value_for_account(account_id INT, interest_rate DOUBLE(10,4))
BEGIN
	SELECT a.id AS account_id, 
    ah.first_name, 
    ah.last_name, 
    a.balance AS current_balance, 
    ufn_calculate_future_value(a.balance, interest_rate, 5) AS balance_in_5_years
    FROM account_holders ah
    JOIN accounts a ON ah.id = a.account_holder_id
    WHERE a.id = account_id;
END $
DELIMITER ;

CALL usp_calculate_future_value_for_account(1, 0.1);
