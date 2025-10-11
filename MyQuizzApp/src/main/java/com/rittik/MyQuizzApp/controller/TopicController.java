package com.rittik.MyQuizzApp.controller;


import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<Topic> getTopics(){
        return topicService.findAll();
    }

    @GetMapping("{id}")
    public Topic getTopicById(@PathVariable Long id){
        return topicService.findById(id)
                .orElseThrow(()->new RuntimeException("Topic not Found"));
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic){
        return topicService.createTopic(topic);
    }


}