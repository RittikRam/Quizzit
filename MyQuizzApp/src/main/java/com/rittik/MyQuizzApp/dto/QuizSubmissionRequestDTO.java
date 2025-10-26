package com.rittik.MyQuizzApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class QuizSubmissionRequestDTO {
    @NotNull(message = "Quiz ID is required for submission")
    public Long quizId;

    @Size(min = 1, message = "Submission must contain at least one answer")
    public List<AnswerDTO> answers;


    public QuizSubmissionRequestDTO() {   }

    public QuizSubmissionRequestDTO(Long quizId, List<AnswerDTO> answers) {
        this.quizId = quizId;
        this.answers = answers;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
