package com.example.gestioonrhetpaie.services;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.repository.BulletinDePaieRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;


import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaieService {
    @Autowired
    private BulletinDePaieRepository repository;

    public BulletinDePaie calculerPaie(Long employeeId, Double heuresTravaillees, Double tauxHoraire,
                                       Double prime, Double deduction) {
        // Calcul simplifié du salaire brut et net
        Double salaireBrut = heuresTravaillees * tauxHoraire + prime;
        Double salaireNet = salaireBrut - deduction;

        BulletinDePaie bulletin = new BulletinDePaie();
        bulletin.setEmployeeId(employeeId);
        bulletin.setPeriode(LocalDate.now());
        bulletin.setSalaireBrut(salaireBrut);
        bulletin.setSalaireNet(salaireNet);

        // Génération du PDF
        String pdfPath = "pdfs/bulletin_" + employeeId + "_" + System.currentTimeMillis() + ".pdf";
        generatePdf(bulletin, pdfPath);
        bulletin.setPdfPath(pdfPath);

        return repository.save(bulletin);
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
}
