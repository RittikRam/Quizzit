package com.rittik.MyQuizzApp.controller;


import com.rittik.MyQuizzApp.dto.TopicRequestDTO;
import com.rittik.MyQuizzApp.dto.TopicResponseDTO;
import com.rittik.MyQuizzApp.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<TopicResponseDTO> getTopics(){
        return topicService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long id){
        return topicService.findById(id)
                .map(ResponseEntity :: ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody TopicRequestDTO topic){
        TopicResponseDTO created = topicService.createTopic(topic);
        return ResponseEntity.status(201).body(created);
    }


}