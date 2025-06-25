
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.HistorialSesionesDto;
import com.example.fumi_forte.models.CertificadoFumigacion;
import com.example.fumi_forte.services.HistorialServiciosService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerente/hitorial_sesiones")
public class HistorialServiciosController {
   @Autowired
    private HistorialServiciosService sesionService;

    @GetMapping
    public ResponseEntity<List<HistorialSesionesDto>> listarSesiones() {
        List<HistorialSesionesDto> sesiones = sesionService.obtenerSesiones();
        return ResponseEntity.ok(sesiones);
    } 
}
