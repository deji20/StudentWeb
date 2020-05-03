package com.example.demo.repositories;

import com.example.demo.models.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InMemoryStudentRepositoryImpl implements IStudentRepository{
    private List<Student> inMemoryDatabase;

    public InMemoryStudentRepositoryImpl(){
        this.inMemoryDatabase = new ArrayList<Student>(
                Arrays.asList(
                        new Student(1, "Nicklas","Frederiksen", LocalDate.of(1970, 04, 20), "31134115-1231"),
                        new Student(2, "Bent","Karlsen", LocalDate.of(1971, 02,10), "31134115-4112"),
                        new Student(3, "Bob","Alicesen", LocalDate.of(1982,03,29), "233124f14-5551")

                )
        );
    }

    @Override
    public boolean create(Student student) {
        this.inMemoryDatabase.add(student);
        return true;
    }

    @Override
    public Student read(int id) {
        for(Student stu : inMemoryDatabase){
            if(stu.getId() == id){
                return stu;
            }
        }
        return null;
    }

    @Override
    public List<Student> readAll() {
        return inMemoryDatabase;
    }

    @Override
    public boolean update(Student student) {
        for(Student stu : inMemoryDatabase) {
            if (stu.getId() == student.getId()) {

            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for(Student stu : inMemoryDatabase){
            if(stu.getId() == id){
                inMemoryDatabase.remove(stu.getId());
            }
        }
        return true;
    }
}
