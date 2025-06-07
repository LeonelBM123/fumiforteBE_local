package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long>{
    //operaciones
}

