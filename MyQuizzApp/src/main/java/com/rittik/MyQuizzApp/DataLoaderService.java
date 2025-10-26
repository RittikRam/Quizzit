package com.rittik.MyQuizzApp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rittik.MyQuizzApp.dto.QuestionLoadDTO;
import com.rittik.MyQuizzApp.dto.OptionLoadDTO;
import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import com.rittik.MyQuizzApp.entity.Option;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.repository.QuestionRepository;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DataLoaderService implements CommandLineRunner {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public DataLoaderService(TopicRepository topicRepository, QuestionRepository questionRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (questionRepository.count() > 0) {
            System.out.println("--- Question Bank is already populated. Skipping import. ---");
            return;
        }
        System.out.println("--- Starting Question Bank Import via JSON Mapper ---");

        // 1. Ensure the default Topic exists
        Topic javaTopic = topicRepository.findByName("Java Programming")
                .orElseGet(() -> {
                    Topic newTopic = Topic.builder()
                            .name("Java Programming")
                            .description("Core and Advanced Java Concepts")
                            .build();
                    return topicRepository.save(newTopic);
                });

        // 2. Load the file using ObjectMapper
        // Assuming file is named 'qustn.txt' in resources and contains JSON
        Resource resource = resourceLoader.getResource("classpath:qustn.txt");

        List<QuestionLoadDTO> loadedQuestions = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<QuestionLoadDTO>>() {}
        );

        List<Question> questionsToSave = new ArrayList<>();
        AtomicLong counter = new AtomicLong(0);

        // 3. Map DTOs to JPA Entities
        for (QuestionLoadDTO loadDto : loadedQuestions) {
            // Stagger difficulty: 0=EASY, 1=MEDIUM, 2=HARD
            DifficultyLevel difficulty = switch ((int) (counter.incrementAndGet() % 3)) {
                case 1 -> DifficultyLevel.MEDIUM;
                case 2 -> DifficultyLevel.HARD;
                default -> DifficultyLevel.EASY;
            };

            Question question = Question.builder()
                    .text(loadDto.getText())
                    .topic(javaTopic)
                    .difficultyLevel(difficulty) // Assigned the staggered difficulty
                    .options(new ArrayList<>())
                    .build();

            List<Option> options = new ArrayList<>();
            for (OptionLoadDTO optionDto : loadDto.getOptions()) {
                Option option = Option.builder()
                        .text(optionDto.getText())
                        .isCorrect(optionDto.isCorrect())
                        .question(question)
                        .build();
                options.add(option);
            }

            question.setOptions(options);
            questionsToSave.add(question);
        }

        // 4. Save all Questions
        questionRepository.saveAll(questionsToSave);
        System.out.println("--- Successfully imported " + questionsToSave.size() + " questions via JSON! ---");
    }
}