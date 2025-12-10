-- 1. Получить всех студентов, возраст которых между 10 и 20
SELECT *
FROM student
WHERE age BETWEEN 10 AND 20;

-- 2. Получить всех студентов, отображая только их имена
SELECT name
FROM student;

-- 3. Получить всех студентов, у которых в имени есть буква 'О' (регистронезависимый)
SELECT *
FROM student
WHERE name ILIKE '%о%';

-- 4. Получить всех студентов, у которых возраст меньше ID
SELECT *
FROM student
WHERE age < id;

-- 5. Получить всех студентов, отсортированных по возрасту (по возрастанию)
SELECT *
FROM student
ORDER BY age ASC;

