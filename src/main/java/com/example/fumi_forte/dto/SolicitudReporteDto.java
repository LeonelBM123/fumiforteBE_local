/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author PC
 */
@Data
@AllArgsConstructor
public class SolicitudReporteDto {
    private Long idSolicitudServicio;
    private String descripcion;
    private String ubicacionGps;
    private String direccionEscrita;
    private String estado;
    private BigDecimal montoPendienteCotizacion;
    private Short cantidadSesiones;
    private String requiereCertificado;
    private Long idCliente;
    private Long idGerente;
    private Long idCertificado;
}
