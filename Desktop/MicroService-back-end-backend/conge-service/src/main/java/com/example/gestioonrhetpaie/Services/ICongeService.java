package com.example.gestioonrhetpaie.Services;

import com.example.gestioonrhetpaie.Entity.Conge;
import com.example.gestioonrhetpaie.Entity.StatutConge;

import java.time.LocalDate;
import java.util.List;

public interface ICongeService {


    Conge addOrUpdate(Conge conge);
    List<Conge> findAll();
    Conge findById(Long id);
    void deleteById(Long id);

    // New Methods
    Conge requestLeave(Long employeId, LocalDate dateDebut, LocalDate dateFin, String motif);
    Conge updateLeaveStatus(Long congeId, StatutConge statut, String hrComment);
}
