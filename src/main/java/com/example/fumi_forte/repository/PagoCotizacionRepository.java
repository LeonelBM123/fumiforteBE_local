package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.PagoCotizacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoCotizacionRepository extends JpaRepository<PagoCotizacion, Long>{
    boolean existsByPago_IdPago(Long idPago);
    Optional<PagoCotizacion> findByPago_IdPago(Long idPago);
}

