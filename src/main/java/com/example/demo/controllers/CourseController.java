package com.example.demo.controllers;

import com.example.demo.models.Course;
import com.example.demo.repositories.CourseRepositoryImpl;
import com.example.demo.repositories.ICourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    ICourseRepository courseRepository;
    public CourseController(){
        courseRepository = new CourseRepositoryImpl();
    }

    @GetMapping("")
    public String courses(Model model){
        model.addAttribute("courses", courseRepository.readAll());
        return "courses";
    }
    @PostMapping("/addCourse")
    public String add(@ModelAttribute Course course){
        courseRepository.create(course);
        return "redirect:/courses";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model){
        model.addAttribute("course", courseRepository.read(id));
        return "course_edit";
    }

    @GetMapping("/create")
    public String create(){
        return "course_create";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id){
        courseRepository.delete(id);
        return "redirect:/courses";
    }
}
