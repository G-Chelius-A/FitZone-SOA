package com.api.fitzone.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.InscripcionModel;

@Repository 
public interface IInscripcionRepository extends JpaRepository<InscripcionModel, Long>{
    long countByClase_IdClaseAndActivoTrue(Long idClase);
    List<InscripcionModel> findByActivoTrue();
}
