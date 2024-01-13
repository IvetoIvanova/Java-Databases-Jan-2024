CREATE TABLE people(
	id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    picture BLOB,
    height DOUBLE(6, 2),
    weight DOUBLE(6, 2),
    gender CHAR(1) NOT NULL,
    birthdate DATE NOT NULL,
    biography BLOB
);

INSERT INTO people (name, picture, height, weight, gender, birthdate, biography) VALUES
('Peter', 'test', 1.89, 85.5, 'm', '1976-05-04', 'text'),
('Sara', 'test', 1.65, 56.4, 'f', '1978-06-08', 'text'),
('John', 'test', 1.80, 80.5, 'm', '1984-09-10', 'text'),
('Merry', 'test', 1.69, 59.5, 'f', '1992-08-08', 'text'),
('Tom', 'test', 1.82, 84.4, 'm', '1986-02-02', 'text');