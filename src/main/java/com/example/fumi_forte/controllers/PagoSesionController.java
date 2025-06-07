package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.PagoSesion;
import com.example.fumi_forte.repository.PagoSesionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    // GET: Obtener lista de pagos sesion
    @BitacoraLog("Listar Pagos")
    @GetMapping("/listar_pagos")
    public ResponseEntity<?> listarPagoSesion() {
       return ResponseEntity.ok(pagoSesionRepository.findAll());
    }
    
    // POST: Crear pago sesion
    @PostMapping("/crear_pago_sesion")
    public PagoSesion crearPago(@RequestBody PagoSesion pagoSesion) {
        return pagoSesionRepository.save(pagoSesion);
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
