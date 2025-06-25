package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.ReporteServicioDto;
import com.example.fumi_forte.services.ReporteServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente/reporte_servicio")
public class ReporteServicioController {

    @Autowired
    private ReporteServicioService reporteServicioService;

    @GetMapping("/{idSolicitud}")
    public ResponseEntity<List<ReporteServicioDto>> obtenerReporte(@PathVariable int idSolicitud) {
        List<ReporteServicioDto> sesiones = reporteServicioService.obtenerSesionesPorSolicitud(idSolicitud);
        return ResponseEntity.ok(sesiones);
    }
}

