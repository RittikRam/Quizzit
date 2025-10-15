package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;

import java.util.List;

public class UserQuizResponseDTO {

    public Long id;
    public String title;
    public String description;
    public TopicResponseDTO topic;
    public DifficultyLevel difficultyLevel;
    public List<UserQuestionResponseDTO> questions;

    public UserQuizResponseDTO() {    }

    public UserQuizResponseDTO(Long id, String title, String description, TopicResponseDTO topic, DifficultyLevel difficultyLevel, List<UserQuestionResponseDTO> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.difficultyLevel = difficultyLevel;
        this.questions = questions;
    }
}
