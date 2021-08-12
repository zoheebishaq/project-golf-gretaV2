package com.example.projectgolfgreta.controller;

import com.example.projectgolfgreta.formdata.TourFormDTO;
import com.example.projectgolfgreta.formdata.TournoiFormDTO;
import com.example.projectgolfgreta.modelCSV.Equipe;
import com.example.projectgolfgreta.modelCSV.Joueur;
import com.example.projectgolfgreta.models.*;
import com.example.projectgolfgreta.service.GolfService;
import com.example.projectgolfgreta.service.PdfService;
import com.example.projectgolfgreta.service.TournoiService;
import com.itextpdf.text.Document;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TournoiController {
    private GolfService golfService;
    private TournoiService tournoiService;
    private PdfService pdfService;

    @Autowired
    public TournoiController(GolfService golfService, TournoiService tournoiService, PdfService pdfService) {
        this.golfService = golfService;
        this.tournoiService = tournoiService;
        this.pdfService = pdfService;
    }

    //Tournoi-----------------------------------------------------------------------------------------------------------
//    remettre la bonne URL une fois en prod --  /arbitre/tournoi"
    @GetMapping("/tournoi")
    public String tournoi(Model model) {
        model.addAttribute("tournoi", tournoiService.getTournois());
        return "tournoiList";
    }

    @GetMapping("/tournoi/{id}")
    public String tournois(Model model, @PathVariable(name = "id") Long id) {
        Tournoi tournoi = tournoiService.getTournoi(id);
        TournoiFormDTO tournoiFormDTO = new TournoiFormDTO();
        tournoiFormDTO.setId(tournoi.getId());
        tournoiFormDTO.setNom(tournoi.getNom());
        tournoiFormDTO.setDateDebut(tournoi.getDateDebut());
        tournoiFormDTO.setCommentaire(tournoi.getCommentaire());
        List<Parcours> parcours = golfService.geParcours();
        model.addAttribute("tournoi", tournoiFormDTO);
        model.addAttribute("parcours", parcours);

        return "tournoiForm";
    }


    @GetMapping("/tournoi/form")
    public String addTournoi(Model model) {
        model.addAttribute("tournoi", new TournoiFormDTO());
        List<Parcours> parcours = golfService.geParcours();
        model.addAttribute("parcours", parcours);
        return "tournoiForm";
    }

    @GetMapping("/tournoi/delete{id}")
    public String deleteTournoi(@PathVariable(name = "id") Long id) {
        tournoiService.deleteTournois(id);
        return "redirect:/tournoi";
    }

    @PostMapping("/tournoi")
    public String postTournoi(@ModelAttribute(name = "tournoi") TournoiFormDTO tournoiFormDTO) {
        tournoiService.saveTournoi(tournoiFormDTO);
        return "redirect:/tournoi";
    }

    //Tour--------------------------------------------------------------------------------------------------------------
    @GetMapping("/tournoi/tour/{idTournois}")
    public String tour(Model model, @PathVariable Long idTournois) {
        TourFormDTO tourFormDTO = new TourFormDTO();
        tourFormDTO.setTournoiId(idTournois);
        List<Ajustement> ajustements = new ArrayList<Ajustement>();
        Parcours parcours = tournoiService.getTournoi(idTournois).getParcours();
        for (Trou trou : parcours.getTrous()
        ) {
            ajustements.add(new Ajustement(trou));

        }
        tourFormDTO.setAjustements(ajustements);
        model.addAttribute("tour", tourFormDTO);

        return "tourAddForm";
    }

    @GetMapping("/tournoi/tour/update/{id}")
    public String tourUpdate(Model model, @PathVariable(name = "id") Long id) {
        Tour tour = tournoiService.getTour(id);
        TourFormDTO tourFormDTO = new TourFormDTO();
        tourFormDTO.setId(tour.getId());
//        tourFormDTO.setNumTour(tour.getNumTour());
        tourFormDTO.setDate(tour.getDate());
        tourFormDTO.setNbJoueursParPartie(tour.getNbJoueursParPartie());
//        tourFormDTO.setIntervalleDepart(tour.getIntervalleDepart());
        tourFormDTO.setTournoiId(tour.getTournoi().getId());
        List<Tournoi> tournoi = tournoiService.getTournois();
        List<Ajustement> ajustements = (List<Ajustement>) tour.getAjustement();
        tourFormDTO.setAjustements(ajustements);
        model.addAttribute("tournoi", tournoi);
        model.addAttribute("tour", tourFormDTO);

        return "tourAddForm";
    }

    @GetMapping("/tournoi/tour/delete{id}")
    public String deleteTour(@PathVariable(name = "id") Long id) {
        tournoiService.deleteTour(id);
        return "redirect:/tournoi";
    }


    @PostMapping("/tournoi/tour")
    public String postTour(Model model, @ModelAttribute TourFormDTO tourFormDTO) {
        Document pdf = new Document();
        // parse CSV file to create a list of `User` objects
        try (Reader reader = new BufferedReader(new InputStreamReader(tourFormDTO.getFile().getInputStream(), "utf-16"))) {

            // create csv bean reader
            CsvToBean<Joueur> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Joueur.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withSkipLines(1)
                    .build();

            // convert `CsvToBean` object to list of users
            List<Joueur> joueurs = csvToBean.parse();
            String numEquipe = "";
            String heure = "";
            List<Equipe> equipes = new ArrayList<Equipe>();
            Equipe equipe = new Equipe();
            for (Joueur joueur : joueurs
            ) {
                if (!joueur.getNum().equals("")) {
                    numEquipe = joueur.getNum();
                    heure = joueur.getDepart();
                    equipe = new Equipe(numEquipe, heure, new ArrayList<Joueur>());
                    equipes.add(equipe);
                }
                joueur.setNum(numEquipe);
                joueur.setDepart(heure);
                equipe.getJoueurs().add(joueur);
            }
            Tour tour = tournoiService.saveTour(tourFormDTO);
            for (Equipe equipeA : equipes) {

                tournoiService.generateCadence(equipeA, tour);
            }
            pdf = pdfService.generatePdf(equipes, tour);

//            System.out.println("apres csvToBean");
//
//            // save users list on model
////            model.addAttribute("users", users);
////            model.addAttribute("status", true);

        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            System.out.println("An error occurred while processing the CSV file.");
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            ex.printStackTrace();
            model.addAttribute("status", false);

        }

        return "redirect:/tournoi";
    }

}

