package com.example.gestioonrhetpaie.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import com.example.gestioonrhetpaie.Entity.Conge;
import com.example.gestioonrhetpaie.Entity.StatutConge;
import com.example.gestioonrhetpaie.Services.ICongeService;

@Tag(name = "Gestion Conge")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/conge")
@AllArgsConstructor
public class CongeRestController {
    private final ICongeService service;

    // Existing CRUD operations
    @PostMapping("/addOrUpdate")
    public ResponseEntity<Conge> addOrUpdate(@RequestBody Conge conge) {
        return ResponseEntity.ok(service.addOrUpdate(conge));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Conge>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById")
    public ResponseEntity<Conge> findById(@RequestParam Long id) {
        Conge conge = service.findById(id);
        return conge != null ? ResponseEntity.ok(conge) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // New endpoints

    // Employee requests leave
    @PostMapping("/request")
    public ResponseEntity<Conge> requestLeave(
            @RequestParam Long employeId,
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            @RequestParam String motif) {

        LocalDate startDate = LocalDate.parse(dateDebut);
        LocalDate endDate = LocalDate.parse(dateFin);

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }

        Conge conge = service.requestLeave(employeId, startDate, endDate, motif);
        return ResponseEntity.ok(conge);
    }

    // HR approves or rejects leave
    @PutMapping("/updateStatus")
    public ResponseEntity<Conge> updateLeaveStatus(
            @RequestParam Long congeId,
            @RequestParam StatutConge statut,
            @RequestParam(required = false) String hrComment) {

        try {
            Conge updatedConge = service.updateLeaveStatus(congeId, statut, hrComment);
            return ResponseEntity.ok(updatedConge);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

