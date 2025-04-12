package com.example.gestioonrhetpaie.services;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.entities.Employee;
import com.example.gestioonrhetpaie.repository.BulletinDePaieRepository;
import com.example.gestioonrhetpaie.repository.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaieService {

    private static final Logger log = LoggerFactory.getLogger(PaieService.class);

    @Autowired
    private BulletinDePaieRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BulletinDePaie calculerPaie(Long employeeId, Double heuresTravailleesParSemaine, Double tauxHoraire,
                                       Double prime, Double deduction, Double acompte, String email) {
        log.info("📩 Calcul de la paie pour l'employé ID: {}", employeeId);
        try {
            double heuresNormales = Math.min(heuresTravailleesParSemaine, 40.0);
            double heuresSupplementaires = Math.max(0, heuresTravailleesParSemaine - 40);

            double salaireBrutNormale = heuresNormales * tauxHoraire;
            double salaireBrutSup = heuresSupplementaires * tauxHoraire * 1.8;
            double salaireBrut = salaireBrutNormale + salaireBrutSup + prime;
            double salaireNet = salaireBrut - deduction - acompte;

            BulletinDePaie bulletin = new BulletinDePaie();
            bulletin.setEmployeeId(employeeId);
            bulletin.setPeriode(LocalDate.now());
            bulletin.setSalaireBrut(salaireBrut);
            bulletin.setSalaireNet(salaireNet);
            bulletin.setAcompte(acompte);

            // Générer PDF
            String pdfPath = "pdfs/bulletin_" + employeeId + "_" + System.currentTimeMillis() + ".pdf";
            generatePdf(bulletin, pdfPath);
            bulletin.setPdfPath(pdfPath);

            // Sauvegarder en DB
            BulletinDePaie savedBulletin = repository.save(bulletin);
            log.info("✅ Bulletin sauvegardé avec ID: {}", savedBulletin.getId());

            // Envoyer email
            sendEmailWithPdf(email, pdfPath);
            log.info("📧 Email envoyé à {}", email);

            return savedBulletin;

        } catch (Exception e) {
            log.error("❌ Erreur lors du calcul de la paie", e);
            throw new RuntimeException("Erreur lors du calcul de la paie", e);
        }
    }

    public List<BulletinDePaie> historiquePaie(Long employeeId) {
        log.info("📜 Récupération de l’historique de paie pour l’employé ID: {}", employeeId);
        return repository.findByEmployeeId(employeeId);
    }

    public List<BulletinDePaie> findAll() {
        log.info("📥 Appel de PaieService.findAll()");
        try {
            List<BulletinDePaie> list = repository.findAll();
            log.info("✅ {} bulletins récupérés", list.size());
            return list;
        } catch (Exception e) {
            log.error("❌ Erreur dans findAll()", e);
            throw e;
        }
    }

    public Optional<BulletinDePaie> findById(Long id) {
        log.info("🔍 Recherche du bulletin ID: {}", id);
        return repository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        log.info("📋 Récupération de tous les employés");
        return employeeRepository.findAll();
    }

    public void generatePdf(BulletinDePaie bulletin, String pdfPath) {
        try {
            log.info("🧾 Génération du PDF : {}", pdfPath);
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
            log.error("❌ Erreur lors de la génération du PDF", e);
        }
    }

    private void sendEmailWithPdf(String email, String pdfPath) {
        try {
            log.info("📤 Envoi de l’email avec pièce jointe : {}", pdfPath);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Votre Bulletin de Paie");
            helper.setText("Bonjour,\n\nVeuillez trouver en pièce jointe votre bulletin de paie.\n\nCordialement,\nService RH");

            FileSystemResource file = new FileSystemResource(new File(pdfPath));
            helper.addAttachment("BulletinDePaie.pdf", file);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("❌ Erreur lors de l’envoi de l’email à {}", email, e);
        }
    }
}
