package com.rittik.MyQuizzApp.dto;

public class    TopicResponseDTO {

    public Long id;
    public String name;
    public String description;

    public TopicResponseDTO() {    }

    public TopicResponseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
