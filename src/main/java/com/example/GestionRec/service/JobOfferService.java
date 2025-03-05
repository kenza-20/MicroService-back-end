package com.example.webdist.service;

import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.Application;
import com.example.webdist.entity.User;
import com.example.webdist.repository.JobOfferRepository;
import com.example.webdist.repository.ApplicationRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobOfferService {
    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    public JobOfferService(
            JobOfferRepository jobOfferRepository,
            ApplicationRepository applicationRepository,
            UserService userService) {
        this.jobOfferRepository = jobOfferRepository;
        this.applicationRepository = applicationRepository;
        this.userService = userService;
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
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
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
            .orElseThrow(() -> new RuntimeException("Job offer not found"));
            
        // First delete all applications for this job offer
        applicationRepository.deleteByJobOfferId(id);
        
        // Then delete the job offer
        jobOfferRepository.delete(offer);
    }

    @Transactional
    public Application applyToOffer(Long offerId, String coverLetter) {
        JobOffer offer = jobOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
                
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        
        Application application = new Application();
        application.setUser(user);
        application.setJobOffer(offer);
        application.setCoverLetter(coverLetter);
        application.setApplicationDate(LocalDateTime.now());
        
        return applicationRepository.save(application);
    }
}
