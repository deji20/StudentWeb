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

CREATE TABLE course(
id INT auto_increment PRIMARY KEY,
name VARCHAR(50) NOT NULL,
startDate date NOT NULL,
etcs INT NOT NULL,
description VARCHAR(400) NOT NULL
);