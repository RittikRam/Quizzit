package com.rittik.MyQuizzApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TopicRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100)
    public String name;

    @Size(max = 500)
    public String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
