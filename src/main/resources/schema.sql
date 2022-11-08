CREATE TABLE IF NOT EXISTS student (
                                       id SERIAL PRIMARY KEY,
                                       name TEXT NOT NULL,
                                       dob DATE NOT NULL,
                                       credits INTEGER,
                                       avarage FLOAT
);

CREATE TABLE IF NOT EXISTS professor (
                                         id SERIAL PRIMARY KEY,
                                         name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS relation (
                                         id SERIAL PRIMARY KEY,
                                         student_id INTEGER,
                                         professor_id INTEGER
);



ALTER TABLE relation DROP CONSTRAINT IF EXISTS relation_fk1;
ALTER TABLE relation DROP CONSTRAINT IF EXISTS relation_fk2;
ALTER TABLE  relation  ADD CONSTRAINT  relation_fk1 FOREIGN KEY (student_id) REFERENCES student(id);
ALTER TABLE  relation  ADD CONSTRAINT  relation_fk2 FOREIGN KEY (professor_id) REFERENCES professor(id);