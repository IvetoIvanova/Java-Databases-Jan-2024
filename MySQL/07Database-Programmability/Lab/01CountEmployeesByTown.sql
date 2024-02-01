DELIMITER $

CREATE FUNCTION ufn_count_employees_by_town(town_name VARCHAR(50)) RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE result INT;
	SET result := (SELECT COUNT(*) FROM employees e
    JOIN addresses a
    ON e.address_id = a.address_id
    JOIN towns t
    ON a.town_id = t.town_id
    WHERE t.name = town_name);
    RETURN result;
END -- $

DELIMITER ;
SELECT COUNT(*) FROM employees;
