package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
