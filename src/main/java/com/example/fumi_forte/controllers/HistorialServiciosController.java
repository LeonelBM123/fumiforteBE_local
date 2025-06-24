/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.HistorialSesionesDto;
import com.example.fumi_forte.services.HistorialServiciosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerente/hitorial_sesiones")
public class HistorialServiciosController{
    @Autowired
    private HistorialServiciosService sesionService;

    @GetMapping
    public ResponseEntity<List<HistorialSesionesDto>> listarSesiones() {
        List<HistorialSesionesDto> sesiones = sesionService.obtenerSesiones();
        return ResponseEntity.ok(sesiones);
    } 
}
