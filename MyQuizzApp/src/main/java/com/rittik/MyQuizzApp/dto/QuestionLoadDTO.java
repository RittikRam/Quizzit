package com.rittik.MyQuizzApp.dto;

import java.util.List;

public class QuestionLoadDTO {
    // Matches the "text" key in the JSON
    public String text;

    // Matches the "options" array in the JSON
    public List<OptionLoadDTO> options;

    // Getters and Setters (needed for Jackson ObjectMapper)
    public String getText() {
        return text;
    }
    public List<OptionLoadDTO> getOptions() {
        return options;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setOptions(List<OptionLoadDTO> options) {
        this.options = options;
    }
}