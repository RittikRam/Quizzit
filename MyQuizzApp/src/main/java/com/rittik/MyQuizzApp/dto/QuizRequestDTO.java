package com.rittik.MyQuizzApp.dto;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class QuizRequestDTO {

    @NotBlank(message = "title is required")
    public String title;
    @Size(max = 500)
    public String description;
    public TopicIdDTO topicIdDTO;
    public DifficultyLevel difficultyLevel;
    public List<QuestionRequestDTO> questions;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TopicIdDTO getTopicIdDTO() {
        return topicIdDTO;
    }

    public void setTopicIdDTO(TopicIdDTO topicIdDTO) {
        this.topicIdDTO = topicIdDTO;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<QuestionRequestDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequestDTO> questions) {
        this.questions = questions;
    }
}
