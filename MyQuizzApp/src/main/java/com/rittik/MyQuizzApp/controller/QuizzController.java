package com.rittik.MyQuizzApp.controller;

import com.rittik.MyQuizzApp.dto.QuizRequestDTO;
import com.rittik.MyQuizzApp.dto.QuizResponseDTO;
import com.rittik.MyQuizzApp.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizzController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<QuizResponseDTO> getQuizzes(){
        return quizService.getAll();
    }
//    @GetMapping("/{id}")
//    public QuizResponseDTO getQuizById(@PathVariable Long id){
//        return quizService.findById(id);
//
//    }
    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> getQuizById(@PathVariable Long id){
        return quizService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@Valid @RequestBody QuizRequestDTO quizRequestDTO){
        QuizResponseDTO q = quizService.createQuiz(quizRequestDTO);
        return ResponseEntity.status(201).body(q);
    }
}
