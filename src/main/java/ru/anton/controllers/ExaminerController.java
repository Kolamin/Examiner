package ru.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.anton.services.QuestionDataService;

@Controller
public class ExaminerController {

    @Autowired
    QuestionDataService questionDataService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("allQuestions", questionDataService.getAllQuestion());
        return "home";
    }

}