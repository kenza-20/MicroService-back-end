package com.example.gestioonrhetpaie.Controler;

import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Service.NotificationServiceImpl;
import com.example.gestioonrhetpaie.Entity.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    // Endpoint to send a notification (email or SMS)
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(
            @RequestParam String recipient,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam String message,
            @RequestParam(defaultValue = "DefaultService") String serviceName) {

        // Send notification (email or SMS)
        Notification notification = notificationService.sendNotification(recipient, phoneNumber, message, serviceName);

        return ResponseEntity.ok(notification);  // Return the notification that was created
    }

    // Endpoint to get all notifications
    @GetMapping("/ALL")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Endpoint to get notifications by status
    @GetMapping("/status")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(@RequestParam NotificationStatus status) {
        List<Notification> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }

    // Endpoint to get notifications by recipient
    @GetMapping("/recipient")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(@RequestParam String recipient) {
        List<Notification> notifications = notificationService.getNotificationsByRecipient(recipient);
        return ResponseEntity.ok(notifications);
    }

    // Endpoint to get notifications by service name
    @GetMapping("/service")
    public ResponseEntity<List<Notification>> getNotificationsByService(@RequestParam String serviceName) {
        List<Notification> notifications = notificationService.getNotificationsByService(serviceName);
        return ResponseEntity.ok(notifications);
    }
}
