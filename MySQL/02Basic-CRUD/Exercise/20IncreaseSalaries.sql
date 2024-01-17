-- SELECT department_id, name FROM departments
-- WHERE name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services');

UPDATE employees
SET salary = salary * 1.12
WHERE department_id IN (1, 2, 4, 11);

SELECT 
    salary
FROM
    employees;