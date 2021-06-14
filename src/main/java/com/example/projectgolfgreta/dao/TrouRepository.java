package com.example.projectgolfgreta.dao;

import com.example.projectgolfgreta.models.Trou;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrouRepository extends CrudRepository<Trou,Long> {
    List<Trou> findTrouByParcours_Id(Long id);
}
