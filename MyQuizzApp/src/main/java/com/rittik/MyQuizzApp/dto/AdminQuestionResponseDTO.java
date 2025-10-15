package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import java.util.List;

public class AdminQuestionResponseDTO {
    public Long id;
    public String text;
    public DifficultyLevel difficultyLevel;
    public List<OptionResponseDTO> options;

    public AdminQuestionResponseDTO() {}

    public AdminQuestionResponseDTO(Long id, String text, DifficultyLevel difficultyLevel, List<OptionResponseDTO> options) {
        this.id = id;
        this.text = text;
        this.difficultyLevel = difficultyLevel;
        this.options = options;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<OptionResponseDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionResponseDTO> options) {
        this.options = options;
    }
}
