package com.example.projectgolfgreta.controller;

import com.example.projectgolfgreta.formdata.TourFormDTO;
import com.example.projectgolfgreta.formdata.TournoiFormDTO;
import com.example.projectgolfgreta.models.*;
import com.example.projectgolfgreta.service.GolfService;
import com.example.projectgolfgreta.service.TournoiService;
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

    @Autowired
    public TournoiController(GolfService golfService, TournoiService tournoiService) {
        this.golfService = golfService;
        this.tournoiService = tournoiService;
    }

    //Tournoi-----------------------------------------------------------------------------------------------------------
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
    public String tourUpdate(Model model,@PathVariable(name = "id") Long id){
        Tour tour = tournoiService.getTour(id);
        TourFormDTO tourFormDTO = new TourFormDTO();
        tourFormDTO.setId(tour.getId());
        tourFormDTO.setNumTour(tour.getNumTour());
        tourFormDTO.setDate(tour.getDate());
        tourFormDTO.setNbJoueursParPartie(tour.getNbJoueursParPartie());
        tourFormDTO.setIntervalleDepart(tour.getIntervalleDepart());
        tourFormDTO.setTournoiId(tour.getTournoi().getId());
        List<Tournoi> tournoi = tournoiService.getTournois();
        List<Ajustement> ajustements = (List<Ajustement>) tour.getAjustement();
        tourFormDTO.setAjustements(ajustements);
        model.addAttribute("tournoi",tournoi);
        model.addAttribute("tour",tourFormDTO);

        return "tourAddForm";
    }

    @GetMapping("/tournoi/tour/delete{id}")
    public String deleteTour(@PathVariable(name = "id") Long id){
        tournoiService.deleteTour(id);
        return "redirect:/tournoi";
    }


    @PostMapping("/tournoi/tour")
    public String postTour(Model model,@ModelAttribute TourFormDTO tourFormDTO){
        tournoiService.saveTour(tourFormDTO);
        // parse CSV file to create a list of `User` objects
        try (Reader reader = new BufferedReader(new InputStreamReader(tourFormDTO.getFile().getInputStream()))) {

            // create csv bean reader
            CsvToBean<Equipe> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Equipe.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of users
            List<Equipe> equipes = csvToBean.parse();

            // TODO: save users in DB?

            // save users list on model
//            model.addAttribute("users", users);
//            model.addAttribute("status", true);

        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            System.out.println("An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }
        return "redirect:/tournoi";
    }
}

