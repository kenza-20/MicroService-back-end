package com.example.gestioonrhetpaie.Service;

import org.springframework.stereotype.Service;
import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Repository.INotificationRepository;
import java.util.List;

@Service
    public class NotificationServiceImpl implements NotificationService {
        private  INotificationRepository repository;

        public NotificationServiceImpl(INotificationRepository repository) {
            this.repository = repository;
        }

        @Override
        public Notification sendNotification(String recipient, String message) {
            Notification notification = new Notification(recipient, message);
            return repository.save(notification);
        }

        @Override
        public List<Notification> getAllNotifications() {
            return repository.findAll();
        }
    }

