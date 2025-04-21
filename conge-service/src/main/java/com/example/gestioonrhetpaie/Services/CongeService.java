package com.example.gestioonrhetpaie.Services;

import com.example.gestioonrhetpaie.Entity.Conge;
import com.example.gestioonrhetpaie.Entity.StatutConge;
import com.example.gestioonrhetpaie.Repositories.CongeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CongeService implements ICongeService {
    private final CongeRepository repository;

    @Override
    public Conge addOrUpdate(Conge conge) {
        return repository.save(conge);
    }

    @Override
    public List<Conge> findAll() {
        return repository.findAll();
    }

    @Override
    public Conge findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Employee requests leave
    public Conge requestLeave(Long employeId, LocalDate dateDebut, LocalDate dateFin, String motif) {
        // Check if start date is after end date
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        // Check if the requested leave period overlaps with an existing approved leave
        List<Conge> existingConges = repository.findAll();
        for (Conge existingConge : existingConges) {
            if (existingConge.getEmployeId().equals(employeId)) {
                if ((dateDebut.isEqual(existingConge.getDateDebut()) || dateDebut.isAfter(existingConge.getDateDebut())) &&
                        (dateDebut.isBefore(existingConge.getDateFin()) || dateDebut.isEqual(existingConge.getDateFin())) ||
                        (dateFin.isEqual(existingConge.getDateFin()) || dateFin.isBefore(existingConge.getDateFin())) &&
                                (dateFin.isAfter(existingConge.getDateDebut()) || dateFin.isEqual(existingConge.getDateDebut()))) {
                    throw new IllegalStateException("Requested leave period overlaps with existing leave.");
                }
            }
        }

        Conge conge = new Conge();
        conge.setEmployeId(employeId);
        conge.setDateDebut(dateDebut);
        conge.setDateFin(dateFin);
        conge.setMotif(motif);
        conge.setStatut(StatutConge.PENDING); // Default status
        conge.setDateRequest(LocalDate.now()); // Set the request date

        return repository.save(conge);
    }

    // HR approves or rejects leave
    public Conge updateLeaveStatus(Long congeId, StatutConge statut, String hrComment) {
        Conge conge = repository.findById(congeId).orElseThrow(() -> new RuntimeException("Conge not found"));


//        if (conge.getStatut() != StatutConge.PENDING) {
//            throw new IllegalStateException("Leave request already processed");
//        }

        // Validate HR comment and response date
        if (statut == StatutConge.APPROVED && hrComment == null) {
            throw new IllegalArgumentException("HR comment is required for approved requests.");
        }

        conge.setStatut(statut);
        conge.setHrComment(hrComment);
        conge.setResponseDate(LocalDate.now()); // Set the response date

        return repository.save(conge);
    }
}
