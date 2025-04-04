package com.example.gestioonrhetpaie.repository;

import com.example.gestioonrhetpaie.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
