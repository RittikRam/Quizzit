package com.rittik.MyQuizzApp.dto;


import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class QuestionRequestDTO {
    @NotBlank(message = "Question text is required")
    public String text;
    private DifficultyLevel difficultyLevel;
    private TopicIdDTO topicIdDTO;

    private List<OptionRequestDTO> options;

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public TopicIdDTO getTopicIdDTO() {
        return topicIdDTO;
    }

    public void setTopicIdDTO(TopicIdDTO topicIdDTO) {
        this.topicIdDTO = topicIdDTO;
    }

    public List<OptionRequestDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequestDTO> options) {
        this.options = options;
    }

    // getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
