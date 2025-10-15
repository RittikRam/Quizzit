package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.*;
import com.rittik.MyQuizzApp.entity.Option;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Quiz;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.repository.QuestionRepository;
import com.rittik.MyQuizzApp.repository.QuizRepository;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizRepository  quizRepository;


    private List<OptionResponseDTO> mapOptionsToDTO(List<Option> options) {
        return options.stream()
                .map(opt -> new OptionResponseDTO(
                        opt.getId(),
                        opt.getText(),
                        opt.isCorrect()
                ))
                .collect(Collectors.toList());
    }
    private List<UserOptionResponseDTO> mapUserOptionsToDTO(List<Option> options) {
        return options.stream()
                .map(opt -> new UserOptionResponseDTO(
                        opt.getId(),
                        opt.getText()
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public QuizResponseDTO createQuiz(QuizRequestDTO quizRequestDTO) throws IllegalArgumentException{
        if(quizRepository.findByTitle(quizRequestDTO.getTitle()).isPresent()){
            throw new IllegalArgumentException("Quiz With Same Title Already Exists");
        }

        Long topicID = quizRequestDTO.getTopicIdDTO().id;
        Topic topic = topicRepository.findById(topicID)
                .orElseThrow(()-> new IllegalArgumentException("No Such Topic Found"));

        Quiz q =Quiz.builder()
                .title(quizRequestDTO.getTitle())
                .description(quizRequestDTO.getDescription())
                .topic(topic)
                .difficulty(quizRequestDTO.getDifficultyLevel())
                .build();

        List<Question> list = questionRepository.findRandomQuestionsByTopicAndDifficulty(topicID,q.getDifficulty(),quizRequestDTO.getQuestionCount());

        q.setQuestions(list);


        Quiz saved = quizRepository.save(q);
        TopicResponseDTO topicResponseDTO = new TopicResponseDTO(topic.getId(),topic.getName(),topic.getDescription());
        List<AdminQuestionResponseDTO> questionResponseDTOS  = saved.getQuestions()
                .stream()
                .map(que-> {
                    return new AdminQuestionResponseDTO(que.getId(),que.getText(),que.getDifficultyLevel(), mapOptionsToDTO(que.getOptions()));
                })
                .collect(Collectors.toList());

        QuizResponseDTO responseDTO = new QuizResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                topicResponseDTO,
                saved.getDifficulty(),
                questionResponseDTOS
        );
        return responseDTO;
    }

    @Transactional(readOnly = true)
    public List<UserQuizResponseDTO> getAll() {
        return quizRepository.findAll()
                .stream()
                .map(q-> {
                    TopicResponseDTO tdo = new TopicResponseDTO(
                            q.getTopic().getId()
                            ,q.getTopic().getName()
                            ,q.getTopic().getDescription());
                    List<UserQuestionResponseDTO> qdo = q.getQuestions()
                                                    .stream()
                                                    .map(questionr-> {
                                                        return new UserQuestionResponseDTO(questionr.getId(),questionr.getText(),questionr.getDifficultyLevel(), mapUserOptionsToDTO(questionr.getOptions()));
                                                            }
                                                    )
                                                    .collect(Collectors.toList());
                    return new UserQuizResponseDTO(
                            q.getId(),
                            q.getTitle(),
                            q.getDescription(),
                            tdo,
                            q.getDifficulty(),
                            qdo
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserQuizResponseDTO> getById(Long id) {
        return quizRepository.findById(id)
                .map(q -> {
                    TopicResponseDTO tdo = new TopicResponseDTO(
                            q.getTopic().getId()
                            ,q.getTopic().getName()
                            ,q.getTopic().getDescription());

                    List<UserQuestionResponseDTO> qdo = q.getQuestions()
                            .stream()
                            .map(questionr-> {
                                        return new UserQuestionResponseDTO(questionr.getId(),questionr.getText(),questionr.getDifficultyLevel(),  mapUserOptionsToDTO(questionr.getOptions()));
                                    }
                            )
                            .collect(Collectors.toList());

                    return new UserQuizResponseDTO(
                            q.getId(),
                            q.getTitle(),
                            q.getDescription(),
                            tdo,
                            q.getDifficulty(),
                            qdo
                    );
    });
    }


}
