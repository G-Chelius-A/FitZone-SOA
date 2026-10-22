package com.api.fitzone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.SucursalModel;

@Repository 
public interface ISucursalRepository extends JpaRepository<SucursalModel, Long>{
    
}
