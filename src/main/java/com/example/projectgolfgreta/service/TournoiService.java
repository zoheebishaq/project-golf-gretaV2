package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.dao.metier.*;
import com.example.projectgolfgreta.formdata.TourFormDTO;
import com.example.projectgolfgreta.formdata.TournoiFormDTO;
import com.example.projectgolfgreta.modelCSV.Cadence;
import com.example.projectgolfgreta.modelCSV.Equipe;
import com.example.projectgolfgreta.models.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Tour saveTour(TourFormDTO tourFormDTO){
        Tour tourDB = new Tour();
        tourDB.setId(tourFormDTO.getId());
//        tourDB.setIntervalleDepart(tourFormDTO.getIntervalleDepart());
        tourDB.setDate(tourFormDTO.getDate());
//        tourDB.setNumTour(tourFormDTO.getNumTour());
        tourDB.setNbJoueursParPartie(tourFormDTO.getNbJoueursParPartie());
        tourDB.setTournoi(tournoiRepository.findById(tourFormDTO.getTournoiId()).get());
        for (Ajustement ajustement:tourFormDTO.getAjustements()) {
            ajustement.setTour(tourDB);
        }
        tourDB.setAjustement(tourFormDTO.getAjustements());
        tourRepository.save(tourDB);
        ajustmentRepository.saveAll(tourDB.getAjustement());
        return tourDB;
    }

//    public void generateCadence(Equipe equipe ,Tour tour) throws ParseException {
//        List<Cadence> cadences = new ArrayList<Cadence>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'MM");
//        Date jour  =  tour.getDate();
//        TimeZone timeZone = TimeZone.getDefault() ;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(timeZone);
//        calendar.setTime(jour);
//        System.out.println(calendar.getTimeZone().getDisplayName());
//        for (Ajustement ajustement:tour.getAjustement()
//             ) {
//            Date d0;
//            int tempsStandar=11;
//            if (ajustement.getTrou().getPar()==4){
//                tempsStandar=14;
//            }
//            else if(ajustement.getTrou().getPar()==5){
//                tempsStandar=17;
//            }
//            if (cadences.size()==0){
//                d0=jour;
//                Date time= simpleDateFormat.parse(equipe.getHeure());
//                calendar.add(Calendar.HOUR,time.getHours());
//                calendar.add(Calendar.MINUTE,time.getMinutes()+ajustement.getTempsAjuste()+tempsStandar);
//            }
//            else{
//                d0=cadences.get(cadences.size()-1).getDate();
//                calendar.add(Calendar.MINUTE,ajustement.getTempsAjuste()+tempsStandar);
//            }
//            Cadence cadence = new Cadence(ajustement,equipe,calendar.getTime());
//            cadences.add(cadence);
//        }
//        equipe.setCadences(cadences);
//
//    }
public List<Cadence> generateCadence(Equipe equipe,Tour tour) throws ParseException {
    List<Cadence> cadenceArrayList= new ArrayList<Cadence>();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH'h'mm");
    Date jour= tour.getDate();
    Calendar calendar = Calendar. getInstance();
    calendar.setTime(jour);
    calendar.getTimeZone().getDisplayName();
    for (Ajustement ajustement:tour.getAjustement()
    ) {
        Date d0;
        int tempsStandar;
        if(ajustement.getTrou().getPar()==3)
        {
            tempsStandar=11;
        }
        else if(ajustement.getTrou().getPar()==4)
        {
            tempsStandar=14;
        }
        else {
            tempsStandar=17;
        }
        if(cadenceArrayList.size()==0)
        {
            d0=jour;
            Date time=simpleDateFormat.parse(equipe.getHeure());
            calendar.add(Calendar.HOUR,time.getHours());
            calendar.add(Calendar.MINUTE,time.getMinutes()+ajustement.getTempsAjuste()+tempsStandar);
        }
        else{
            d0=cadenceArrayList.get(cadenceArrayList.size()-1).getDate();
            calendar.add(Calendar.MINUTE,ajustement.getTempsAjuste()+tempsStandar);

        }
        Cadence cadence = new Cadence(ajustement,calendar.getTime());
        cadenceArrayList.add(cadence);
    }
    equipe.setCadences(cadenceArrayList);
    return cadenceArrayList;
}

    public void deleteAjustement(long id) {
        ajustmentRepository.deleteById(id);
    }
}


