package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    public List<Quiz> findByTopicId(Long topicId);
    Optional<Object> findByTitle(String title);
}
