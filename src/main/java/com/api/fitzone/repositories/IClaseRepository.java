package com.api.fitzone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.ClaseModel;

@Repository
public interface IClaseRepository extends JpaRepository<ClaseModel, Long>{
    
}
