-- SELECT 
--     *
-- FROM
--     `movies_additional_info`
-- WHERE
--     id >= 15 AND id <= 25;
    
UPDATE movies_additional_info 
SET 
    runtime = runtime - 10
WHERE
    id >= 15 AND id <= 25;