package com.example.gestioonrhetpaie.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String type;

    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

    private String serviceName;

    public Notification() {

    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

    // Add a constructor to match the parameters you're passing
    public Notification(String recipient, String phoneNumber, String message, LocalDateTime sentAt) {
        this.recipient = recipient;
        this.content = message;
        this.sentAt = sentAt;
        this.type = determineNotificationType(phoneNumber); // Determine if it should be SMS or email based on the phoneNumber
        this.status = NotificationStatus.PENDING; // Default status
    }

    private String determineNotificationType(String phoneNumber) {
        // Add logic to determine notification type based on the recipient (email or phone number)
        if (phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}")) {
            return "SMS";
        } else if (recipient != null && recipient.contains("@")) {
            return "EMAIL";
        }
        return "UNKNOWN";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCreatedAt(LocalDateTime now) {
    }
}
