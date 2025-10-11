package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.Question;
import com.rittik.MyQuizzApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
