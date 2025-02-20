package com.example.gestioonrhetpaie.repository;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulletinDePaieRepository extends JpaRepository<BulletinDePaie, Long> {
    List<BulletinDePaie> findByEmployeeId(Long employeeId);
}
