package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.TopicRequestDTO;
import com.rittik.MyQuizzApp.dto.TopicResponseDTO;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<TopicResponseDTO> findAll(){
        return topicRepository.findAll()
                .stream()
                .map(t->new TopicResponseDTO(t.getId(),t.getName(),t.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<TopicResponseDTO> findById(Long id){
        return topicRepository.findById(id)
                .map(t->new TopicResponseDTO(t.getId(),t.getName(),t.getDescription()));
    }
    public TopicResponseDTO createTopic(TopicRequestDTO topic) throws IllegalArgumentException{
       if(topicRepository.findByName(topic.getName()).isPresent()){
           throw new IllegalArgumentException("Topic with same name Exits");
       }
       Topic t = Topic.builder()
               .name(topic.name)
               .description(topic.description)
               .build();
       Topic saved = topicRepository.save(t);
       return new TopicResponseDTO(saved.getId(),saved.getName(),saved.getDescription());
    }

//    public Topic updateTopic(Long id, Topic newTopic)throws IllegalArgumentException{
//        Topic existing = topicRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("Topic not found"));
//
//        existing.setName(newTopic.getName());
//        existing.setDescription(newTopic.getDescription());
//        return topicRepository.save(existing);
//    }
}
