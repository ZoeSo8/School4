 -- liquibase formatted sql
 -- changeset zsoroka:1

CREATE INDEX IF NOT EXISTS student_name_index
ON student (name);

 -- changeset zsoroka:2

CREATE INDEX faculty_name_color_index
ON faculty (name,color);
