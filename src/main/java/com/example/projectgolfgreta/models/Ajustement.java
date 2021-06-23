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
public class Ajustement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int tempsAjuste;
    @ManyToOne
    private Tour tour;
    @ManyToOne
    private Trou trou;

    public Ajustement() {
    }

    public Ajustement(Trou trou) {
        this.trou = trou;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ajustement that = (Ajustement) o;
        return id == that.id && tempsAjuste == that.tempsAjuste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tempsAjuste);
    }

    @Override
    public String toString() {
        return "Ajustement{" +
                "id=" + id +
                ", tempsAjuste=" + tempsAjuste +
                '}';
    }
}
