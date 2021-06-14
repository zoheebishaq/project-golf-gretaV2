package com.example.projectgolfgreta.formdata;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
@Getter
@Setter
public class TourFormDTO {
    private long id;
    private Date date;
    private LocalTime heurePremierDepart;
    private int intervalleDepart;
    private int nbJoueursPartie;
    private int numTour;
    private long tournoiId;
}
