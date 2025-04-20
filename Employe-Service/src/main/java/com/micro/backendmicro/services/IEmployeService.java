package com.micro.backendmicro.services;

import com.micro.backendmicro.entities.Employe;

import java.util.List;

public interface IEmployeService {
    List<Employe> retrieveAllEmploye();

    Employe addEmploye(Employe f);

    Employe modifyEmploye(Employe f);

    Employe retrieveEmploye(Long id);

    void removeEmploye(Long id);
}
