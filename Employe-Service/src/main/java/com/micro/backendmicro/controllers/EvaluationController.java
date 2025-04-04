package com.micro.backendmicro.controllers;


        import com.micro.backendmicro.entities.Evaluation;
        import com.micro.backendmicro.services.EvaluationService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationService.createEvaluation(evaluation);
    }

    @GetMapping("/employe/{id}")
    public List<Evaluation> getEvaluationsByEmploye(@PathVariable Long id) {
        return evaluationService.getEvaluationsByEmploye(id);
    }

    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }
    @GetMapping("/employe/{id}/grade")
    public int getEmployeGrade(@PathVariable Long id) {
        return evaluationService.getEmployeById(id).getGrade();
    }

}
