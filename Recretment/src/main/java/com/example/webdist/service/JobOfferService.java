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

    // ✅ Méthode : récupérer toutes les offres
    public List<JobOffer> getAllOffers() {
        return jobOfferRepository.findAll();
    }

    // ✅ Méthode : créer une offre
    public JobOffer createOffer(JobOffer offer) {
        return jobOfferRepository.save(offer);
    }

    // ✅ Méthode : modifier une offre
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


    // ✅ Méthode : supprimer une offre
    @Transactional
    public void deleteOffer(Long id) {
        JobOffer offer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        // Supprimer les candidatures liées
        applicationRepository.deleteByJobOfferId(id);

        jobOfferRepository.delete(offer);
    }

    // ✅ Méthode : postuler à une offre
    @Transactional
    public Application applyToOffer(Long jobId, ApplicationRequest request) {

        // 🔍 Récupérer l'offre à partir de l'ID
        JobOffer offer = jobOfferRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        // 🔐 Récupérer l'utilisateur connecté via le SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("🔐 Utilisateur connecté : " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 🧾 Debug des données reçues depuis le corps de la requête
        System.out.println("📄 Candidat : " + request.getFullName());
        System.out.println("📧 Email : " + request.getEmail());
        System.out.println("📎 CV : " + request.getCvUrl());

        // 📝 Créer une nouvelle entité de candidature
        Application application = new Application();
        application.setUser(user);
        application.setJobOffer(offer);
        application.setFullName(request.getFullName());
        application.setEmail(request.getEmail());
        application.setCvUrl(request.getCvUrl());
        application.setAppliedAt(LocalDateTime.now()); // ou application.setApplicationDate(...) si nécessaire

        // 💾 Enregistrer dans la base
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
