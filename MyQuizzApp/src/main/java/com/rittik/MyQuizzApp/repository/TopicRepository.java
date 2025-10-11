package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
