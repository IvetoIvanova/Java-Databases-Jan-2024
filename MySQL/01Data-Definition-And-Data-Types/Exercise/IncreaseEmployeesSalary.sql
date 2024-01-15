UPDATE employees
SET salary = `salary` + (`salary` * 0.10);
SELECT `salary` FROM employees;