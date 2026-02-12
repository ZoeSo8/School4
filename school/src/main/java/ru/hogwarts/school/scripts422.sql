Описание структуры: у каждого человека есть машина.
Причем несколько человек могут пользоваться одной машиной.
У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
У каждой машины есть марка, модель и стоимость.
Также не забудьте добавить таблицам первичные ключи и связать их.

CREATE TABLE drivers (
personal_id SERIAL PRIMARY KEY
name TEXT NOT NULL,
age INTEGER CHECK(age>=16),
driving_passport BOOLEAN DEFAULT (driving_passport =false)
car_id INTEGER NOT NULL
CONSTRAINT fk_car
    FOREIGN KEY (car_id)
    REFERENCES cars(car_id)
)
CREATE TABLE cars (
mark TEXT NOT NULL
model TEXT NOT NULL
priсe INTEGER CHECK (priсe>0)
car_id SERIAL PRIMARY KEY


)