package com.micro.backendmicro.services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.micro.backendmicro.entities.Employe;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PDFService {

    public void generateEmployePDF(Employe employe) throws IOException {
        // Set up the PDF file
        String filePath = "C:\\Users\\amir\\Desktop\\microindiv\\Employe-Service\\employee_" + employe.getId() + ".pdf";
        File file = new File(filePath);

        // Create a PdfDocument and a Document instance to add content
        PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(new FileOutputStream(file)));
        Document document = new Document(pdfDoc);

        // Set up the font and styling for the document
        PdfFont font = com.itextpdf.kernel.font.PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Add employee information to the PDF
        document.add(new Paragraph(new Text("Employee ID: " + employe.getId()).setFont(font)));
        document.add(new Paragraph("Name: " + employe.getName()));
        document.add(new Paragraph("Description: " + employe.getDescription()));
        document.add(new Paragraph("Role: " + employe.getRole()));
        document.add(new Paragraph("Grade: " + employe.getGrade()));

        // Close the document
        document.close();
    }
}
