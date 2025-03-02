package com.example.gestioonrhetpaie.Service;

import com.example.gestioonrhetpaie.Entity.Notification;
import java.util.List;

public interface NotificationService {
    Notification sendNotification(String recipient, String phoneNumber, String message);
    List<Notification> getAllNotifications();
}
