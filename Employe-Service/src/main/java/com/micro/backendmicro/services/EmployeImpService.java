package com.micro.backendmicro.services;

import com.micro.backendmicro.entities.Employe;
import com.micro.backendmicro.repositories.EmployeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeImpService implements IEmployeService{
    @Autowired
    EmployeRepository employeRepository;

    @Override
    public List<Employe> retrieveAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public Employe addEmploye(Employe f) {
        return employeRepository.save(f);
    }

    @Override
    public Employe modifyEmploye(Employe f) {
        return employeRepository.save(f);
    }

    @Override
    public Employe retrieveEmploye(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    @Override
    public void removeEmploye(Long id) {
        employeRepository.deleteById(id);
    }

}
