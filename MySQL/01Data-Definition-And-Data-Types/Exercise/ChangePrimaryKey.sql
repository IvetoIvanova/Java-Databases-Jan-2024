ALTER TABLE users
DROP PRIMARY KEY,
ADD PRIMARY KEY (id, username);

ALTER TABLE users
CHANGE last_login_time last_login_time DATETIME DEFAULT NOW();