package com.rittik.MyQuizzApp.dto;

public class OptionLoadDTO {
    // Matches the "text" key in the JSON
    public String text;

    // Matches the "isCorrect" key in the JSON
    public boolean isCorrect;

    // Getters and Setters (needed for Jackson ObjectMapper)
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }
}