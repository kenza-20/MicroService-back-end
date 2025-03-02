package com.example.gestioonrhetpaie.Controler;

import com.example.gestioonrhetpaie.Service.NotificationService;
import com.example.gestioonrhetpaie.Entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public Notification sendNotification(@RequestParam String recipient,
                                         @RequestParam(required = false) String phoneNumber,
                                         @RequestParam String message) {
        return service.sendNotification(recipient, phoneNumber, message);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return service.getAllNotifications();
    }
}
