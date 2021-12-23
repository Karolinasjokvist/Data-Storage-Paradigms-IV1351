-- Select all lessons given each month in a year
SELECT count(CASE WHEN time >= '2021-01-01' AND time < '2021-02-01' THEN 1 ELSE NULL END) FROM lesson

CREATE VIEW lesson_count_month AS
    SELECT
	EXTRACT(month FROM time) AS month,
	count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' GROUP BY EXTRACT(month FROM time)
	ORDER BY EXTRACT(month FROM time) ASC;


-- Select all lessons each month in a year grouped by lesson type
SELECT count(CASE WHEN time >= '2019-01-01' AND time < '2023-11-01' AND lesson_type='individual' THEN 1 ELSE NULL END) FROM lesson

SELECT
EXTRACT(month FROM time),
count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='individual' GROUP BY EXTRACT(month FROM time)
ORDER BY EXTRACT(month FROM time);

SELECT
EXTRACT(month FROM time),
count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='group' GROUP BY EXTRACT(month FROM time)
ORDER BY EXTRACT(month FROM time);

SELECT
EXTRACT(month FROM time),
count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='ensemble' GROUP BY EXTRACT(month FROM time)
ORDER BY EXTRACT(month FROM time);

CREATE VIEW Lesson_types_year
    AS WITH
    data_ensamble AS (
        SELECT
        EXTRACT(month FROM time),
        count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='ensemble' GROUP BY EXTRACT(month FROM time)
        ORDER BY EXTRACT(month FROM time)
    ),
    data_individual AS (
        SELECT
        EXTRACT(month FROM time),
        count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='individual' GROUP BY EXTRACT(month FROM time)
        ORDER BY EXTRACT(month FROM time)
    ),
    data_group AS (
        SELECT
        EXTRACT(month FROM time),
        count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' AND lesson_type='group' GROUP BY EXTRACT(month FROM time)
        ORDER BY EXTRACT(month FROM time)
    )
    SELECT data_ensamble.month, data_ensamble.count, data_individual.count, data_group.count FROM data_ensamble
    INNER JOIN data_individual ON data_ensamble.month = data_individual.month
    INNER JOIN data_group ON data_ensamble.month = data_group.month;


-- Get the average number of lessons in a year
CREATE VIEW lesson_average_instructor AS
    SELECT instructor_id, count(*) FROM lesson WHERE EXTRACT(YEAR FROM time) = '2021' GROUP BY instructor_id HAVING COUNT(*) > 3;


-- Select all instructors that have taught more than one lesson
CREATE VIEW instructor_count_lessons AS
    SELECT instructor_id, count(*) FROM lesson WHERE time >= '2021-01-01' AND time < '2022-01-01' GROUP BY instructor_id HAVING COUNT(*) > 3;


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
