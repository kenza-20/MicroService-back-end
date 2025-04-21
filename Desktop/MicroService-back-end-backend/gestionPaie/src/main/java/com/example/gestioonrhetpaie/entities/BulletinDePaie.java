package com.example.gestioonrhetpaie.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class BulletinDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private LocalDate periode;
    private Double salaireBrut;
    private Double salaireNet;
    private String pdfPath;
    private Double acompte;
    private int heuresTravaillees;

    private Double tauxHoraire;
    private Double prime;

    private Double deduction;

    public int getHeuresTravaillees() {
        return heuresTravaillees;
    }

    public void setHeuresTravaillees(int heuresTravaillees) {
        this.heuresTravaillees = heuresTravaillees;
    }

    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public Double getPrime() {
        return prime;
    }

    public void setPrime(Double prime) {
        this.prime = prime;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getPeriode() {
        return periode;
    }

    public void setPeriode(LocalDate periode) {
        this.periode = periode;
    }

    public Double getSalaireBrut() {
        return salaireBrut;
    }

    public void setSalaireBrut(Double salaireBrut) {
        this.salaireBrut = salaireBrut;
    }

    public Double getSalaireNet() {
        return salaireNet;
    }

    public void setSalaireNet(Double salaireNet) {
        this.salaireNet = salaireNet;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Double getAcompte() {
        return acompte;
    }

    public void setAcompte(Double acompte) {
        this.acompte = acompte;
    }
}