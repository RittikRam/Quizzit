package com.rittik.MyQuizzApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(length = 500)
    private String description;
}
