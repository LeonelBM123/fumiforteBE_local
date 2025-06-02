package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Bitacora;
import com.example.fumi_forte.models.Producto;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController

public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;
    @Autowired
    private UsuarioRepository usuarios;
    
    @BitacoraLog("Se creo una solicitud de Servicio")
    @PostMapping
    @RequestMapping("/solicitud_servicio")
    public SolicitudServicio registrarSolicitud(@RequestBody SolicitudServicio solicitud) {
        solicitud.setEstado("Pendiente");
        solicitud.setIdGerente(null);
        solicitud.setIdCertificado(null);
        solicitud.setMontoPendienteCotizacion(new BigDecimal("0.00"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null && auth.isAuthenticated()) {
             String username = auth.getName();
             Usuario usuario = usuarios.findByNombreCompleto(username);
             solicitud.setIdCliente(usuario.getIdUsuario());
        }
        solicitudServicioRepository.save(solicitud);
        return solicitud;
    }
    
    // Endpoint GET para obtener todas las solicitudes
    @RequestMapping("/solicitudes")
    @GetMapping
    public List<SolicitudServicio> getAllSolicitudes() {
        return solicitudServicioRepository.findAll();
    }
    
    //Obtener solicitud por id
    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudServicio> getSolicitudById(@PathVariable Long id) {
        Optional<SolicitudServicio> solicitud = solicitudServicioRepository.findById(id);
        if (solicitud.isPresent()) {
            return ResponseEntity.ok(solicitud.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    //MODIFICAR
    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody SolicitudServicio solicitudActualizado) {
        Optional<SolicitudServicio> solicitudOptional = solicitudServicioRepository.findById(id);

        if (!solicitudOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudServicio solicitudExistente = solicitudOptional.get();

        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        solicitudExistente.setDescripcion(solicitudActualizado.getDescripcion());
        solicitudExistente.setUbicacionGps(solicitudActualizado.getUbicacionGps());
        solicitudExistente.setDireccionEscrita(solicitudActualizado.getDireccionEscrita());
        solicitudExistente.setEstado(solicitudActualizado.getEstado());
        solicitudExistente.setMontoPendienteCotizacion(solicitudActualizado.getMontoPendienteCotizacion());
        solicitudExistente.setCantidadSesiones(solicitudActualizado.getCantidadSesiones());
        //Creo tantas sesiones como 
        solicitudExistente.setRequiereCertificado(solicitudActualizado.getRequiereCertificado());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                String username = auth.getName(); //luego cambiar por el correo para que no existan duplicados
                Usuario usuario = usuarios.findByNombreCompleto(username);
                solicitudExistente.setIdGerente(usuario.getIdUsuario());
            }
        solicitudExistente.setIdCertificado(solicitudActualizado.getIdCertificado());
        SolicitudServicio solicitudModificado = solicitudServicioRepository.save(solicitudExistente);
        return ResponseEntity.ok(solicitudModificado);
    }
}
