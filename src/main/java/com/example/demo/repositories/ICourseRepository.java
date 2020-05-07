package com.example.demo.repositories;

import com.example.demo.models.Course;

import java.util.List;

public interface ICourseRepository {
    public int create(Course course);

    public Course read(int id);

    public List<Course> readAll();

    public boolean update(Course course);

    public boolean delete(int id);
}
