package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.InMemoryStudentRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private IStudentRepository studentRepository;

    public StudentController() {
        studentRepository = new InMemoryStudentRepositoryImpl();
    }

    @GetMapping("/create")
    public String createStudent(Model model) {
        model.addAttribute("students", studentRepository);
        return "/create";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student studentFromPost){
        studentRepository.create(studentFromPost);
        return "redirect:/studentList";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam int id) {
        model.addAttribute("students", studentRepository.read(id));
        return "/edit";
    }

    @PostMapping("/editStudent")
    public String editStudent(@ModelAttribute Student studentFromPost){
        studentRepository.update(studentFromPost);
        return "redirect:/studentList";
    }


    @GetMapping("/studentList")
    public String studentList(Model model){
        model.addAttribute("students" , studentRepository.readAll());
        return "/studentList";
    }

    @GetMapping("/student")
    @ResponseBody
    public String getStudentByParameter(@RequestParam int id) {
        Student stu = studentRepository.read(id);
        return "The name is: " + stu.getFirstName() + " and the cpr is " + stu.getCpr();
    }


}