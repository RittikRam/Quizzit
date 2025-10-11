package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import com.rittik.MyQuizzApp.entity.Topic;

public class QuizResponseDTO {

    public Long id;
    public String title;
    public String description;
    public TopicResponseDTO topic;
    public DifficultyLevel difficultyLevel;

    public QuizResponseDTO() {    }

    public QuizResponseDTO(Long id, String title, String description, TopicResponseDTO topic, DifficultyLevel difficultyLevel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.difficultyLevel = difficultyLevel;
    }


}
