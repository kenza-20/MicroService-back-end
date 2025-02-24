package com.example.gestioonrhetpaie.Controler;

import com.example.gestioonrhetpaie.Service.NotificationService;
import org.springframework.web.bind.annotation.*;
import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Service.NotificationService;
import com.example.gestioonrhetpaie.Service.NotificationServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public Notification sendNotification(@RequestParam String recipient, @RequestParam String message) {
        return service.sendNotification(recipient, message);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return service.getAllNotifications();
    }
}
