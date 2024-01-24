CREATE TABLE mountains(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE peaks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    mountain_id INT NOT NULL,
    CONSTRAINT fk_peaks_mountains_id_mountain_id FOREIGN KEY (mountain_id)
        REFERENCES mountains (id) 
);
