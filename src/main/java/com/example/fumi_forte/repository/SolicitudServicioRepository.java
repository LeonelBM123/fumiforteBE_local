package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.SolicitudServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {
    List<SolicitudServicio> findByIdCertificado(Long idCertificado);
    List<SolicitudServicio> findByIdCliente(Long idCliente);
    List<SolicitudServicio> findByIdClienteAndEstado(Long idCliente, String estado);
}
