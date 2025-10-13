package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.QuestionResponseDTO;
import com.rittik.MyQuizzApp.dto.QuizRequestDTO;
import com.rittik.MyQuizzApp.dto.QuizResponseDTO;
import com.rittik.MyQuizzApp.dto.TopicResponseDTO;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Quiz;
import com.rittik.MyQuizzApp.entity.Topic;
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
    private TopicRepository topicRepository;

    @Autowired
    private QuizRepository  quizRepository;



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

        List<Question> questions = quizRequestDTO.getQuestions()
                .stream()
                .map(qDto -> Question.builder()
                        .text(qDto.getText())
                        .type(qDto.getType())
                        .quiz(q)
                        .build())
                .collect(Collectors.toList());

        q.setQuestions(questions);


        Quiz saved = quizRepository.save(q);
        TopicResponseDTO topicResponseDTO = new TopicResponseDTO(topic.getId(),topic.getName(),topic.getDescription());
        List<QuestionResponseDTO> questionResponseDTOS  = saved.getQuestions()
                .stream()
                .map(que->new QuestionResponseDTO(que.getId(),que.getText(),que.getType()))
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
    public List<QuizResponseDTO> getAll() {
        return quizRepository.findAll()
                .stream()
                .map(q-> {
                    TopicResponseDTO tdo = new TopicResponseDTO(
                            q.getTopic().getId()
                            ,q.getTopic().getName()
                            ,q.getTopic().getDescription());
                    List<QuestionResponseDTO> qdo = q.getQuestions()
                                                    .stream()
                                                    .map(questionr-> new QuestionResponseDTO(
                                                            questionr.getId(),
                                                            questionr.getText(),
                                                            questionr.getType()
                                                    ))
                                                    .collect(Collectors.toList());
                    return new QuizResponseDTO(
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
    public Optional<QuizResponseDTO> getById(Long id) {
        return quizRepository.findById(id)
                .map(q -> {
                    TopicResponseDTO tdo = new TopicResponseDTO(
                            q.getTopic().getId()
                            ,q.getTopic().getName()
                            ,q.getTopic().getDescription());

                    List<QuestionResponseDTO> qdo = q.getQuestions()
                            .stream()
                            .map(questionr-> new QuestionResponseDTO(
                                    questionr.getId(),
                                    questionr.getText(),
                                    questionr.getType()
                            ))
                            .collect(Collectors.toList());

                    return new QuizResponseDTO(
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
