DELIMITER $
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(10,4), yearly_interest_rate DECIMAL(10,4),
num_of_years INT)
RETURNS DECIMAL(10,4)
READS SQL DATA
BEGIN
	RETURN initial_sum * POW(1 + yearly_interest_rate, num_of_years);
END $
DELIMITER ;    

SELECT UFN_CALCULATE_FUTURE_VALUE(1000, 0.5, 5);
