package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    
    @Query("SELECT c FROM Calificacion c WHERE c.solicitud.idSolicitudServicio = :solicitudId")
    List<Calificacion> findBySolicitudId(@Param("solicitudId") Long solicitudId);
}