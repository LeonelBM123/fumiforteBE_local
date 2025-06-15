package com.example.fumi_forte.controllers;


import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.PagoDto;
import com.example.fumi_forte.models.Pago;
import com.example.fumi_forte.repository.PagoRepository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagar")
@RequiredArgsConstructor

public class PagoController {
    
    @Autowired
    PagoRepository pagoRepository;
    
    // GET: Obtener lista de pagos
    @BitacoraLog("Listar Pagos")
    @GetMapping("/listar_pagos")
    public List<PagoDto> obtenerPagos() {
        List<Pago> pagos = pagoRepository.findAll();

        return pagos.stream().map(p -> new PagoDto(
            p.getIdPago(),
            p.getFecha(),
            p.getMonto(),
            p.getTipoPago(),
            p.getNroVoucher(),
            p.getEstado(),
            p.getCliente().getIdCliente()
        )).toList();
    }
    
    // POST: Crear pago
    @BitacoraLog("Crear Pago")
    @PostMapping("/crear_pago")
    public Pago crearPago(@RequestBody Pago pago) {
        System.out.println("Pago recibido: " + pago);
        return pagoRepository.save(pago);
    }

    // PUT: Actualizar Pago
    @BitacoraLog("Actualizar Pago")
    @PutMapping("/actualizar_pago/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pagoActualizada) {
        Optional<Pago> pagoOptional = pagoRepository.findById(id);
        if (pagoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pago pagoExistente = pagoOptional.get();
        pagoExistente.setFecha(pagoActualizada.getFecha());
        pagoExistente.setMonto(pagoActualizada.getMonto());
        pagoExistente.setTipoPago(pagoActualizada.getTipoPago());
        pagoExistente.setNroVoucher(pagoActualizada.getNroVoucher());

        return ResponseEntity.ok(pagoRepository.save(pagoExistente));
    }
    
    // DELETE: Eliminar una pago
    @BitacoraLog("Eliminar Pago")
    @DeleteMapping("/eliminar_pago/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        if (!pagoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
