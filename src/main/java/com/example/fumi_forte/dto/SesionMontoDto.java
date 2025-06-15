/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SesionMontoDto {
    private Long idSesion;
    private BigDecimal montoPendienteSesion;
    private String estado;
}
