package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.dao.metier.*;
import com.example.projectgolfgreta.formdata.GolfFormDTO;
import com.example.projectgolfgreta.formdata.ParcoursFormDTO;
import com.example.projectgolfgreta.formdata.TrouFormDTO;
import com.example.projectgolfgreta.models.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GolfService {
    GolfRepository golfRepository;
    ParcoursRepository parcoursRepository;
    TrouRepository trouRepository;
    TournoiRepository tournoiRepository;
    TourRepository tourRepository;
    AjustmentRepository ajustementRepository;
    TournoiService tournoiService;

    @Autowired
    public GolfService(GolfRepository golfRepository,
                       ParcoursRepository parcoursRepository,
                       TrouRepository trouRepository,
                       TournoiRepository tournoiRepository,
                       TourRepository tourRepository,
                       AjustmentRepository ajustementRepository,
                       TournoiService tournoiService) {
        this.golfRepository = golfRepository;
        this.parcoursRepository = parcoursRepository;
        this.trouRepository = trouRepository;
        this.tournoiRepository = tournoiRepository;
        this.tourRepository = tourRepository;
        this.ajustementRepository = ajustementRepository;
        this.tournoiService = tournoiService;
    }

    //Golf--------------------------------------------------------------------------------------------------------------
    public List<Golf> getGolfs() {
        return Lists.newArrayList(golfRepository.findAll());
    }

    public Golf getGolf(Long id) {
        return golfRepository.findById(id).orElse(new Golf());
    }

    public void deleteGolfs(Long id) {
        List<Parcours> parcours = parcoursRepository.findParcoursByGolf_Id(id);
        for (Parcours parcour : parcours) {
            deleteParcours(parcour.getId());
        }

        golfRepository.deleteById(id);
    }

    public void saveGolf(GolfFormDTO golfFormDTO) {
        Golf golfDB = golfRepository.findById(golfFormDTO.getId()).orElse(new Golf());
        golfDB.setNom(golfFormDTO.getNom());
        golfDB.setCoordGPS(golfFormDTO.getCoordGPS());
        golfRepository.save(golfDB);
    }

    //Parcours----------------------------------------------------------------------------------------------------------
    public List<Parcours> geParcours() {
        return Lists.newArrayList(parcoursRepository.findAll());
    }

    public Parcours getParcour(Long id) {
        return parcoursRepository.findById(id).orElse(new Parcours());
    }

    public void deleteParcours(Long id) {
        List<Tournoi> tournoi = tournoiRepository.findTournoiByParcours_Id(id);
        for (Tournoi tournois : tournoi) {
            tournoiService.deleteTournois(tournois.getId());
        }
        List<Trou> trou = trouRepository.findTrouByParcours_Id(id);
        for (Trou trous : trou) {
            deleteTrou(trous.getId());
        }

        parcoursRepository.deleteById(id);
    }

    public void saveParcours(ParcoursFormDTO parcoursFormDTO) {
//        Parcours parcoursDB = parcoursRepository.findById(parcoursFormDTO.getId()).orElse(new Parcours());
        Parcours parcoursDB = new Parcours();
        parcoursDB.setId(parcoursFormDTO.getId());
        parcoursDB.setNom(parcoursFormDTO.getNom());
        parcoursDB.setGolf(golfRepository.findById(parcoursFormDTO.getGolfId()).orElse(new Golf()));

//        parcoursRepository.save(parcoursDB);
        for (Trou trou : parcoursFormDTO.getTrous()) {
            trou.setParcours(parcoursDB);
        }

        parcoursDB.setTrous(parcoursFormDTO.getTrous());
        parcoursRepository.save(parcoursDB);
        trouRepository.saveAll(parcoursDB.getTrous());
    }


    //Trou--------------------------------------------------------------------------------------------------------------
    public List<Trou> getTrous() {
        return Lists.newArrayList(trouRepository.findAll());
    }

    public Trou getTrou(Long id) {
        return trouRepository.findById(id).orElse(new Trou());
    }

    public void deleteTrou(Long id) {
        List<Ajustement> ajustement = ajustementRepository.findByTrou_Id(id);
        for (Ajustement ajustements : ajustement) {
            tournoiService.deleteAjustement(ajustements.getId());
        }
        trouRepository.deleteById(id);
    }

    public void saveTrou(TrouFormDTO trouFormDTO) {
        Trou trouDB = trouRepository.findById(trouFormDTO.getId()).orElse(new Trou());
        trouDB.setNom(trouFormDTO.getNom());
        trouDB.setPar(trouFormDTO.getPar());
        trouDB.setNumTrou(trouFormDTO.getNumTrou());
        Parcours parcours = parcoursRepository.findById(trouFormDTO.getId()).orElse(new Parcours());
        trouDB.setParcours(parcours);

        trouRepository.save(trouDB);
    }
}
