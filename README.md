# Mock File Upload
Project to upload file, read data and insert the data to DB.

Currently the project only accepts .txt file types

## SQL Changes
Create a database named "employees" and create a table employees using the below DDL query.

CREATE TABLE public.employee
(
    id bigint NOT NULL,
    age integer NOT NULL,
    employee_name character varying(255),
    unique_id character varying(255),
    CONSTRAINT employee_pkey PRIMARY KEY (id)
)
