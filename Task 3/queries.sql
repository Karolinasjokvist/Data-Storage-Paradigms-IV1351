-- Select all lessons given each month in a year
CREATE VIEW lesson_count_month AS
    SELECT
	EXTRACT(month FROM time) AS month,
	count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' GROUP BY EXTRACT(month FROM time)
	ORDER BY EXTRACT(month FROM time) ASC;


-- Select all lessons each month in a year grouped by lesson type
CREATE VIEW lesson_count_type_month AS
    SELECT lesson_type ,COUNT(*) as amount ,EXTRACT(MONTH FROM time) as month from lesson
    WHERE EXTRACT(YEAR FROM time) = '2021'
    GROUP BY month, lesson_type;


-- Get the average number of lessons in a year
CREATE VIEW lesson_average_year AS
    SELECT count(CASE WHEN EXTRACT(YEAR FROM time) = '2021' THEN 1 ELSE NULL END)/12.0 as average FROM lesson

CREATE VIEW lesson_average_type AS
    SELECT lesson_type ,COUNT(*)/12.0 as amount from lesson
    WHERE EXTRACT(YEAR FROM time) = '2021'
    GROUP BY lesson_type;


-- Select all instructors that have taught more than one lesson
CREATE VIEW lesson_average_instructor AS
    SELECT instructor_id, count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' GROUP BY instructor_id HAVING COUNT(*) > 3;


-- Select all lessons that is in the next week
CREATE MATERIALIZED VIEW lesson_next_week AS
    SELECT lesson_type, to_char(time, 'Day') as weekday, genre, time,
    CASE
        WHEN amount_students = max_students THEN 'full'
        WHEN amount_students = max_students - 1 THEN '1 seats left'
        WHEN amount_students = max_students - 2 THEN '2 seats left'
        ELSE 'More than 2 seats left'
    END as seats_left
    FROM lesson WHERE date_trunc('week', time) = date_trunc('week', now()) + interval '1 week' AND lesson_type = 'ensemble' ORDER BY genre, weekday;
