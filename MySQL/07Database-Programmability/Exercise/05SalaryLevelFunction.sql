DELIMITER $
CREATE FUNCTION ufn_get_salary_level(salary DECIMAL(10,4))
RETURNS VARCHAR(20)
READS SQL DATA
BEGIN
	DECLARE result VARCHAR(20);
    IF (salary < 30000) THEN
		SET result := 'Low';
	ELSEIF (salary >= 30000 AND salary <= 50000) THEN
		SET result := 'Average';
	ELSE
		SET result := 'High';
	END IF;
    RETURN result;
END $
        

DELIMITER ;

SELECT UFN_GET_SALARY_LEVEL(43300);