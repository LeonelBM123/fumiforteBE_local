package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Participa;
import com.example.fumi_forte.models.ParticipaId;
import com.example.fumi_forte.repository.ParticipaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participa")
@RequiredArgsConstructor
public class ParticipaController {

    private final ParticipaRepository participaRepository;

    // GET: Listar todas las participaciones
    @GetMapping("/listar")
    public List<Participa> listarParticipaciones() {
        return participaRepository.findAll();
    }

    // GET: Obtener por ID compuesta
    @GetMapping("/{idTrabajador}/{idSesion}")
    public ResponseEntity<Participa> obtenerParticipacion(@PathVariable Long idTrabajador, @PathVariable Long idSesion) {
        ParticipaId id = new ParticipaId(idTrabajador, idSesion);
        Optional<Participa> participa = participaRepository.findById(id);
        return participa.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener lista con filtros (alguna id null o vacia)
    @GetMapping("/buscar")
    public ResponseEntity<List<Participa>> filtrarParticipaciones(
            @RequestParam(required = false) Long idTrabajador,
            @RequestParam(required = false) Long idSesion) {

        List<Participa> resultados;

        if (idTrabajador != null && idSesion != null) {
            resultados = participaRepository.findByTrabajador_IdTrabajadorAndSesion_IdSesion(idTrabajador, idSesion);
        } else if (idTrabajador != null) {
            resultados = participaRepository.findByTrabajador_IdTrabajador(idTrabajador);
        } else if (idSesion != null) {
            resultados = participaRepository.findBySesion_IdSesion(idSesion);
        } else {
            resultados = participaRepository.findAll();
        }

        return ResponseEntity.ok(resultados);
    }

    // POST: Crear nueva participación
    @PostMapping("/crear")
    public Participa crearParticipacion(@RequestBody Participa participa) {
        return participaRepository.save(participa);
    }

    // PUT: Actualizar participación
    @PutMapping("/actualizar/{idTrabajador}/{idSesion}")
    public ResponseEntity<Participa> actualizarParticipacion(@PathVariable Long idTrabajador, @PathVariable Long idSesion,
                                                             @RequestBody Participa nuevaData) {
        ParticipaId id = new ParticipaId(idTrabajador, idSesion);
        Optional<Participa> existente = participaRepository.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Participa actual = existente.get();
        actual.setObservaciones(nuevaData.getObservaciones());
        actual.setPruebas(nuevaData.getPruebas());
        return ResponseEntity.ok(participaRepository.save(actual));
    }

    // DELETE: Eliminar participación
    @DeleteMapping("/eliminar/{idTrabajador}/{idSesion}")
    public ResponseEntity<Void> eliminarParticipacion(@PathVariable Long idTrabajador, @PathVariable Long idSesion) {
        ParticipaId id = new ParticipaId(idTrabajador, idSesion);
        if (!participaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        participaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
