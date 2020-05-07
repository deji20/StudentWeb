package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Course {
    private int id;
    private String name;
    private int etcs;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    public Course(){
        this(null, 0, null, null);
    }
    public Course(String name, int etcs, String description, LocalDate startDate) {
        this.name = name;
        this.etcs = etcs;
        this.description = description;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEtcs() {
        return etcs;
    }

    public void setEtcs(int etcs) {
        this.etcs = etcs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", etcs=" + etcs +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
