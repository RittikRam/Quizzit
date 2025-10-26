package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.AdminQuestionResponseDTO;
import com.rittik.MyQuizzApp.dto.OptionResponseDTO;
import com.rittik.MyQuizzApp.dto.QuestionRequestDTO;
import com.rittik.MyQuizzApp.entity.Option;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.exception.ResourceNotFoundException;
import com.rittik.MyQuizzApp.repository.QuestionRepository;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;


    private List<OptionResponseDTO> mapOptionsToDTO(List<Option> options) {
        return options.stream()
                .map(opt -> new OptionResponseDTO(
                        opt.getId(),
                        opt.getText(),
                        opt.isCorrect()
                ))
                .collect(Collectors.toList());
    }

    private AdminQuestionResponseDTO mapQuestionToResponseDTO(Question question) {
        List<OptionResponseDTO> optionDTOs = mapOptionsToDTO(question.getOptions());

        return new AdminQuestionResponseDTO(
                question.getId(),
                question.getText(),
                question.getDifficultyLevel(),
                optionDTOs
        );
    }

    public List<AdminQuestionResponseDTO> getAll() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(q-> new AdminQuestionResponseDTO(q.getId(),q.getText(),q.getDifficultyLevel(),mapOptionsToDTO(q.getOptions())))
                .collect(Collectors.toList());

    }

    @Transactional
    public AdminQuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO){
        Long topicId = questionRequestDTO.getTopicIdDTO().id;
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic Not Found"));

        Question question = Question.builder()
                .text(questionRequestDTO.getText())
                .difficultyLevel(questionRequestDTO.getDifficultyLevel())
                .topic(topic)
                .options(new ArrayList<>())
                .build();

        List<Option> optionList = questionRequestDTO.getOptions().stream()
                .map(optionRequestDTO -> Option.builder()
                        .text(optionRequestDTO.getText())
                        .isCorrect(optionRequestDTO.isCorrect())
                        .question(question)
                        .build())
                .collect(Collectors.toList());

        question.setOptions(optionList);

        Question savedQuestion = questionRepository.save(question);

        return mapQuestionToResponseDTO(savedQuestion);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    @Transactional
    public AdminQuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO questionRequestDTO){
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Question doesn't exist with :"+id));
        Long topicID = questionRequestDTO.getTopicIdDTO().id;

        Topic newTopic = topicRepository.findById(topicID)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicID));

        existingQuestion.setText(questionRequestDTO.getText());
        existingQuestion.setDifficultyLevel(questionRequestDTO.getDifficultyLevel());
        existingQuestion.setTopic(newTopic);

        List<Option> newOptions = questionRequestDTO.getOptions().stream()
                .map(optionDto -> Option.builder()
                        .text(optionDto.getText())
                        .isCorrect(optionDto.isCorrect())
                        .question(existingQuestion) // Set the critical back-reference
                        .build())
                .collect(Collectors.toList());

        existingQuestion.getOptions().clear();
        existingQuestion.getOptions().addAll(newOptions);

        Question savedQuestion = questionRepository.save(existingQuestion);

        return mapQuestionToResponseDTO(savedQuestion);


    }
}
