/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialSesionesDto {
     private int idSolicitudServicio;
    private int idSesion;
    private int nroSesion;
    private String trabajadores;
    private LocalDate fecha;
    private LocalTime hora;
    private String nombreCliente;
    private String telefono;
    private String direccionEscrita;
    private String tipoCliente;
    private BigDecimal montoPendienteSesion;
    private String estadoPago;
    private String factura;
    private String certificado;
}
