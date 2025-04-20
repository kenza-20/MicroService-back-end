package com.example.webdist.service;

import com.example.webdist.dto.ApplicationRequest;
import com.example.webdist.dto.ApplicationResponse;
import com.example.webdist.entity.Application;
import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.User;
import com.example.webdist.repository.ApplicationRepository;
import com.example.webdist.repository.JobOfferRepository;
import com.example.webdist.repository.UserRepository;
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
        offer.setCreatedAt(LocalDateTime.now());
        return jobOfferRepository.save(offer);
    }

    @Transactional
    public JobOffer updateOffer(Long id, JobOffer updatedOffer) {
        JobOffer existing = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        existing.setTitle(updatedOffer.getTitle());
        existing.setDescription(updatedOffer.getDescription());
        existing.setCompany(updatedOffer.getCompany());
        existing.setLocation(updatedOffer.getLocation());
        existing.setSalary(updatedOffer.getSalary());
        existing.setUpdatedAt(LocalDateTime.now());

        return jobOfferRepository.save(existing);
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
        JobOffer offer = jobOfferRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        // Utilisateur fictif (car plus d'auth) → on crée un user de base
        User user = userRepository.findByUsername(request.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(request.getEmail());
                    newUser.setPassword("default"); // juste pour éviter null
                    return userRepository.save(newUser);
                });

        Application app = new Application();
        app.setUser(user);
        app.setJobOffer(offer);
        app.setFullName(request.getFullName());
        app.setEmail(request.getEmail());
        app.setCvUrl(request.getCvUrl());
        app.setAppliedAt(LocalDateTime.now());
        app.setApplicationDate(LocalDateTime.now());

        return applicationRepository.save(app);
    }

    public List<ApplicationResponse> getAllApplicationResponses() {
        return applicationRepository.findAll().stream()
                .map(app -> new ApplicationResponse(
                        app.getFullName(),
                        app.getEmail(),
                        app.getCvUrl()
                ))
                .toList();
    }
}
