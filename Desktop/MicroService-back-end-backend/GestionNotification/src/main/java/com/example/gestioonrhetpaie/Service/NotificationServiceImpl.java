package com.example.gestioonrhetpaie.Service;

import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Entity.NotificationStatus;
import com.example.gestioonrhetpaie.Repository.INotificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final SmsService smsService; // Assume this is your SMS service

    @Autowired
    public NotificationServiceImpl(INotificationRepository notificationRepository, JavaMailSender mailSender, SmsService smsService) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.smsService = smsService;
    }

    @Override
    public Notification sendNotification(String recipient, String phoneNumber, String message, String serviceName) {
        // Validation: Ensure at least one of the recipient or phone number is provided
        if ((recipient == null || recipient.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
            throw new IllegalArgumentException("Recipient email or phone number must be provided.");
        }

        // Create and save the notification (status will be 'PENDING' by default)
        Notification notification = new Notification(recipient, phoneNumber, message, LocalDateTime.now());
        notification.setRecipient(recipient);
        notification.setContent(message);
        notification.setServiceName(serviceName);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        boolean emailSent = false;
        boolean smsSent = false;

        // Send email if recipient is a valid email
        if (recipient != null && recipient.contains("@")) {
            sendEmail(recipient, "New Notification", message);
            emailSent = true;
            notification.setSentAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.SENT);
        }

        // Send SMS if phone number is valid
        if (phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}")) {
            smsService.sendSms(phoneNumber, message);
            smsSent = true;
            notification.setSentAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.SENT);
        }

        // If no communication method was used, mark as failed
        if (!emailSent && !smsSent) {
            notification.setStatus(NotificationStatus.FAILED);
        }

        notificationRepository.save(notification);  // Update the notification in the repository

        return notification;
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom("your-email@example.com"); // Replace with your email
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    @Override
    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    @Override
    public List<Notification> getNotificationsByService(String serviceName) {
        return notificationRepository.findByServiceName(serviceName);
    }
}
