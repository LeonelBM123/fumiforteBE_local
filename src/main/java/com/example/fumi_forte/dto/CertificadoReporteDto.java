
package com.example.fumi_forte.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificadoReporteDto {
    private Long idCertificado;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String estado;
}
