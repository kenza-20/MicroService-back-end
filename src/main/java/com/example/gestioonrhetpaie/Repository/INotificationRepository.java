package com.example.gestioonrhetpaie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gestioonrhetpaie.Entity.Notification;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

}