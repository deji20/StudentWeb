package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.InMemoryStudentRepositoryImpl;
import com.example.demo.repositories.StudentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class StudentController {
    @Autowired
    public ServletContext context;
    private IStudentRepository studentRepository;

    public StudentController() {
        studentRepository = new StudentRepositoryImpl();
    }

    @GetMapping("/create")
    public String createStudent(Model model) {
        model.addAttribute("students", studentRepository);
        return "/create";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student studentFromPost, @RequestParam MultipartFile picture){
        Student student = studentRepository.create(studentFromPost);
        //add picture if picture exists
        if(!picture.isEmpty()){addPicture(student, picture);}
        return "redirect:/studentList";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam int id) {
        model.addAttribute("students", studentRepository.read(id));
        return "/edit";
    }

    @PostMapping("/editStudent")
    public String editStudent(@ModelAttribute Student student, @RequestParam MultipartFile picture){
        if(!picture.isEmpty()) {addPicture(student, picture);}
        else{student.profilePic = studentRepository.read(student.id).profilePic;}
        studentRepository.update(student);
        return "redirect:/student?id="+student.id;
    }


    @GetMapping("/studentList")
    public String studentList(Model model){
        model.addAttribute("students" , studentRepository.readAll());
        return "/studentList";
    }

    @GetMapping("/student")
    public String getStudentByParameter(Model model, @RequestParam int id) {
        model.addAttribute("student", studentRepository.read(id));
        System.out.println(studentRepository.read(id));
        return "detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id){
        studentRepository.delete(id);
        return "redirect:/studentList";
    }

    //saves the given picture on the server and sets the students profilePic variable as the path to the picture
    //should perhaps, be moved to studentRepositoryImpl to separate responsibilities
    //not an optimal solution as pictures wont be retained when restarting server
    private void addPicture(Student student, MultipartFile picture) {
        //gets the true path of the running server
        String path = context.getRealPath("/");
        try {
            student.profilePic = student.id + ".jpg";
            studentRepository.update(student);
            File file = new File(path + student.profilePic);
            picture.transferTo(file);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}