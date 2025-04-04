package com.micro.backendmicro.repositories;

import com.micro.backendmicro.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe,Long> {

}
