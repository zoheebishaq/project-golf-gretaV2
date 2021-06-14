package com.example.projectgolfgreta.formdata;

import com.example.projectgolfgreta.models.Trou;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ParcoursFormDTO {
    private long id;
    private String nom;
    private long golfId;

    private List<Trou> trous;

    public void addTrou(Trou trou)
    {
        this.trous.add(trou);
    }
}
