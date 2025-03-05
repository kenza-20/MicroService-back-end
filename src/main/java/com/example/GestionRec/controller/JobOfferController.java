package com.example.webdist.controller;

import com.example.webdist.entity.JobOffer;
import com.example.webdist.entity.Application;
import com.example.webdist.service.JobOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200") // Change l'URL selon ton environnement
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
    public ResponseEntity<JobOffer> updateOffer(@PathVariable Long id, @RequestBody JobOffer offer) {
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Application> applyToOffer(
            @PathVariable Long id,
            @RequestBody String coverLetter) {
        return ResponseEntity.ok(jobOfferService.applyToOffer(id, coverLetter));
    }
}
