package com.example.projectgolfgreta.dao.metier;

import com.example.projectgolfgreta.models.Tour;
import com.example.projectgolfgreta.models.Tournoi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournoiRepository extends CrudRepository<Tournoi,Long> {

    List<Tournoi> findTournoiByParcours_Id(Long id);
}
