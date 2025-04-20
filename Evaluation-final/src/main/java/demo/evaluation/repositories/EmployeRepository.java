package demo.evaluation.repositories;

import demo.evaluation.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe,Long> {

}
