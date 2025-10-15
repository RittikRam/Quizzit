package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.AdminQuestionResponseDTO;
import com.rittik.MyQuizzApp.dto.OptionResponseDTO;
import com.rittik.MyQuizzApp.dto.UserOptionResponseDTO;
import com.rittik.MyQuizzApp.entity.Option;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    private List<OptionResponseDTO> mapOptionsToDTO(List<Option> options) {
        return options.stream()
                .map(opt -> new OptionResponseDTO(
                        opt.getId(),
                        opt.getText(),
                        opt.isCorrect()
                ))
                .collect(Collectors.toList());
    }

    public List<AdminQuestionResponseDTO> getAll() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(q-> new AdminQuestionResponseDTO(q.getId(),q.getText(),q.getDifficultyLevel(),mapOptionsToDTO(q.getOptions())))
                .collect(Collectors.toList());

    }
}
