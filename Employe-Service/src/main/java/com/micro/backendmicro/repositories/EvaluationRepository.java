package com.micro.backendmicro.repositories;



import com.micro.backendmicro.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEmployeId(Long employeId);
}
