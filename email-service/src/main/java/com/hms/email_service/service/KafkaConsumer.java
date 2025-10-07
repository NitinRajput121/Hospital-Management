package com.hms.email_service.service;

import com.hms.email_service.dto.EmailEvent;
import com.hms.email_service.dto.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EmailService emailService;

    @Value("${kafka.topic.email-send}")
    private String topic;

    @KafkaListener(topics = "${kafka.topic.email-send}", groupId = "email-group")
    public void handle(EmailEvent event) {
        // Build the mail body using event data
        MailBody mailBody = MailBody.builder()
                .to(event.getEmail())
                .subject("Appointment Confirmation")
                .text("Your appointment is confirmed for: " + event.getAppointmentTime())
                .build();

        // Send email
        emailService.sendSimpleMessage(mailBody);
    }
}
