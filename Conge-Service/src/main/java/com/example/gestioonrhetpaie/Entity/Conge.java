package com.example.gestioonrhetpaie.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CONGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "employee_id", nullable = false)
//    private Employe employe;
//
    private Long employeId;

    private LocalDate dateRequest;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;

    @Enumerated(EnumType.STRING)
    private StatutConge statut = StatutConge.PENDING;  // Default: Pending

    private String hrComment; // HR feedback
    private LocalDate responseDate; // When HR responded

}

