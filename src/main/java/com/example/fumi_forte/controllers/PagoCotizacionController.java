package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.PagoCotizacion;
import com.example.fumi_forte.repository.PagoCotizacionRepository;

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
@RequestMapping("/pagar_cotizacion")
@RequiredArgsConstructor

public class PagoCotizacionController {
    @Autowired
    PagoCotizacionRepository pagoCotizacionRepository;
    
    // GET: Obtener lista de pagos cotizacion
    @BitacoraLog("Listar Pagos")
    @GetMapping("/listar_pagos")
    public ResponseEntity<?> listarPagoCotizacion() {
       return ResponseEntity.ok(pagoCotizacionRepository.findAll());
    }
    
    // POST: Crear pago cotizacion
    @PostMapping("/crear_pago_cotizacion")
    public PagoCotizacion crearPago(@RequestBody PagoCotizacion pagoCotizacion) {
        return pagoCotizacionRepository.save(pagoCotizacion);
    }
    
    // DELETE: Eliminar un pago cotizacion
    @DeleteMapping("/eliminar_pago_cotizacion/{id}")
    public ResponseEntity<Void> eliminarPagoCotizacion(@PathVariable Long id) {
        if (!pagoCotizacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoCotizacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
