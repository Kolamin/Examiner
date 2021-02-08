package ru.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anton.models.Question;
import ru.anton.services.QuestionDataService;

@Controller
@RequestMapping("/tests")
public class ExaminerController {

    @Autowired
    QuestionDataService questionDataService;


    @GetMapping()
    public String home(){

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
       // model.addAttribute("answer", new Answer());
        return "tests/index";
    }

    @PostMapping("/answer/{id}")
    public String getAnswer(Model model, @ModelAttribute Question answer, @PathVariable("id") int id){
        model.addAttribute("answers",  answer);
        model.addAttribute("id", id);
        return "tests/answer";
    }


}