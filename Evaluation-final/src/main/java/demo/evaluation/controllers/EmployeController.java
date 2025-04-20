package demo.evaluation.controllers;

import demo.evaluation.entities.Employe;
import demo.evaluation.services.IEmployeService;
import jakarta.persistence.TableGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employe microservice")
@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final IEmployeService employeService;

    public EmployeController(IEmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping("/retrieve-all-employes")
    public List<Employe> getEmployes() {
        return employeService.retrieveAllEmploye();
    }

    @GetMapping("/retrieve-employe/{employe-id}")
    public Employe retrieveEmploye(@PathVariable("employe-id") Long id) {
        return employeService.retrieveEmploye(id);
    }

    @PostMapping("/add-employe")
    public Employe addEmploye(@RequestBody Employe f) {
        return employeService.addEmploye(f);
    }

    @DeleteMapping("/remove-employe/{employe-id}")
    public void removeEmploye(@PathVariable("employe-id") Long id) {
        employeService.removeEmploye(id);
    }

    @PutMapping("/modify-employe")
    public Employe modifyEmploye(@RequestBody Employe f) {
        return employeService.modifyEmploye(f);
    }
}