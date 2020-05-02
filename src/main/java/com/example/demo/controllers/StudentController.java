package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.InMemoryStudentRepositoryImpl;
import com.example.demo.repositories.StudentRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private IStudentRepository studentRepository;

    public StudentController() {
        studentRepository = new InMemoryStudentRepositoryImpl();
    }

    @GetMapping("/student/create")
    public String createStudent(Model model) {
        model.addAttribute("students", studentRepository);
        return "/student/create";
    }

    @PostMapping("/create/addStudent")
    public String addStudent(@ModelAttribute Student stuFromPost){
        studentRepository.create(stuFromPost);
        return "redirect:/";
    }

    @GetMapping("/student/edit")
    public String edit(@RequestParam int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("student", stu);
        return "/student/edit";

    }

    @GetMapping("/studentList")
    public String studentList(Model model){
        model.addAttribute("students" , studentRepository.readAll());
        return "studentList";
    }

    @GetMapping("/student")
    public String getStudentByParameter(@RequestParam int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("student", stu);
        return "student/detail";
    }
}