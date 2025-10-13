package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.QuestionType;

public class QuestionResponseDTO {
    public Long id;
    public String text;
    public QuestionType type;

    public QuestionResponseDTO() {}

    public QuestionResponseDTO(Long id, String text, QuestionType type) {
        this.id = id;
        this.text = text;
        this.type = type;
    }
}
