package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Pago;
import com.example.fumi_forte.models.PagoCotizacion;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.repository.PagoCotizacionRepository;
import com.example.fumi_forte.repository.PagoRepository;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
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
@RequestMapping("/pagar_cotizacion")
@RequiredArgsConstructor

public class PagoCotizacionController {
    @Autowired
    private PagoCotizacionRepository pagoCotizacionRepository;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;
    
    // GET: Obtener lista de pagos cotizacion
    @BitacoraLog("Listar Pagos")
    @GetMapping("/listar_pagos")
    public ResponseEntity<?> listarPagoCotizacion() {
       return ResponseEntity.ok(pagoCotizacionRepository.findAll());
    }
    
    // POST: Crear pago cotizacion
    @PostMapping("/crear_pago_cotizacion")
    public ResponseEntity<?> crearPagoCotizacion(@RequestBody Map<String, Object> datos) {
        try {
            Map<String, Object> pagoMap = (Map<String, Object>) datos.get("pago");

            Long idPago = Long.valueOf(pagoMap.get("idPago").toString());
            Long idSolicitud = Long.valueOf(datos.get("idSolicitudServicio").toString()); // ← 🔥 CAMBIO AQUÍ

            Pago pago = pagoRepository.findById(idPago).orElseThrow();
            SolicitudServicio solicitud = solicitudServicioRepository.findById(idSolicitud).orElseThrow();

            PagoCotizacion nuevo = new PagoCotizacion();
            nuevo.setPago(pago);
            nuevo.setIdSolicitudServicio(solicitud);

            pagoCotizacionRepository.save(nuevo);

            return ResponseEntity.ok("Pago cotización creado");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
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
