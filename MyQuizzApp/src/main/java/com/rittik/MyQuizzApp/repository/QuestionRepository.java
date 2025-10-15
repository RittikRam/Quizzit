package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.DifficultyLevel;
import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM questions q WHERE q.topic_id = :topicId AND q.difficulty_level = :difficultyLevel ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Question> findRandomQuestionsByTopicAndDifficulty(Long topicId, DifficultyLevel difficultyLevel, int count);
}
