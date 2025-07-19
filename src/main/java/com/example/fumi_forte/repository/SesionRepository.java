package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.SesionDto;
import com.example.fumi_forte.models.Sesion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findBySolicitudServicio_IdSolicitudServicio(Long idSolicitudServicio);
    
    @Query("""
        SELECT new com.example.fumi_forte.dto.SesionDto(
            s.fecha,
            s.hora,
            s.montoPendienteSesion,
            s.nroSesion,
            s.estado
        )
        FROM Sesion s
        WHERE EXTRACT(MONTH FROM s.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)
          AND EXTRACT(YEAR FROM s.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)
        ORDER BY s.fecha ASC
    """)
    List<SesionDto> findSesionesFuturasDelMes();

    
    @Query("""
        SELECT COUNT(s)
        FROM Sesion s
        WHERE s.estado = :estado
          AND EXTRACT(MONTH FROM s.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)
          AND EXTRACT(YEAR FROM s.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    long countByEstadoEnMesActual(@Param("estado") String estado);
}
