package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Pago;
import com.example.fumi_forte.models.PagoSesion;
import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.repository.PagoRepository;
import com.example.fumi_forte.repository.PagoSesionRepository;
import com.example.fumi_forte.repository.SesionRepository;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagar_sesion")
@RequiredArgsConstructor

public class PagoSesionController {
    
    @Autowired
    PagoSesionRepository pagoSesionRepository;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private SesionRepository sesionRepository;
    
    // GET: Obtener lista de pagos sesion
    @BitacoraLog("Listar Pagos")
    @GetMapping("/listar_pagos")
    public ResponseEntity<?> listarPagoSesion() {
       return ResponseEntity.ok(pagoSesionRepository.findAll());
    }
    
    // POST: Crear pago sesion
    @PostMapping("/crear_pago_sesion")
    public ResponseEntity<?> crearPagoSesion(@RequestBody Map<String, Object> datos) {
        try {
            Map<String, Object> pagoMap = (Map<String, Object>) datos.get("pago");

            Long idPago = Long.valueOf(pagoMap.get("idPago").toString());
            Long idSesion = Long.valueOf(datos.get("idSesion").toString());

            Pago pago = pagoRepository.findById(idPago).orElseThrow();
            Sesion sesion = sesionRepository.findById(idSesion).orElseThrow();

            PagoSesion nuevo = new PagoSesion();
            nuevo.setPago(pago);
            nuevo.setSesion(sesion);

            pagoSesionRepository.save(nuevo);

            return ResponseEntity.ok("Pago cotización creado");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    
    // DELETE: Eliminar un pago sesion
    @DeleteMapping("/eliminar_pago_sesion/{id}")
    public ResponseEntity<Void> eliminarPagoSesion(@PathVariable Long id) {
        if (!pagoSesionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoSesionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
