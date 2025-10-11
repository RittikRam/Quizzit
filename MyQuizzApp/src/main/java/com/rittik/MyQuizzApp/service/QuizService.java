package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.QuizRequestDTO;
import com.rittik.MyQuizzApp.dto.QuizResponseDTO;
import com.rittik.MyQuizzApp.dto.TopicResponseDTO;
import com.rittik.MyQuizzApp.entity.Quiz;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.repository.QuizRepository;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizRepository  quizRepository;



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

        Quiz saved = quizRepository.save(q);
        TopicResponseDTO topicResponseDTO = new TopicResponseDTO(topic.getId(),topic.getName(),topic.getDescription());
        QuizResponseDTO responseDTO = new QuizResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                topicResponseDTO,
                saved.getDifficulty()
        );
        return responseDTO;
    }

    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getAll() {
        return quizRepository.findAll()
                .stream()
                .map(q-> new QuizResponseDTO(q.getId(),q.getTitle(),q.getDescription(),
                        new TopicResponseDTO(q.getTopic().getId(),q.getTopic().getName(),q.getTopic().getDescription())
                        ,q.getDifficulty()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<QuizResponseDTO> findById(Long id) {
        return quizRepository.findById(id)
                .map(q -> {
                    Topic t = q.getTopic(); // safe inside transaction
                    TopicResponseDTO topicResponseDTO = new TopicResponseDTO(t.getId(), t.getName(), t.getDescription());
                    return new QuizResponseDTO(q.getId(), q.getTitle(), q.getDescription(), topicResponseDTO, q.getDifficulty());
                });
    }


//        public QuizResponseDTO findById(Long id) {
//            Topic t = quizRepository.findById(id).get().getTopic();
//            TopicResponseDTO topicResponseDTO = new TopicResponseDTO(t.getId(),t.getName(),t.getDescription());
//
//
//
//            return quizRepository.findById(id)
//                    .map(q-> new QuizResponseDTO(q.getId(),q.getTitle(),q.getDescription(),topicResponseDTO,q.getDifficulty()))
//                    .orElse(null);
//        }
}
