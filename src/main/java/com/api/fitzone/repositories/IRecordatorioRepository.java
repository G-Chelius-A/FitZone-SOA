package com.api.fitzone.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fitzone.models.RecordatorioModel;

@Repository
public interface IRecordatorioRepository extends JpaRepository<RecordatorioModel, Long> {
    // Comprobar que no se ha enviado un recordatorio para una inscripción y fecha
    // determinadas. Evita duplicados
    boolean existsByInscripcion_IdAndFecha(Long idInscripcion, LocalDateTime fecha);
}
