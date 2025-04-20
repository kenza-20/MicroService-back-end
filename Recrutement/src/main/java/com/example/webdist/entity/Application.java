package com.example.webdist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_offer_id")
    @JsonIgnore  // 🛡️ Évite la récursion infinie dans le JSON
    private JobOffer jobOffer;

    @Column(nullable = true)
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    private String fullName;
    private String email;
    private String cvUrl;

    @Column(nullable = true)
    private LocalDateTime applicationDate;

    private LocalDateTime appliedAt;
}
