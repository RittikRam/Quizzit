// OptionResponseDTO.java
package com.rittik.MyQuizzApp.dto;

import java.util.List;

public class OptionResponseDTO {
    public Long id;
    public String text;
    public boolean isCorrect;

    public OptionResponseDTO(Long id, String text, boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}