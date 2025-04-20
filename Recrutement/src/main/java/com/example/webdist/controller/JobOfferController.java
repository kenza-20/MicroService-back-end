package com.example.webdist.controller;

import com.example.webdist.dto.ApplicationRequest;
import com.example.webdist.dto.ApplicationResponse;
import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.Application;
import com.example.webdist.service.JobOfferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public JobOffer createOffer(@RequestBody JobOffer offer) {
        return jobOfferService.createOffer(offer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateOffer(@PathVariable Long id, @RequestBody JobOffer offer) {
        return ResponseEntity.ok(jobOfferService.updateOffer(id, offer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        try {
            jobOfferService.deleteOffer(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur suppression : " + e.getMessage());
        }
    }

    @PostMapping("/{id}/apply")
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
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> applications = jobOfferService.getAllApplicationResponses();
        return ResponseEntity.ok(applications);
    }
}
