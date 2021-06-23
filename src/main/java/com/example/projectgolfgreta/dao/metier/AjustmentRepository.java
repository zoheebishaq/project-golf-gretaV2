package com.example.projectgolfgreta.dao.metier;

import com.example.projectgolfgreta.models.Ajustement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AjustmentRepository extends CrudRepository<Ajustement,Long> {


    List<Ajustement> findByTour_Id(Long id);

    List<Ajustement> findByTrou_Id(Long id);
}
