/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoSesionReporteDto {
    Long idPago;
    LocalDate fecha;
    BigDecimal monto;
    String tipoPago;
    String nroVoucher;
    Long idCliente;
    String estado;
    Long idPagoSesion;
    Long idSesion;
}
