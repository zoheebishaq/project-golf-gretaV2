package com.example.projectgolfgreta.models;

import com.example.projectgolfgreta.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Getter @Setter @Entity @Table
public class Tournoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    private String nom;
//    private int nbTours;
    private String commentaire;
    private int nbJoueursPartie = 3;
    @OneToMany(mappedBy = "tournoi")
    private Collection<Tour> tours;
    @ManyToOne
    // a revoire changer user par directeur plus tard car BUG
    private User user;
    @ManyToOne
    private Parcours parcours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournoi tournoi = (Tournoi) o;
        return id == tournoi.id && nbJoueursPartie == tournoi.nbJoueursPartie && dateDebut.equals(tournoi.dateDebut) && nom.equals(tournoi.nom) && commentaire.equals(tournoi.commentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDebut, nom, commentaire, nbJoueursPartie);
    }

    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", nom='" + nom + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", nbJoueursPartie=" + nbJoueursPartie +
                '}';
    }
    // a revoire avec Moulin (ATTRIBUT DERIVE)
    public int nbDeTours(){
        return this.tours.size();
    }
}
