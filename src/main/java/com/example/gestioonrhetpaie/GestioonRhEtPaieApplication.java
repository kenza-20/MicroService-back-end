package com.example.gestioonrhetpaie;

import com.example.gestioonrhetpaie.entities.BulletinDePaie;
import com.example.gestioonrhetpaie.services.PaieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GestioonRhEtPaieApplication {

	public static void main(String[] args) {SpringApplication.run(GestioonRhEtPaieApplication.class, args);

//		// Création d'un bulletin de paie factice
//		BulletinDePaie bulletin = new BulletinDePaie();
//		bulletin.setEmployeeId(123L);
//		bulletin.setPeriode(LocalDate.of(2025, 1, 1));
//		bulletin.setSalaireBrut(3000.0);
//		bulletin.setSalaireNet(2500.0);
//
//		// Chemin du fichier PDF à générer
//		String pdfPath = "pdfs/bulletin_test.pdf";
//
//		// Générer le PDF
//		PaieService paieService = new PaieService();
//		paieService.generatePdf(bulletin, pdfPath);
//
//		System.out.println("✅ PDF généré à : " + pdfPath);
	}
	}


