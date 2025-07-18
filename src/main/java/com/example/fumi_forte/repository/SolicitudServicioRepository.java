package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.SolicitudReporteDto;
import com.example.fumi_forte.dto.SolicitudServicioUsuarioDto;
import com.example.fumi_forte.models.SolicitudServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {
    List<SolicitudServicio> findByIdCertificado(Long idCertificado);
    List<SolicitudServicio> findByIdCliente(Long idCliente);
    List<SolicitudServicio> findByIdClienteAndEstado(Long idCliente, String estado);
    
    @Query("""
        SELECT new com.example.fumi_forte.dto.SolicitudServicioUsuarioDto(
            s.idSolicitudServicio,
            s.idCliente,
            u.nombreCompleto
        )
        FROM SolicitudServicio s
        JOIN Cliente c ON c.idCliente = s.idCliente
        JOIN Usuario u ON u.idUsuario = c.idCliente
        WHERE s.estado = 'PENDIENTE'
    """)
    List<SolicitudServicioUsuarioDto> findSolicitudesPendientes();

    
    long countByEstado(String estado);

}
