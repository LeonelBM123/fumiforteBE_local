package com.example.fumi_forte.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoDto {
    private Long idProducto;
    private String nombre;
    private LocalDate fechaVencimiento;
    private String descripcion;
    private Short stock;
    private String unidadMedida;   
    
    // Constructor parcial para consultas personalizadas
    public ProductoDto(Long idProducto, String nombre, Short stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
    }
}
