package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;
@Getter
@Setter
public class Cadence {
    private Ajustement ajustement;
    private Equipe equipe;
    private Date date;

    public Cadence() {
    }
}
