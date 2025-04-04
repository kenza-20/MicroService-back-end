package com.example.webdist.controller;

import com.example.webdist.dto.ApplicationRequest;
import com.example.webdist.dto.ApplicationResponse;
import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.Application;
import com.example.webdist.service.JobOfferService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.webdist.service.JobOfferService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/joboffers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping
    public List<JobOffer> getAllOffers() {
        return jobOfferService.getAllOffers();
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public JobOffer createOffer(@RequestBody JobOffer offer) {
        return jobOfferService.createOffer(offer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<JobOffer> updateOffer(
            @PathVariable Long id,
            @RequestBody JobOffer offer) {
        return ResponseEntity.ok(jobOfferService.updateOffer(id, offer));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        try {
            jobOfferService.deleteOffer(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting job offer: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/apply")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApplicationResponse> applyToOffer(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequest request) {

        Application application = jobOfferService.applyToOffer(id, request);

        ApplicationResponse response = new ApplicationResponse();
        response.setFullName(application.getFullName());
        response.setEmail(application.getEmail());
        response.setCvUrl(application.getCvUrl());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> applications = jobOfferService.getAllApplicationResponses();
        return ResponseEntity.ok(applications);
    }


}
