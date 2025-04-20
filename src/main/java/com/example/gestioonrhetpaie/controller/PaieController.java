package com.example.gestioonrhetpaie.controller;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.entities.Employee;
import com.example.gestioonrhetpaie.services.PaieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PaieController {
    @Autowired
    private PaieService paieService;

    @PostMapping("/calcul")
    public ResponseEntity<byte[]> genererBulletinDePaie(
            @RequestParam Long employeeId,
            @RequestParam int heuresTravaillees,
            @RequestParam double tauxHoraire,
            @RequestParam double prime,
            @RequestParam double deduction,
            @RequestParam double acompte,
            @RequestParam String email
    ) throws IOException {

        BulletinDePaie bulletin = new BulletinDePaie();
        bulletin.setEmployeeId(employeeId);
        bulletin.setHeuresTravaillees(heuresTravaillees);
        bulletin.setTauxHoraire(tauxHoraire);
        bulletin.setPrime(prime);
        bulletin.setDeduction(deduction);
        bulletin.setAcompte(acompte);


        // Calcul brut et net
        double salaireBrut = heuresTravaillees * tauxHoraire + prime;
        double salaireNet = salaireBrut - deduction - acompte;

        bulletin.setSalaireBrut(salaireBrut);
        bulletin.setSalaireNet(salaireNet);
        bulletin.setPeriode(LocalDate.now());

        // Appel à la méthode de génération PDF
        byte[] pdfData = paieService.genererPDF(bulletin, email); // méthode modifiée ci-dessous

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bulletin.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
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


    @PostMapping("/addEmployee")
    public Employee addEmployee(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email) {
       return  paieService.addEmploye(nom, prenom, email);
    }


}
