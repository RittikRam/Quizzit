package com.rittik.MyQuizzApp.controller;

import com.rittik.MyQuizzApp.dto.AdminQuestionResponseDTO;
import com.rittik.MyQuizzApp.dto.QuestionRequestDTO;
import com.rittik.MyQuizzApp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<AdminQuestionResponseDTO> createQuestion(@Valid @RequestBody QuestionRequestDTO questionRequestDTO){
        AdminQuestionResponseDTO adminQuestionResponseDTO = questionService.createQuestion(questionRequestDTO);
        return ResponseEntity.status(201).body(adminQuestionResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminQuestionResponseDTO> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequestDTO questionRequestDTO) {

        AdminQuestionResponseDTO updated = questionService.updateQuestion(id, questionRequestDTO);
        return ResponseEntity.ok(updated);
    }

}
