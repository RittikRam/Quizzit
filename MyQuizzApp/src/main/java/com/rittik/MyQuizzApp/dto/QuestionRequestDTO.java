package com.rittik.MyQuizzApp.dto;


import com.rittik.MyQuizzApp.entity.QuestionType;
import jakarta.validation.constraints.NotBlank;

public class QuestionRequestDTO {
    @NotBlank(message = "Question text is required")
    public String text;

    public QuestionType type; // Enum: MCQ, TRUE_FALSE, etc.

    // getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }
}
