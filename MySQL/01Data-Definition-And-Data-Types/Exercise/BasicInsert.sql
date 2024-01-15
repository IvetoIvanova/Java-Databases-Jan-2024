INSERT INTO towns (`name`) VALUES
('Sofia'),
('Plovdiv'),
('Varna'),
('Burgas');

INSERT INTO departments (`name`) VALUES
('Engineering'),
('Sales'),
('Marketing'),
('Software Development'),
('Quality Assurance');

INSERT INTO employees(first_name, middle_name, last_name, job_title, department_id, hire_date, salary) VALUES
('Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 4, STR_TO_DATE('01/02/2013', '%d/%m/%Y'), '3500.00'),
('Petar', 'Petrov', 'Petrov', 'Senior Engineer', 1, STR_TO_DATE('02/03/2004', '%d/%m/%Y'), '4000.00'),
('Maria', 'Petrova', 'Ivanova', 'Intern', 5, STR_TO_DATE('28/08/2016', '%d/%m/%Y'), '525.25'),
('Georgi', 'Terziev', 'Ivanov', 'CEO', 2, STR_TO_DATE('09/12/2007', '%d/%m/%Y'), '3000.00'),
('Peter', 'Pan', 'Pan', 'Intern', 3, STR_TO_DATE('28/08/2016', '%d/%m/%Y'), '599.88');




