package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;

import java.util.List;

public class UserQuestionResponseDTO {
    public Long id;
    public String text;
    public DifficultyLevel difficultyLevel;
    public List<UserOptionResponseDTO> userOptions;

    public UserQuestionResponseDTO() {}

    public UserQuestionResponseDTO(Long id, String text, DifficultyLevel difficultyLevel, List<UserOptionResponseDTO> userOptions) {
        this.id = id;
        this.text = text;
        this.difficultyLevel = difficultyLevel;
        this.userOptions = userOptions;
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

    public List<UserOptionResponseDTO> getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(List<UserOptionResponseDTO> userOptions) {
        this.userOptions = userOptions;
    }
}
