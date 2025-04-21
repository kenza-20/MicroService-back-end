package com.micro.backendmicro.services;

import com.micro.backendmicro.entities.Employe;
import com.micro.backendmicro.entities.EmployeEvent;
import com.micro.backendmicro.repositories.EmployeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeImpService implements IEmployeService{
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PDFService pdfService;

    @Autowired
    private EmployeProducer employeProducer;

    @Override
    public List<Employe> retrieveAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public Employe addEmploye(Employe f) {
        Employe addedEmploye = employeRepository.save(f);
        employeProducer.sendEmployeCreatedEvent(addedEmploye);
        sendEmployeEmail(addedEmploye, "Added Employee", "Employee details: " + addedEmploye.toString());
        try {
            pdfService.generateEmployePDF(addedEmploye);
        } catch (IOException e) {
            log.error("Error generating PDF for employee " + addedEmploye.getId(), e);
        }
        return addedEmploye;
    }

    @Override
    public Employe modifyEmploye(Employe f) {
        return employeRepository.save(f);
    }

    @Override
    public Employe retrieveEmploye(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    @Override
    public void removeEmploye(Long id) {
        employeRepository.deleteById(id);
    }

    private void sendEmployeEmail(Employe employe, String subject, String message) {
        String recipientEmail = "belhadj.amir@esprit.tn"; // Change this as needed
        emailService.sendEmail(recipientEmail, subject, message);
    }



}
