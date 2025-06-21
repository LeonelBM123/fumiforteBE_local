package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.PagoSesion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoSesionRepository extends JpaRepository<PagoSesion, Long>{
    boolean existsByPago_IdPago(Long idPago);
    Optional<PagoSesion> findByPago_IdPago(Long idPago);
}
