package com.api.fitzone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.RecordatorioModel;

@Repository 
public interface IRecordatorioModel extends JpaRepository<RecordatorioModel, Long>{
    
}
