package ru.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anton.services.QuestionDataService;

@Controller
@RequestMapping("/tests")
public class ExaminerController {

    @Autowired
    QuestionDataService questionDataService;


    @GetMapping()
    public String home(){
       questionDataService.clearFieldAnswer(questionDataService.getAllQuestion());

        return "/tests/home";
    }

    @GetMapping("/all")
    public String questions(Model model) {
        model.addAttribute("allQuestions", questionDataService.getAllQuestion());
        return "tests/questions";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("question", questionDataService.showSingleQuestion(id));

        return "tests/index";
    }

    @GetMapping("/answer")
    public String getAnswer(Model mode){
        mode.addAttribute("answers", questionDataService);
        return "tests/answer";
    }


}