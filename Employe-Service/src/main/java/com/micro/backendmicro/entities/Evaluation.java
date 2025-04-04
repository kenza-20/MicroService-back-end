package com.micro.backendmicro.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    Employe employe;

    String type; // AUTO_EVALUATION or MANAGER_FEEDBACK

    int performanceScore;

    String comments;

    LocalDate evaluationDate;

    String competencies; // Could be JSON or comma-separated for now
}
