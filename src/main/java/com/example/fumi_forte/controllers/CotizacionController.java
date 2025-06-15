package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.CotizacionDto;
import com.example.fumi_forte.models.Cotizacion;
import com.example.fumi_forte.repository.CotizacionRepository;

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
@RequestMapping("/cotizar")
@RequiredArgsConstructor
public class CotizacionController {

    @Autowired
    CotizacionRepository cotizacionRepository;

    // GET: Obtener lista de cotizaciones
    @BitacoraLog("Listar Cotizaciones")
    @GetMapping("/listar_cotizaciones")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<CotizacionDto> obtenerCotizaciones() {
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();

        return cotizaciones.stream().map(c -> new CotizacionDto(
            c.getIdCotizacion(),
            c.getFecha(),
            c.getCostoCotizacion(),
            c.getSolicitudServicio().getIdSolicitudServicio(),
            c.getGerente().getIdGerente()
        )).toList();
    }

    // POST: Crear cotizacion
    @BitacoraLog("Crear Cotizacion")
    @PostMapping("/crear_cotizacion")
    @PreAuthorize("hasAuthority('Gerente')")
    public Cotizacion crearCotizacion(@RequestBody Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }

    // PUT: Actualizar Cotizacion
    @BitacoraLog("Actualizar Cotizacion")
    @PutMapping("/actualizar_cotizacion/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<Cotizacion> actualizarCotizacion(@PathVariable Long id, @RequestBody Cotizacion cotizacionActualizada) {
        Optional<Cotizacion> cotizacionOptional = cotizacionRepository.findById(id);
        if (cotizacionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cotizacion cotizacionExistente = cotizacionOptional.get();
        cotizacionExistente.setFecha(cotizacionActualizada.getFecha());
        cotizacionExistente.setCostoCotizacion(cotizacionActualizada.getCostoCotizacion());

        return ResponseEntity.ok(cotizacionRepository.save(cotizacionExistente));
    }

    // DELETE: Eliminar una cotizacion
    @BitacoraLog("Eliminar Cotizacion")
    @DeleteMapping("/eliminar_cotizacion/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<Void> eliminarCotizacion(@PathVariable Long id) {
        if (!cotizacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cotizacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
