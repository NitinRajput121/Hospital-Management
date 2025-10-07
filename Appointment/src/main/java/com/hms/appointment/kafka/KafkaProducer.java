package com.hms.appointment.kafka;

import com.hms.appointment.dto.AppointmentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${kafka.topic.email-send}")
    private  String topic;


    public void appointmentCreatedEvent(AppointmentEvent event){

        System.out.println("Sending kafka payload message to stock ..............");
        kafkaTemplate.send(topic,event);
    }
}
