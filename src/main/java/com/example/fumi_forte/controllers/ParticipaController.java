package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.ParticipaDto;
import com.example.fumi_forte.models.Participa;
import com.example.fumi_forte.models.ParticipaId;
import com.example.fumi_forte.dto.TrabajadorDto;
import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.models.Trabajador;
import com.example.fumi_forte.repository.ParticipaRepository;
import com.example.fumi_forte.repository.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/participa")
@RequiredArgsConstructor
public class ParticipaController {

    @Autowired
    private ParticipaRepository participaRepository;
    
    @Autowired
    private TrabajadorRepository trabajadorRepository;

    // GET: Listar todas las participaciones
    @GetMapping("/listar")
    public List<ParticipaDto> listarParticipaciones() {
        List<Participa> participaciones = participaRepository.findAll();

        // Agrupar por sesión
        Map<Sesion, List<Participa>> mapa = participaciones.stream()
                .collect(Collectors.groupingBy(Participa::getSesion));

        // Transformar a ParticipaDto
        return mapa.entrySet().stream().map(entry -> {
            Sesion sesion = entry.getKey();
            List<Participa> participas = entry.getValue();

            List<TrabajadorDto> trabajadores = participas.stream().map(participa -> {
                Trabajador trabajador = participa.getTrabajador();
                TrabajadorDto dto = new TrabajadorDto();
                dto.setIdTrabajador(trabajador.getIdTrabajador());
                dto.setNombreCompleto(trabajador.getUsuario().getNombreCompleto());
                dto.setCorreo(trabajador.getUsuario().getCorreo());
                dto.setTelefono(trabajador.getUsuario().getTelefono());
                dto.setEspecialidad(trabajador.getEspecialidad());
                return dto;
            }).collect(Collectors.toList());

            ParticipaDto participaDto = new ParticipaDto();
            participaDto.setSesion(sesion);
            participaDto.setTrabajadores(trabajadores);
            return participaDto;
        }).collect(Collectors.toList());
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
    
    //GET: Lista los trabajadores segun el TrabajadorDto
    @GetMapping("/listar_trabajadores_tareas")
    public List<TrabajadorDto> listarTrabajadores() {
        List<Trabajador> trabajadores = trabajadorRepository.findAll();

        return trabajadores.stream().map(trabajador -> {
            TrabajadorDto dto = new TrabajadorDto();
            dto.setIdTrabajador(trabajador.getIdTrabajador());
            dto.setNombreCompleto(trabajador.getUsuario().getNombreCompleto());
            dto.setCorreo(trabajador.getUsuario().getCorreo());
            dto.setTelefono(trabajador.getUsuario().getTelefono());
            dto.setEspecialidad(trabajador.getEspecialidad());
            return dto;
        }).collect(Collectors.toList());
    }
}
