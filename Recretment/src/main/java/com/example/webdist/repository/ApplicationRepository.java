package com.example.webdist.repository;

import com.example.webdist.entity.Application;
import com.example.webdist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByJobOfferId(Long jobOfferId);

    @Modifying
    @Transactional
    void deleteByJobOfferId(Long jobOfferId);
} 