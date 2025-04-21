package com.micro.backendmicro.services;

import com.micro.backendmicro.entities.Employe;
import com.micro.backendmicro.entities.EmployeEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmployeProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public EmployeProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmployeCreatedEvent(Employe employe) {
        EmployeEvent event = new EmployeEvent(employe.getId(), employe.getName(), employe.getEmail());
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
