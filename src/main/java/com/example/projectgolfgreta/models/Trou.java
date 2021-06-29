package com.example.projectgolfgreta.models;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
@Getter
@Setter
@Entity
@Table
public class Trou {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    private String nom;
    @Basic
    @Column(nullable = false)
    private int par;
    @Basic
    @Column(nullable = false)
    private int numTrou;
    @ManyToOne
    private Parcours parcours;
    @ManyToOne
    private Ajustement ajustement;

    public Trou() {
    }

    public Trou(int numTrou) {
        this.numTrou = numTrou;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trou trou = (Trou) o;
        return id == trou.id && par == trou.par && numTrou == trou.numTrou && nom.equals(trou.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, par, numTrou);
    }

    @Override
    public String toString() {
        return "Trou{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", par=" + par +
                ", numTrou=" + numTrou +
                '}';
    }
}

