package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@Entity
@Table
public class Parcours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    private String nom;
    @ManyToOne
    private Golf golf;
    @OneToMany(mappedBy = "parcours")
    private List<Trou> trous;
    @OneToMany(mappedBy = "parcours")
    private Collection<Tournoi>tournoi;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcours parcours = (Parcours) o;
        return id == parcours.id && nom.equals(parcours.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public String toString() {
        return "Parcours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
