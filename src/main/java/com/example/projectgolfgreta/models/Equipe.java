package com.example.projectgolfgreta.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Equipe {
    @CsvBindByName
    private int num;
    @CsvBindByName
    private String depart;
    @CsvBindByName
    private List<Joueur> joueurs;
    @CsvBindByName
    private List<Cadence> cadences;

    public Equipe() {
    }
}
