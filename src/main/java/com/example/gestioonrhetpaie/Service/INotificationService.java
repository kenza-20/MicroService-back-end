package com.example.gestioonrhetpaie.Service;

import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Entity.NotificationStatus;

import java.util.List;

public interface INotificationService {

    // Send a notification (either email or SMS)
    Notification sendNotification(String recipient, String phoneNumber, String message, String serviceName);

    // Retrieve all notifications
    List<Notification> getAllNotifications();

    // Retrieve notifications by status
    List<Notification> getNotificationsByStatus(NotificationStatus status);

    // Retrieve notifications by recipient (email or phone)
    List<Notification> getNotificationsByRecipient(String recipient);

    // Retrieve notifications by service name
    List<Notification> getNotificationsByService(String serviceName);
}
