package com.example.projectgolfgreta.modelCSV;

import com.example.projectgolfgreta.models.Ajustement;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Cadence {

    private Ajustement ajustement;

    private Equipe equipe;

    private Date date;


    public Cadence() {
    }

    public Cadence(Ajustement ajustement, Equipe equipe, Date date) {
        this.ajustement = ajustement;
        this.equipe = equipe;
        this.date = date;
    }

    public Cadence(Ajustement ajustement, Date date) {
        this.ajustement = ajustement;
        this.date = date;
    }
}
