package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.SesionDto;
import com.example.fumi_forte.models.Sesion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findBySolicitudServicio_IdSolicitudServicio(Long idSolicitudServicio);
    
    @Query("""
        SELECT new com.example.fumi_forte.dto.SesionDto(
            s.fecha,
            s.hora,
            s.montoPendienteSesion,
            s.nroSesion
        )
        FROM Sesion s
        WHERE s.fecha >= CURRENT_DATE
        ORDER BY s.fecha ASC
    """)
    List<SesionDto> findSesionesFuturas();

    
    long countByEstado(String estado);
}
