CREATE TABLE `users`(
	`id` INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(30) UNIQUE NOT NULL,
    `password` VARCHAR(26) NOT NULL,
    `profile_picture` BLOB,
    `last_login_time` DATETIME,
    `is_deleted` BOOL
);

INSERT INTO users(username, password, profile_picture, last_login_time, is_deleted) 
VALUES
('John', '568', 'test', NOW(), false),
('Tom', '8246', 'test', NOW(), false),
('Peter', '23327', 'test', NOW(), true),
('Tomas', '233247', 'test', NOW(), false),
('Ben', '235327', 'test', NOW(), false);