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
    public String addStudent(@ModelAttribute Student studentFromPost){
        studentRepository.create(studentFromPost);
        return "redirect:/studentList";
    }

    @GetMapping("/student/edit")
    public String edit() {
        return "/student/edit";

    }

    @GetMapping("/studentList")
    public String studentList(Model model){
        model.addAttribute("students" , studentRepository.readAll());
        return "studentList";
    }

    @GetMapping("/student")
    @ResponseBody
    public String getStudentByParameter(@RequestParam int id) {
        Student stu = studentRepository.read(id);
        return "The name is: " + stu.getFirstName() + " and the cpr is " + stu.getCpr();
    }
}