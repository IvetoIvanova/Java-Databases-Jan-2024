DELIMITER $

CREATE FUNCTION udf_average_alumni_grade_by_course_name(course_name VARCHAR(60))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
	DECLARE result DECIMAL(10,2);
    SET result := (SELECT AVG(sc.grade) FROM courses c
					JOIN students_courses sc ON c.id = sc.course_id
					JOIN students s ON sc.student_id = s.id
					WHERE c.name = course_name AND s.is_graduated = 1
					GROUP BY c.id);
	RETURN result;
END $
DELIMITER ;

-- SELECT udf_average_alumni_grade_by_course_name('Quantum Physics');

-- SELECT 
--     AVG(sc.grade), c.name
-- FROM
--     courses c
--         JOIN
--     students_courses sc ON c.id = sc.course_id
--         JOIN
--     students s ON sc.student_id = s.id
-- WHERE
--     c.name = 'Quantum Physics'
--         AND s.is_graduated = 1
-- GROUP BY c.id;