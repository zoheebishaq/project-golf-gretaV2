package com.example.projectgolfgreta.dao.metier;

import com.example.projectgolfgreta.models.Parcours;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParcoursRepository extends CrudRepository<Parcours,Long> {

     List<Parcours> findParcoursByGolf_Id(Long id);
}
