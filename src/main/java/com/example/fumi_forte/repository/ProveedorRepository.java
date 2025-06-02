package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{
    boolean existsByCorreo(String correo);
}
