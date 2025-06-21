package com.example.fumi_forte.controllers;


import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.PagoDto;
import com.example.fumi_forte.models.Pago;
import com.example.fumi_forte.repository.PagoRepository;
import com.example.fumi_forte.dto.AdministrarPagosDto;
import com.example.fumi_forte.models.PagoCotizacion;
import com.example.fumi_forte.models.PagoSesion;
import com.example.fumi_forte.repository.PagoCotizacionRepository;
import com.example.fumi_forte.repository.PagoSesionRepository;

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
    @Autowired
    PagoCotizacionRepository pagoCotizacionRepository;
    @Autowired
    PagoSesionRepository pagoSesionRepository;
    
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
    
    @BitacoraLog("Listar Pagos Extendidos")
    @GetMapping("/listar_pagos_ext")
    public List<AdministrarPagosDto> listarPagosExtendidos() {
        List<Pago> pagos = pagoRepository.findAll();

        return pagos.stream().map(pago -> {
            String nombreCompleto = pago.getCliente().getUsuario().getNombreCompleto();
            String tipoCliente = pago.getCliente().getTipoCliente();

            String tipoServicio = "Desconocido";
            Long idSolicitudServicio = null;
            Long idSesion = null;

            try {
                if (pagoCotizacionRepository.existsByPago_IdPago(pago.getIdPago())) {
                    tipoServicio = "Cotización";
                    Optional<PagoCotizacion> pagoCotizacionOpt = pagoCotizacionRepository.findByPago_IdPago(pago.getIdPago());
                    if (pagoCotizacionOpt.isPresent()) {
                        idSolicitudServicio = pagoCotizacionOpt.get().getIdSolicitudServicio() != null 
                            ? pagoCotizacionOpt.get().getIdSolicitudServicio().getIdSolicitudServicio() 
                            : null;
                    }
                    idSesion = null;
                } else if (pagoSesionRepository.existsByPago_IdPago(pago.getIdPago())) {
                    tipoServicio = "Sesión";
                    Optional<PagoSesion> pagoSesionOpt = pagoSesionRepository.findByPago_IdPago(pago.getIdPago());
                    if (pagoSesionOpt.isPresent()) {
                        idSesion = pagoSesionOpt.get().getSesion() != null 
                            ? pagoSesionOpt.get().getSesion().getIdSesion() 
                            : null;

                        idSolicitudServicio = (pagoSesionOpt.get().getSesion() != null 
                            && pagoSesionOpt.get().getSesion().getSolicitudServicio() != null)
                            ? pagoSesionOpt.get().getSesion().getSolicitudServicio().getIdSolicitudServicio()
                            : null;
                    }
                } else {
                    idSolicitudServicio = 0L;
                    idSesion = 0L;
                }
            } catch (Exception e) {
                idSolicitudServicio = 0L;
                idSesion = 0L;
            }

            return new AdministrarPagosDto(
                pago.getIdPago(),
                pago.getCliente().getIdCliente(),
                idSolicitudServicio,
                idSesion,
                pago.getFecha(),
                nombreCompleto,
                tipoCliente,
                tipoServicio,
                pago.getTipoPago(),
                pago.getNroVoucher(),
                pago.getMonto(),
                pago.getEstado()
            );
        }).toList();
    }


    
    // POST: Crear pago
    @BitacoraLog("Crear Pago")
    @PostMapping("/crear_pago")
    public Pago crearPago(@RequestBody Pago pago) {
        System.out.println("Pago recibido: " + pago);
        return pagoRepository.save(pago);
    }

    // PUT: Actualizar Pago
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

        if (pagoActualizada.getNroVoucher() == null || pagoActualizada.getNroVoucher().trim().isEmpty()) {
            pagoExistente.setNroVoucher("N/A");
        } else {
            pagoExistente.setNroVoucher(pagoActualizada.getNroVoucher());
        }

        pagoExistente.setEstado(pagoActualizada.getEstado());

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
