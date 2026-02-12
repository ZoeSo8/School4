SELECT student.name AS student_name, student.age, faculty.name AS faculty_name
FROM student JOIN faculty ON student.faculty_id = faculty.id
WHERE student.avatar IS NOT NULL;