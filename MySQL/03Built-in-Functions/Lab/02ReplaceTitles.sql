SELECT 
    REPLACE(title, 'The', '***') AS 'title'
FROM
    books
WHERE
    SUBSTR(title, 1, 4) = 'The ';