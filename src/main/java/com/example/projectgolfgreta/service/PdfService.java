package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.modelCSV.Cadence;
import com.example.projectgolfgreta.modelCSV.Equipe;
import com.example.projectgolfgreta.modelCSV.Joueur;
import com.example.projectgolfgreta.models.Tour;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfService {

    public Document generatePdf(List<Equipe> equipes, Tour tour) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A2.rotate());
        PdfWriter.getInstance(document,new FileOutputStream("src/main/resources/pdf/cadence"+tour.getId()+".pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA,16,BaseColor.BLACK);
        Chunk chunk = new Chunk(tour.getTournoi().getNom(),font);

        PdfPTable table = new PdfPTable(21);
        table.setWidthPercentage(100);

        addTableHeader(table);
        for (Equipe equipe:equipes
             ) {
            addRows(table,equipe);
        }
        document.add(chunk);
        document.add(table);
        document.close();
        return document;
    }

    private void addRows(PdfPTable table,Equipe equipe){
        table.addCell(equipe.getNumEquipe());
        table.addCell(equipe.getHeure());
        PdfPTable tableNom = new PdfPTable(1);

        for (Joueur joueur:equipe.getJoueurs()
             ) {tableNom.addCell(joueur.getNom());

        }
        table.addCell(tableNom);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        for (Cadence cadence: equipe.getCadences()
             ) {
            table.addCell(dateFormat.format(cadence.getDate()));

        }
    }

    private void  addTableHeader (PdfPTable table){
        Stream.of("Equipe","Départ", "Joueurs","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18")
                .forEach(columTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.ORANGE);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columTitle));
                    table.addCell(header);
                });

    }
}
