CREATE TABLE categories(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    daily_rate DOUBLE(6,2),
    weekly_rate DOUBLE(6,2),
    monthly_rate DOUBLE(6,2),
    weekend_rate DOUBLE(6,2)
);

INSERT INTO categories (category, daily_rate, weekly_rate, monthly_rate, weekend_rate) VALUES
('SUV', 1.2, 4.6, 7, 5),
('Bus', 1.2, 4.6, 8, 9),
('Car', 1.2, 4.6, 7, 5);

CREATE TABLE cars(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plate_number VARCHAR(30) NOT NULL UNIQUE,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    car_year DATE,
    category_id INT,
    doors INT,
    picture BLOB,
    car_condition VARCHAR(255),
    available BLOB
);

INSERT INTO cars(id, plate_number, make, model, car_year, category_id, doors, picture, car_condition, available) VALUES
(1, 'PA6666BP', 'VW', 'Touareg', '2024-01-01', 2, 5, 'BEAUTY', 'NEW', true),
(2, 'CT8888BP', 'BMW', 'X6', '2020-01-01', 2, 5, 'BEAUTY', 'NEW', true),
(3, 'CH6996BP', 'Toyota', 'Corolla', '2019-01-01', 2, 5, 'BEAUTY', 'NEW', true);

CREATE TABLE employees(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    notes TEXT
);

INSERT INTO employees(first_name, last_name, title, notes) VALUES
('Anton', 'Georgiev', 'Senior', 'TEST'),
('Iva', 'Ivanova', 'Junior', 'TEST'),
('Ivan', 'Aleksandrin', 'Senior', 'TEST');

CREATE TABLE customers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    driver_licence_number INT NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(60),
    zip_code INT,
    notes TEXT
);

INSERT INTO customers(driver_licence_number, full_name, address, city, zip_code, notes) VALUES
(14562, 'Anton Ivanov', 'South', 'Plovdiv', 4000, 'TEST'),
(5846, 'Ben Georgiev', 'Center', 'Stara Zagora', 6000, 'TEST'),
(14589562, 'Tom Johns', 'North', 'Plovdiv', 4000, 'TEST');

CREATE TABLE rental_orders (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    customer_id INT NOT NULL,
    car_id INT NOT NULL,
    car_condition VARCHAR(255),
    tank_level INT,
    kilometrage_start INT,
    kilometrage_end INT,
    total_kilometrage INT,
    start_date DATE,
    end_date DATE,
    total_days INT,
    rate_applied DOUBLE(6 , 2 ),
    tax_rate DOUBLE(6 , 2 ),
    order_status BOOL,
    notes TEXT
);

INSERT INTO rental_orders(employee_id, customer_id, car_id, car_condition, tank_level, kilometrage_start, kilometrage_end, total_kilometrage, start_date, end_date, total_days, rate_applied, tax_rate, order_status, notes) VALUES
(1,1,1, 'GOOD', 100, 1111,2222,3333,'2022-12-05', '2022-12-06',1,1.2,2.2,true, 'Finished'),
(2,2,2, 'NEW', 100, 1111,2222,3333,'2023-11-04', '2023-11-06',1,1.2,2.2,true, 'Finished'),
(3,3,3, 'OLD', 100, 3333,6666,9999,'2000-09-05', '2000-09-06',1,1.2,2.2,true, 'Finished');