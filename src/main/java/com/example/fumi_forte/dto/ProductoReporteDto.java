
package com.example.fumi_forte.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoReporteDto {
    Long idProducto;
    String nombre;
    LocalDate fechaVencimiento;
    String descripcion;
    Integer stock;
    String unidadMedida;
}
