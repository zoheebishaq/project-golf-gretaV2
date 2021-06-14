package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table
public class Tournoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date dateDebut;
    private String nom;
    private Integer nbTours;
    private String commentaire;
    @OneToMany(mappedBy = "tournoi")
    private Collection<Tour>tour;
//    @ManyToOne
//    User user;
    @ManyToOne
    Parcours parcours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournoi tournoi = (Tournoi) o;
        return id == tournoi.id && dateDebut.equals(tournoi.dateDebut) && nom.equals(tournoi.nom) && nbTours.equals(tournoi.nbTours) && commentaire.equals(tournoi.commentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDebut, nom, nbTours, commentaire);
    }

    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", nom='" + nom + '\'' +
                ", nbTours=" + nbTours +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
