package com.example.gestioonrhetpaie.controller;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.entities.Employee;
import com.example.gestioonrhetpaie.services.PaieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paie")
public class PaieController {
    @Autowired
    private PaieService paieService;

    @PostMapping("/calcul")
    @Operation(summary = "Calcule le bulletin de paie d'un employé")
    public BulletinDePaie calculerPaie(@RequestParam Long employeeId,
                                       @RequestParam Double heuresTravaillees,
                                       @RequestParam Double tauxHoraire,
                                       @RequestParam Double prime,
                                       @RequestParam Double deduction,
                                       @RequestParam Double acompte,@RequestParam String email) {
        return paieService.calculerPaie(employeeId, heuresTravaillees, tauxHoraire, prime, deduction,acompte,email);
    }

    @GetMapping("/historique/{employeeId}")
    @Operation(summary = "Historique")
    public List<BulletinDePaie> historiquePaie(@PathVariable Long employeeId) {
        return paieService.historiquePaie(employeeId);
    }

    // Endpoint pour récupérer tous les bulletins
    @GetMapping("/all")
    public List<BulletinDePaie> findAll() {
        return paieService.findAll();
    }

    // Endpoint pour récupérer un bulletin par son id
    @GetMapping("/{id}")
    public Optional<BulletinDePaie> findById(@PathVariable Long id) {
        return paieService.findById(id);
    }

    @GetMapping("/allEmployees")
    public List<Employee> findAllEmployees() {
        return paieService.getAllEmployees();
    }




}
