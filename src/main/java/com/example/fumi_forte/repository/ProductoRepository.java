package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    boolean existsByNombre(String nombre);
}
