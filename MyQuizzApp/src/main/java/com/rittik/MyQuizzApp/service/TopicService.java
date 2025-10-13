package com.rittik.MyQuizzApp.service;

import com.rittik.MyQuizzApp.dto.TopicRequestDTO;
import com.rittik.MyQuizzApp.dto.TopicResponseDTO;
import com.rittik.MyQuizzApp.entity.Topic;
import com.rittik.MyQuizzApp.exception.DuplicateResourceException;
import com.rittik.MyQuizzApp.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
           throw new DuplicateResourceException("Topic with same name Exits "+topic.getName());
       }
       Topic t = Topic.builder()
               .name(topic.name)
               .description(topic.description)
               .build();
       Topic saved = topicRepository.save(t);
       return new TopicResponseDTO(saved.getId(),saved.getName(),saved.getDescription());
    }

    public TopicResponseDTO updateTopic(Long id, @Valid TopicRequestDTO topicRequestDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Topic not found"));
        if(topicRepository.existsByNameAndIdNot(topicRequestDTO.getName(),id)){
            throw new DuplicateResourceException("Topic name '" + topicRequestDTO.getName() + "' is already used");
        }
        topic.setName(topicRequestDTO.getName());
        topic.setDescription(topicRequestDTO.getDescription());
        Topic saved  = topicRepository.save(topic);
        return new TopicResponseDTO(saved.getId(),saved.getName(),saved.getDescription());
    }

}
