package com.example.gestioonrhetpaie.services;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.entities.Employee;
import com.example.gestioonrhetpaie.repository.BulletinDePaieRepository;
import com.example.gestioonrhetpaie.repository.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;


import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaieService {
    @Autowired
    public BulletinDePaieRepository repository;
    @Autowired
    public JavaMailSender mailSender;
    @Autowired
    public EmployeeRepository employeeRepository;


    public BulletinDePaie calculerPaie(Long employeeId, Double heuresTravailleesParSemaine, Double tauxHoraire,
                                       Double prime, Double deduction, Double acompte,String email) {
        // Calcul heures normales et supplémentaires
        double heuresNormales = Math.min(heuresTravailleesParSemaine, 40.0);
        double heuresSupplementaires = (heuresTravailleesParSemaine > 40) ? heuresTravailleesParSemaine - 40 : 0;

        double salaireBrutNormale = heuresNormales * tauxHoraire;
        double salaireBrutSup = heuresSupplementaires * tauxHoraire * 1.8;
        double salaireBrut = salaireBrutNormale + salaireBrutSup + prime;
        // Application de l'acompte : le salaire net est le salaire brut diminué de la déduction et de l'acompte
        double salaireNet = salaireBrut - deduction - acompte;

        BulletinDePaie bulletin = new BulletinDePaie();
        bulletin.setEmployeeId(employeeId);
        bulletin.setPeriode(LocalDate.now());
        bulletin.setSalaireBrut(salaireBrut);
        bulletin.setSalaireNet(salaireNet);
        bulletin.setAcompte(acompte);

//        // Génération du PDF
        String pdfPath = "pdfs/bulletin_" + employeeId + "_" + System.currentTimeMillis() + ".pdf";
        generatePdf(bulletin, pdfPath);
        bulletin.setPdfPath(pdfPath);

        // Sauvegarder le bulletin en base
        BulletinDePaie savedBulletin = repository.save(bulletin);

        // Envoyer le PDF par email
        sendEmailWithPdf(email, pdfPath);

        return savedBulletin;
    }

    public List<BulletinDePaie> historiquePaie(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public void generatePdf(BulletinDePaie bulletin, String pdfPath) {
        try {
            // Créer le dossier s'il n'existe pas
            File pdfFolder = new File("pdfs");
            if (!pdfFolder.exists()) {
                pdfFolder.mkdirs();
            }
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Bulletin de Paie"));
            document.add(new Paragraph("Employee ID: " + bulletin.getEmployeeId()));
            document.add(new Paragraph("Période: " + bulletin.getPeriode()));
            document.add(new Paragraph("Salaire Brut: " + bulletin.getSalaireBrut()));
            document.add(new Paragraph("Salaire Net: " + bulletin.getSalaireNet()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailWithPdf(String email, String pdfPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Votre Bulletin de Paie");
            helper.setText("Bonjour,\n\nVeuillez trouver en pièce jointe votre bulletin de paie.\n\nCordialement,\nService RH");

            FileSystemResource file = new FileSystemResource(new File(pdfPath));
            helper.addAttachment("BulletinDePaie.pdf", file);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BulletinDePaie> findAll() {
        return repository.findAll();
    }

    public Optional<BulletinDePaie> findById(Long id) {
        return repository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
