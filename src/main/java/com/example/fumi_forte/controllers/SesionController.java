package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Participa;
import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.repository.ParticipaRepository;
import com.example.fumi_forte.repository.SesionRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/sesion")
@RequiredArgsConstructor
public class SesionController {

    private final SesionRepository sesionRepository;
    
    @Autowired
    private ParticipaRepository participaRepository;

    // GET: Listar todas las sesiones
    @GetMapping("/listar")
    public List<Sesion> listarSesiones() {
        return sesionRepository.findAll();
    }
    
    // GET: Listar todas las sesiones de un Solicitud
    @GetMapping("/listar/{idSolicitud}")
    public List<Sesion> listarSesionesPorSolicitud(@PathVariable Long idSolicitud){
        return sesionRepository.findAll();
    }

    // GET: Obtener sesión por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sesion> obtenerSesion(@PathVariable Long id) {
        Optional<Sesion> sesion = sesionRepository.findById(id);
        return sesion.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear nueva sesión
    @PostMapping("/crear")
    public Sesion crearSesion(@RequestBody Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    // PUT: Actualizar una sesión
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Sesion> actualizarSesion(@PathVariable Long id, @RequestBody Sesion sesionActualizada) {
        Optional<Sesion> sesionOptional = sesionRepository.findById(id);
        if (sesionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sesion sesionExistente = sesionOptional.get();
        sesionExistente.setFecha(sesionActualizada.getFecha());
        sesionExistente.setHora(sesionActualizada.getHora());
        sesionExistente.setMontoPendienteSesion(sesionActualizada.getMontoPendienteSesion());
        sesionExistente.setEstado(sesionActualizada.getEstado());
        sesionExistente.setNroSesion(sesionActualizada.getNroSesion());
        sesionExistente.setSolicitudServicio(sesionActualizada.getSolicitudServicio());

        return ResponseEntity.ok(sesionRepository.save(sesionExistente));
    }

    // PUT: Actualizar el estado de una sesion
    @PutMapping("/cambiar_estado/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String nuevoEstado = request.get("estado");

        Sesion sesion = sesionRepository.findById(id).orElse(null);
        if (sesion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sesión no encontrada");
        }

        sesion.setEstado(nuevoEstado);
        sesionRepository.save(sesion);

        return ResponseEntity.ok("Estado actualizado");
    }
    
    // PUT: Actualizar el monto de una sesion
    @PutMapping("/actualizar_monto/{id}")
    public ResponseEntity<?> cambiarMonto(@PathVariable Long id, @RequestBody Map<String, String> request) {
        BigDecimal nuevoMonto = new BigDecimal(request.get("monto"));

        Sesion sesion = sesionRepository.findById(id).orElse(null);
        if (sesion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sesión no encontrada");
        }

        sesion.setMontoPendienteSesion(nuevoMonto);
        sesionRepository.save(sesion);

        return ResponseEntity.ok("Monto actualizado");
    }
    
    // DELETE: Eliminar sesión
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarSesion(@PathVariable Long id) {
        if (!sesionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sesionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // GET: Devuelve datos importantes de la sesion de un trabajador especifico
    @GetMapping("/datos/{idTrabajador}")
    public ResponseEntity<List<Map<String, String>>> obtenerDatosSesiones(@PathVariable Long idTrabajador) {
        List<Participa> participaciones = participaRepository.findByTrabajador_IdTrabajador(idTrabajador);

        List<Map<String, String>> resultados = participaciones.stream()
            .map(participa -> {
                Sesion sesion = participa.getSesion();
                SolicitudServicio solicitud = sesion.getSolicitudServicio();

                Map<String, String> mapa = new HashMap<>();
                mapa.put("idSesion", sesion.getIdSesion().toString());
                mapa.put("idSolicitudServicio", sesion.getSolicitudServicio().getIdSolicitudServicio().toString());
                mapa.put("ubicacionGps", solicitud != null ? solicitud.getUbicacionGps() : "N/A");
                mapa.put("fechaSesion", sesion.getFecha().toString());
                mapa.put("horaSesion", sesion.getHora().toString());
                mapa.put("estadoSesion", sesion.getEstado());
                return mapa;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(resultados);
    }

}
