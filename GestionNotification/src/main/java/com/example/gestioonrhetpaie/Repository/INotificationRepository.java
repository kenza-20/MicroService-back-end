package com.example.gestioonrhetpaie.Repository;

import com.example.gestioonrhetpaie.Entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gestioonrhetpaie.Entity.Notification;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipient(String recipient);

    List<Notification> findByServiceName(String serviceName);
}