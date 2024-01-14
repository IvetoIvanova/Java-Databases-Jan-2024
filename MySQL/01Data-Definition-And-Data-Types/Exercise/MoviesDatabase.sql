-- CREATE DATABASE IF NOT EXISTS `movies`;
-- USE `movies`;

CREATE TABLE directors (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    director_name VARCHAR(100) NOT NULL,
    notes TEXT
);

INSERT INTO directors(director_name, notes) VALUES
('Sam', 'TEST'),
('Ben', 'TEST'),
('Tom', 'TEST'),
('Merry', 'TEST'),
('Jean', 'TEST');

-- SELECT * FROM directors;

CREATE TABLE genres (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(100) NOT NULL,
    notes TEXT
);

INSERT INTO genres(genre_name, notes) VALUES
('Action', 'TEST'),
('Comedy', 'TEST123'),
('Fantasy', 'TEST4'),
('Mystery', 'TEST5'),
('Drama', 'TEST6');

-- SELECT * FROM genres;

CREATE TABLE categories (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    notes TEXT
);

INSERT INTO categories(category_name, notes) VALUES
('12', 'TEST01'),
('12A', 'TEST1'),
('15', 'TEST2'),
('18+', 'TEST3'),
('16', 'TEST56');

-- SELECT * FROM categories;

CREATE TABLE movies (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    director_id INT NOT NULL,
    copyright_year YEAR,
    length TIME,
    genre_id INT NOT NULL,
    category_id INT NOT NULL,
    rating DOUBLE(6,2),
    notes TEXT
);

INSERT INTO movies(title, director_id, copyright_year, length, genre_id, category_id, rating, notes) VALUES
('AVATAR: THE WAY OF WATER', 1, 2022, '03:12:00', 1, 1, 13, 'TEST1'),
('The Godfather', 2, 1972, '02:55:00', 2, 2, 11, 'TEST2'),
('The Lord of the Rings: The Fellowship of the Ring', 3, 2001, '02:58:00', 3, 3, 12, 'TEST3'),
('The Matrix', 4, 1999, '02:16:00', 4, 4, 11, 'TEST4'),
('Aliens', 5, 1986, '02:17:00', 5, 5, 13, 'TEST5');

-- SELECT * FROM movies;