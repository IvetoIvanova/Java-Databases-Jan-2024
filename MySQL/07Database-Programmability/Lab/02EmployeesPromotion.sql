-- DELIMITER $

CREATE PROCEDURE usp_raise_salaries(department_name VARCHAR(50))
BEGIN
	UPDATE employees e 
    JOIN departments d
    ON e.department_id = d.department_id
    SET e.salary = e.salary * 1.05
    WHERE d.name = department_name;
END -- $

-- DELIMITER ;

-- SELECT 
--     employee_id, first_name, salary
-- FROM
--     employees e
--         JOIN
--     departments d ON e.department_id = d.department_id
-- WHERE
--     d.name = 'Finance'
-- ORDER BY first_name , salary;

-- CALL  usp_raise_salaries('Finance');