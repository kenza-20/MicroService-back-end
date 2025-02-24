package com.example.gestioonrhetpaie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gestioonrhetpaie.Entity.Notification;
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
}