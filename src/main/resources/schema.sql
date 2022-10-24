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
