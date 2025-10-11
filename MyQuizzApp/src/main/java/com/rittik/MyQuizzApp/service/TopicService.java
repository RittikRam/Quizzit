package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll(){
        return topicRepository.findAll();

    }
    public Optional<Topic> findById(Long id){
        return topicRepository.findById(id);
    }
    public Topic createTopic(Topic topic) throws IllegalArgumentException{
       if(topicRepository.findByName(topic.getName()).isPresent()){
           throw new IllegalArgumentException("Topic with same name Exits");
       }
       return topicRepository.save(topic);
    }

    public Topic updateTopic(Long id, Topic newTopic)throws IllegalArgumentException{
        Topic existing = topicRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Topic not found"));

        existing.setName(newTopic.getName());
        existing.setDescription(newTopic.getDescription());
        return topicRepository.save(existing);
    }
}
