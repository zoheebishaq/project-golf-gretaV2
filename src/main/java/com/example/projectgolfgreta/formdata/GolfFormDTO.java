package com.example.projectgolfgreta.formdata;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class GolfFormDTO {
    private long id;
    private String nom;
    private String coordGPS;

    public void setNom(String nom) {
        this.nom = Jsoup.parse(nom).text();
    }
}
