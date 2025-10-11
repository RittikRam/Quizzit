package com.rittik.MyQuizzApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
