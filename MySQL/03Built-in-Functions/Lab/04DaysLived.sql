SELECT 
    CONCAT(first_name, ' ', last_name) AS 'Full Name',
    ABS(TIMESTAMPDIFF(DAY, born, died)) AS 'Days Lived'
FROM
    authors;