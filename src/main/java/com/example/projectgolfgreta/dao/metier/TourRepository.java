package com.example.projectgolfgreta.dao.metier;

import com.example.projectgolfgreta.models.Tour;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TourRepository extends CrudRepository<Tour,Long> {
    List<Tour> findTourByTournoi_Id(Long id);
}
