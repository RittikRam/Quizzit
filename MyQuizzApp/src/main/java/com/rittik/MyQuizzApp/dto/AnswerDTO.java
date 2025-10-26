package com.rittik.MyQuizzApp.dto;

public class AnswerDTO {
    public Long questionId;
    public Long selectedOptionId;

    public Long getQuestionId() {
        return questionId;
    }

    public AnswerDTO() {   }

    public AnswerDTO(Long questionId, Long selectedOptionId) {
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }
}
