package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.ProductoDto;
import com.example.fumi_forte.models.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    boolean existsByNombre(String nombre);
    
    @Query("""
        SELECT new com.example.fumi_forte.dto.ProductoDto(
            p.idProducto,
            p.nombre,
            p.stock
        )
        FROM Producto p
        WHERE p.stock > 0
    """)
    List<ProductoDto> findInventario();
}
