package com.micro.backendmicro.services;

import com.micro.backendmicro.entities.Employe;
import com.micro.backendmicro.entities.Evaluation;
import com.micro.backendmicro.repositories.EmployeRepository;
import com.micro.backendmicro.repositories.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EmployeRepository employeRepository;

    public Evaluation createEvaluation(Evaluation evaluation) {
        Evaluation savedEvaluation = evaluationRepository.save(evaluation);

        // Après chaque évaluation, on met à jour le grade de l'employé
        updateGradeFromEvaluations(evaluation.getEmploye().getId());

        return savedEvaluation;
    }

    public List<Evaluation> getEvaluationsByEmploye(Long employeId) {
        return evaluationRepository.findByEmployeId(employeId);
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Employe updateGradeFromEvaluations(Long employeId) {
        List<Evaluation> evaluations = evaluationRepository.findByEmployeId(employeId);
        if (evaluations.isEmpty()) throw new RuntimeException("Aucune évaluation trouvée");

        double avgScore = evaluations.stream()
                .mapToInt(Evaluation::getPerformanceScore)
                .average()
                .orElse(0.0);

        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        employe.setGrade((int) Math.round(avgScore)); // Tu peux changer en fonction du système de notation
        return employeRepository.save(employe);
    }

    public Employe getEmployeById(Long employeId) {
        return employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }
}
