package com.example.gestioonrhetpaie.controller;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.services.PaieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaieController {
    @Autowired
    private PaieService paieService;

    @PostMapping("/calcul")
    @Operation(summary = "Calcule le bulletin de paie d'un employé")
    public BulletinDePaie calculerPaie(@RequestParam Long employeeId,
                                       @RequestParam Double heuresTravaillees,
                                       @RequestParam Double tauxHoraire,
                                       @RequestParam Double prime,
                                       @RequestParam Double deduction) {
        return paieService.calculerPaie(employeeId, heuresTravaillees, tauxHoraire, prime, deduction);
    }

    @GetMapping("/historique/{employeeId}")
    @Operation(summary = "Historique")
    public List<BulletinDePaie> historiquePaie(@PathVariable Long employeeId) {
        return paieService.historiquePaie(employeeId);
    }
}
