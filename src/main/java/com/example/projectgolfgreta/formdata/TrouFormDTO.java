package com.example.projectgolfgreta.formdata;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class TrouFormDTO {
    private long id;
    private String nom;
    private int par;
    private int numTrou;
    private long parcoursId;
    private long ajustmentId;


}
