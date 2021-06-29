package com.example.projectgolfgreta.modelCSV;

import com.example.projectgolfgreta.modelCSV.Equipe;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Joueur {
    @CsvBindByPosition(position = 0)
    private String num;
    @CsvBindByPosition(position = 1)
    private String depart;
    @CsvBindByPosition(position = 2)
    private String nom;
    @CsvBindByPosition(position = 3)
    private String club;
    @CsvBindByPosition(position = 4)
    private String nat;
    @CsvBindByPosition(position = 5)
    private String index;
    @CsvBindByPosition(position = 6)
    private String serie;
    @CsvBindByPosition(position = 7)
    private String rep;

    private Equipe equipe;

    public Joueur() {
    }
}
