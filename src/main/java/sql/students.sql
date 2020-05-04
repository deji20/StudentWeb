DROP DATABASE IF EXISTS students;
CREATE DATABASE students;

USE students;

CREATE TABLE student(
id INT auto_increment PRIMARY KEY,
firstName varchar(20) NOT NULL,
lastName varchar(20) NOT NULL,
enrollmentDate date NOT NULL,
cpr INT NOT NULL,
picture VARCHAR(200) NULL
);