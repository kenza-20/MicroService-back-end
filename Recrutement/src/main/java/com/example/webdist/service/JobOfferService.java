package com.example.webdist.service;

import com.example.webdist.dto.ApplicationRequest;
import com.example.webdist.dto.ApplicationResponse;
import com.example.webdist.entity.Application;
import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.User;
import com.example.webdist.repository.ApplicationRepository;
import com.example.webdist.repository.JobOfferRepository;
import com.example.webdist.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public JobOfferService(JobOfferRepository jobOfferRepository,
                           ApplicationRepository applicationRepository,
                           UserRepository userRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<JobOffer> getAllOffers() {
        return jobOfferRepository.findAll();
    }

    public JobOffer createOffer(JobOffer offer) {
        return jobOfferRepository.save(offer);
    }

    @Transactional
    public JobOffer updateOffer(Long id, JobOffer updatedOffer) {
        JobOffer existingOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        existingOffer.setTitle(updatedOffer.getTitle());
        existingOffer.setDescription(updatedOffer.getDescription());
        existingOffer.setCompany(updatedOffer.getCompany());
        existingOffer.setLocation(updatedOffer.getLocation());
        existingOffer.setSalary(updatedOffer.getSalary());

        return jobOfferRepository.save(existingOffer);
    }

    @Transactional
    public void deleteOffer(Long id) {
        JobOffer offer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        applicationRepository.deleteByJobOfferId(id);
        jobOfferRepository.delete(offer);
    }

    @Transactional
    public Application applyToOffer(Long jobId, ApplicationRequest request) {
        System.out.println("▶️ [POST] /api/joboffers/" + jobId + "/apply");
        System.out.println("📥 Reçu : " + request);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("🔐 Utilisateur connecté : " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.err.println("❌ Utilisateur non trouvé pour username : " + username);
                    return new IllegalArgumentException("Utilisateur non trouvé !");
                });

        JobOffer offer = jobOfferRepository.findById(jobId)
                .orElseThrow(() -> {
                    System.err.println("❌ Offre non trouvée pour l’ID : " + jobId);
                    return new IllegalArgumentException("Offre non trouvée !");
                });

        System.out.println("📌 Candidat : " + request.getFullName());
        System.out.println("📧 Email : " + request.getEmail());
        System.out.println("📎 CV : " + request.getCvUrl());

        Application application = new Application();
        application.setUser(user);
        application.setJobOffer(offer);
        application.setFullName(request.getFullName());
        application.setEmail(request.getEmail());
        application.setCvUrl(request.getCvUrl());
        application.setAppliedAt(LocalDateTime.now());
        application.setApplicationDate(LocalDateTime.now());

        Application saved = applicationRepository.save(application);
        System.out.println("✅ Candidature enregistrée avec ID : " + saved.getId());

        return saved;
    }

    public List<ApplicationResponse> getAllApplicationResponses() {
        return applicationRepository.findAll()
                .stream()
                .map(app -> new ApplicationResponse(
                        app.getFullName(),
                        app.getEmail(),
                        app.getCvUrl()
                ))
                .toList();
    }
}
