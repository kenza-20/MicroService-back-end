package com.micro.backendmicro.controllers;


import com.micro.backendmicro.entities.Employe;
import com.micro.backendmicro.services.IEmployeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name= "employe microservice")
@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    IEmployeService employeService;

    @GetMapping("/retrieve-all-employes")
    public List<Employe> getEmployes() {
        List<Employe> listEmployes = employeService.retrieveAllEmploye();
        return listEmployes;
    }
    // http://localhost:8089/tpfoyer/foyer/retrieve-foyer/8
    @GetMapping("/retrieve-employe/{employe-id}")
    public Employe retrieveEmploye(@PathVariable("employe-id") Long id) {
        Employe employe = employeService.retrieveEmploye(id);
        return employe;
    }

    @PostMapping("/add-employe")
    public Employe addEmploye(@RequestBody Employe f) {
        Employe employe = employeService.addEmploye(f);
        return employe;
    }
    // http://localhost:8089/tpfoyer/foyer/remove-foyer/{foyer-id}
    @DeleteMapping("/remove-employe/{employe-id}")
    public void removeEmploye(@PathVariable("employe-id") Long id) {
        employeService.removeEmploye(id);
    }
    // http://localhost:8089/tpfoyer/foyer/modify-foyer

    @PutMapping("/modify-employe")
    public Employe modifyEmploye(@RequestBody Employe f) {
        return employeService.modifyEmploye(f);

    }
}
