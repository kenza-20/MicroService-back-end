package com.example.gestioonrhetpaie.services;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.entities.Employee;
import com.example.gestioonrhetpaie.repository.BulletinDePaieRepository;
import com.example.gestioonrhetpaie.repository.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        genererPDF(bulletin, pdfPath);
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

    public byte[] genererPDF(BulletinDePaie bulletin, String nomEmploye) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            repository.save(bulletin);
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Infos avec valeurs sûres
            int heures = bulletin.getHeuresTravaillees();
            double taux = bulletin.getTauxHoraire() != null ? bulletin.getTauxHoraire() : 0.0;
            double prime = bulletin.getPrime() != null ? bulletin.getPrime() : 0.0;
            double deduction = bulletin.getDeduction() != null ? bulletin.getDeduction() : 0.0;
            double acompte = bulletin.getAcompte() != null ? bulletin.getAcompte() : 0.0;
            double salaireBrut = bulletin.getSalaireBrut() != null ? bulletin.getSalaireBrut() : 0.0;
            double salaireNet = bulletin.getSalaireNet() != null ? bulletin.getSalaireNet() : 0.0;

            Paragraph infos = new Paragraph()
                    .add("Employé : " + (nomEmploye != null ? nomEmploye : "N/A") + "\n")
                    .add("Date : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n")
                    .add("Heures travaillées : " + heures + "\n")
                    .add("Taux horaire : " + taux + " DT\n")
                    .add("Prime : " + prime + " DT\n")
                    .add("Déductions : " + deduction + " DT\n")
                    .add("Acompte : " + acompte + " DT\n")
                    .add("\nSalaire Brut à Payer : " + salaireBrut + " DT")
                    .add("\nSalaire Net à Payer : " + salaireNet + " DT");

            document.add(new Paragraph("Bulletin de Paie").setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold());
            document.add(infos);
            document.add(new Paragraph("Signature").setTextAlignment(TextAlignment.RIGHT).setMarginTop(30));
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
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

    public Employee addEmploye(String nom, String prenom, String email) {
        Employee employee = new Employee();
        employee.setNom(nom);
        employee.setPrenom(prenom);
        employee.setEmail(email);

        return employeeRepository.save(employee);
    }

}