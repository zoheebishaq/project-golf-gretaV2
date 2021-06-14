package com.example.projectgolfgreta.formdata;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class TournoiFormDTO {
    private long id;
    private Date dateDebut;
    private String nom;
    private int nbTours;
    private String commentaire;
}
