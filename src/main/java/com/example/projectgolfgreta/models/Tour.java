package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private LocalTime heurePremierDepart;
    private int intervalleDepart;
    private int nbJoueursParPartie;
    private int numTour;
    @ManyToOne
    private Tournoi tournoi;
    @OneToMany(mappedBy = "tour")
    private Collection<Ajustement>ajustement;


    public Tour() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id && intervalleDepart == tour.intervalleDepart && nbJoueursParPartie == tour.nbJoueursParPartie && numTour == tour.numTour && date.equals(tour.date) && heurePremierDepart.equals(tour.heurePremierDepart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, heurePremierDepart, intervalleDepart, nbJoueursParPartie, numTour);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", date=" + date +
                ", heurePremierDepart=" + heurePremierDepart +
                ", intervalleDepart=" + intervalleDepart +
                ", nbJoueursPartie=" + nbJoueursParPartie +
                ", numTour=" + numTour +
                '}';
    }
}

