package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private LocalTime heurePremierDepart;
    private Integer intervalleDepart;
    private Integer nbJoueursPartie;
    private Integer numTour;
    @ManyToOne
    private Tournoi tournoi;
    @OneToMany(mappedBy = "tour")
    private Collection<Ajustement>ajustement;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id && date.equals(tour.date) && heurePremierDepart.equals(tour.heurePremierDepart) && intervalleDepart.equals(tour.intervalleDepart) && nbJoueursPartie.equals(tour.nbJoueursPartie) && numTour.equals(tour.numTour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, heurePremierDepart, intervalleDepart, nbJoueursPartie, numTour);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", date=" + date +
                ", heurePremierDepart=" + heurePremierDepart +
                ", intervalleDepart=" + intervalleDepart +
                ", nbJoueursPartie=" + nbJoueursPartie +
                ", numTour=" + numTour +
                '}';
    }
}

