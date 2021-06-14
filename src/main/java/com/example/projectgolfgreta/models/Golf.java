package com.example.projectgolfgreta.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Getter
@Setter
@Entity
@Table
public class Golf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    @Column(nullable = false,unique = false)
    private String nom;
    private String coordGPS;
    @OneToMany(mappedBy = "golf")
    private Collection <Parcours> parcours;

    public Collection<Parcours> getParcours() {
        return parcours;
    }

    public void setParcours(Collection<Parcours> parcours) {
        this.parcours = parcours;
    }

    public Golf(){

    }
    public Golf(String name) {
        this.nom=name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Golf golf = (Golf) o;
        return id == golf.id && nom.equals(golf.nom) && coordGPS.equals(golf.coordGPS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, coordGPS);
    }

    @Override
    public String toString() {
        return "Golf{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
