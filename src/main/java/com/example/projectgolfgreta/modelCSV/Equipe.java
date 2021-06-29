package com.example.projectgolfgreta.modelCSV;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Equipe {
    @CsvBindByPosition(position = 0)
    private String numEquipe;
    @CsvBindByPosition(position = 1)
    private String heure;

    private List<Joueur> joueurs;

    private List<Cadence> cadences;

    public Equipe() {
    }


    public Equipe(String numEquipe, String heure, List<Joueur> joueurs) {
        this.numEquipe = numEquipe;
        this.heure = heure;
        this.joueurs = joueurs;
    }
}
