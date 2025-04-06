package com.example.gestioonrhetpaie.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String twilioNumber;

    // Async method to send SMS and handle failures
    @Async
    public CompletableFuture<Boolean> sendSms(String to, String message) {
        try {
            if (to == null || to.isEmpty() || message == null || message.isEmpty()) {
                log.error("Invalid phone number or message content.");
                return CompletableFuture.completedFuture(false);
            }

            // Make sure Twilio phone number and recipient phone number are not null/empty
            PhoneNumber toPhoneNumber = new PhoneNumber(to);
            PhoneNumber fromPhoneNumber = new PhoneNumber(twilioNumber);

            Message sms = Message.creator(
                    toPhoneNumber,
                    fromPhoneNumber,
                    message
            ).create();

            log.info("SMS sent successfully to {}. Message SID: {}", to, sms.getSid());
            return CompletableFuture.completedFuture(true);

        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", to, e.getMessage(), e);
            return CompletableFuture.completedFuture(false);
        }
    }
}
