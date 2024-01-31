SELECT 
    c.continent_code,
    c.currency_code,
    COUNT(*) AS currency_usage
FROM
    countries c
GROUP BY c.continent_code , c.currency_code
HAVING currency_usage > 1
    AND currency_usage = (SELECT 
        COUNT(*) AS max_usage
    FROM
        countries
    WHERE
        continent_code = c.continent_code
    GROUP BY currency_code
    ORDER BY max_usage DESC
    LIMIT 1)
ORDER BY c.continent_code, c.currency_code;

-- SELECT currency_code, COUNT(*) AS max_usage FROM countries
-- WHERE continent_code = 'EU'
-- GROUP BY currency_code
-- ORDER BY max_usage DESC
-- LIMIT 1;