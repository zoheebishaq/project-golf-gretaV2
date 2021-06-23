package com.example.projectgolfgreta.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Joueur {
    @CsvBindByName
    private String nom;
    @CsvBindByName
    private String club;
    @CsvBindByName
    private String nat;
    @CsvBindByName
    private float index;
    @CsvBindByName
    private String serie;
    @CsvBindByName
    private String rep;
    @CsvBindByName
    private Equipe equipe;

    public Joueur() {
    }
}
