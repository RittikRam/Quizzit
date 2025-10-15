package com.rittik.MyQuizzApp.controller;

import com.rittik.MyQuizzApp.dto.AdminQuestionResponseDTO;
import com.rittik.MyQuizzApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<AdminQuestionResponseDTO> getAllQuestions(){
        List<AdminQuestionResponseDTO> questions = questionService.getAll();

        return questions;
    }

}
