CREATE TABLE employees (
      second_name TEXT NOT NULL PRIMARY KEY,
      first_name TEXT NOT NULL,
      date_of_birth INTEGER NOT NULL
    )
CREATE INDEX full_name_index ON employees(first_name,second_name) CREATE INDEX dob_index ON employees(date_of_birth);
