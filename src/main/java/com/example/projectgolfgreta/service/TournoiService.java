package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.dao.metier.*;
import com.example.projectgolfgreta.formdata.TourFormDTO;
import com.example.projectgolfgreta.formdata.TournoiFormDTO;
import com.example.projectgolfgreta.models.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournoiService {
    TournoiRepository tournoiRepository;
    TourRepository tourRepository;
    GolfRepository golfRepository;
    ParcoursRepository parcoursRepository;
    TrouRepository trouRepository;
    AjustmentRepository ajustmentRepository;


    public TournoiService(TournoiRepository tournoiRepository,
                          TourRepository tourRepository,
                          GolfRepository golfRepository,
                          ParcoursRepository parcoursRepository,
                          TrouRepository trouRepository,
                          AjustmentRepository ajustmentRepository) {
        this.tournoiRepository = tournoiRepository;
        this.tourRepository = tourRepository;
        this.golfRepository = golfRepository;
        this.parcoursRepository = parcoursRepository;
        this.trouRepository = trouRepository;
        this.ajustmentRepository = ajustmentRepository;
    }

    //Tournoi-----------------------------------------------------------------------------------------------------------
    public List<Tournoi> getTournois() {
        return Lists.newArrayList(tournoiRepository.findAll());
    }

    public Tournoi getTournoi(Long id) {
        return tournoiRepository.findById(id).orElse(new Tournoi());
    }

    public void deleteTournois(Long id){
        List<Tour> tours = tourRepository.findTourByTournoi_Id(id);
        for (Tour tour : tours){
            deleteTour(tour.getId());
        }
        tournoiRepository.deleteById(id);
    }

    public void saveTournoi(TournoiFormDTO tournoiFormDTO){
        Tournoi tournoiDB = tournoiRepository.findById(tournoiFormDTO.getId()).orElse(new Tournoi());
        tournoiDB.setNom(tournoiFormDTO.getNom());
        tournoiDB.setDateDebut(tournoiFormDTO.getDateDebut());
        tournoiDB.setCommentaire(tournoiFormDTO.getCommentaire());
        Parcours parcoursDB = parcoursRepository.findById(tournoiFormDTO.getParcoursId()).orElse(new Parcours());
        tournoiDB.setParcours(parcoursDB);
        tournoiRepository.save(tournoiDB);
    }


    //Tour--------------------------------------------------------------------------------------------------------------

    public List<Tour> getTours() {
        return Lists.newArrayList(tourRepository.findAll());
    }

    public Tour getTour(Long id) {
        return tourRepository.findById(id).orElse(new Tour());
    }

    public void deleteTour(Long id){

        List<Ajustement> ajustement = ajustmentRepository.findByTour_Id(id);
        for (Ajustement ajustements : ajustement) {
            deleteAjustement(ajustements.getId());
        }

        tourRepository.deleteById(id);
    }

    public void saveTour(TourFormDTO tourFormDTO){
        Tour tourDB = new Tour();
        tourDB.setId(tourFormDTO.getId());
        tourDB.setIntervalleDepart(tourFormDTO.getIntervalleDepart());
        tourDB.setDate(tourFormDTO.getDate());
        tourDB.setNumTour(tourFormDTO.getNumTour());
        tourDB.setNbJoueursParPartie(tourFormDTO.getNbJoueursParPartie());
        tourDB.setTournoi(tournoiRepository.findById(tourFormDTO.getTournoiId()).get());
        for (Ajustement ajustement:tourFormDTO.getAjustements()) {
            ajustement.setTour(tourDB);
        }
        tourDB.setAjustement(tourFormDTO.getAjustements());
        tourRepository.save(tourDB);
        ajustmentRepository.saveAll(tourDB.getAjustement());
    }
    public void deleteAjustement(long id) {
        ajustmentRepository.deleteById(id);
    }
}


