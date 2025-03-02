package com.example.gestioonrhetpaie.Service;

import com.twilio.rest.api.v2010.account.Message;
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

    @Async
    public CompletableFuture<Boolean> sendSms(String to, String message) {
        try {
            Message sms = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(twilioNumber),
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
