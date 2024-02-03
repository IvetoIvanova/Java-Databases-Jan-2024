SELECT 
    CONCAT(s.first_name, ' ', s.last_name) AS full_name,
    SUBSTRING(s.email, 2, 10) AS username,
    REVERSE(s.phone) AS password
FROM
    students s
        LEFT JOIN
    students_courses st ON s.id = st.student_id
WHERE
    st.course_id IS NULL
ORDER BY password DESC;