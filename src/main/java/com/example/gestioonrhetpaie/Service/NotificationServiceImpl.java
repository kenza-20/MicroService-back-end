package com.example.gestioonrhetpaie.Service;

import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Repository.INotificationRepository;
import com.example.gestioonrhetpaie.Service.NotificationService;
import com.example.gestioonrhetpaie.Service.SmsService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class    NotificationServiceImpl implements NotificationService {
    private final INotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final SmsService smsService; // Service SMS

    public NotificationServiceImpl(INotificationRepository notificationRepository, JavaMailSender mailSender, SmsService smsService) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.smsService = smsService;
    }

    @Override
    public Notification sendNotification(String recipient, String phoneNumber, String message) {
        if ((recipient == null || recipient.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
            System.out.println("⚠️ Aucun destinataire fourni !");
            throw new IllegalArgumentException("Veuillez fournir un email ou un numéro de téléphone.");
        }

        Notification notification = new Notification(recipient, phoneNumber, message);
        notificationRepository.save(notification);

        boolean emailSent = false;
        boolean smsSent = false;

        // Vérifie et envoie un email
        if (recipient != null && recipient.contains("@")) {
            sendEmail(recipient, "Nouvelle Notification", message);
            emailSent = true;
        }

        // Vérifie et envoie un SMS
        if (phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}")) {
            smsService.sendSms(phoneNumber, message);
            smsSent = true;
        }

        // Vérification si aucun envoi n'a eu lieu
        if (!emailSent && !smsSent) {
            System.out.println("⚠️ Destinataire non valide !");
        }

        return notification;
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom("sarrabenhamouda7@gmail.com");
            mailSender.send(message);
            System.out.println("✅ Email envoyé à " + to);
        } catch (MessagingException e) {
            System.out.println("❌ Erreur d'envoi de l'email : " + e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
