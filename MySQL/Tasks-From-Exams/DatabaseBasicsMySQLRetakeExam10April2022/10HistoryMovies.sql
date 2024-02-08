DELIMITER $
CREATE FUNCTION udf_actor_history_movies_count(full_name VARCHAR(50))
	RETURNS INT
    DETERMINISTIC
BEGIN
	DECLARE history_movies INT;
    SET history_movies := (
		SELECT COUNT(*) FROM actors a
		JOIN movies_actors ma ON ma.actor_id = a.id 
        JOIN movies m ON ma.movie_id = m.id
        JOIN genres_movies gm ON gm.movie_id = ma.movie_id
        JOIN genres g ON gm.genre_id = g.id
        WHERE (full_name = CONCAT(a.first_name, ' ', a.last_name)) AND g.name = 'History'
        GROUP BY gm.genre_id
    );
    RETURN history_movies;
END$
DELIMITER ;

-- SELECT * FROM genres;
-- SELECT udf_actor_history_movies_count('Jared Di Batista');
-- SELECT udf_actor_history_movies_count('Stephan Lundberg');

-- SELECT COUNT(*) FROM actors a
-- 		JOIN movies_actors ma ON ma.actor_id = a.id 
--         JOIN movies m ON ma.movie_id = m.id
--         JOIN genres_movies gm ON gm.movie_id = ma.movie_id
--         JOIN genres g ON gm.genre_id = g.id
--         WHERE first_name = 'Stephan' AND g.name = 'History';