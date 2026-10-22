package com.api.fitzone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.SocioModel;

@Repository 
public interface ISocioRepository extends JpaRepository<SocioModel, Long>{
    boolean existsByEmail(String email);
}
