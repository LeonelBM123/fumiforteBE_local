package com.example.fumi_forte.controllers;

// Imports de Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// Imports de Java
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Imports de tus modelos y repositorios
import com.example.fumi_forte.models.Calificacion;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.CalificacionRepository;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import com.example.fumi_forte.repository.UsuarioRepository;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/calificar/{idSolicitud}")
    public ResponseEntity<String> calificar(
            @PathVariable Long idSolicitud,
            @RequestBody Calificacion calificacionEntrada) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Usuario usuario = usuarioRepository.findByNombreCompleto(username);

            Optional<SolicitudServicio> optional = solicitudServicioRepository.findById(idSolicitud);
            if (optional.isEmpty()) return ResponseEntity.notFound().build();

            SolicitudServicio solicitud = optional.get();

            // Verifica que el cliente sea el dueño y el estado esté aprobado
            if (!solicitud.getIdCliente().equals(usuario.getIdUsuario())
                || !"aprobado".equalsIgnoreCase(solicitud.getEstado())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para calificar esta solicitud");
            }

            // Evita calificación duplicada
            if (!calificacionRepository.findBySolicitudId(idSolicitud).isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Esta solicitud ya fue calificada");
            }

            Calificacion calificacion = new Calificacion();
            calificacion.setSolicitud(solicitud);
            calificacion.setPuntuacion(calificacionEntrada.getPuntuacion());
            calificacion.setComentario(calificacionEntrada.getComentario());
            calificacion.setFechaCalificacion(LocalDateTime.now());

            calificacionRepository.save(calificacion);
            return ResponseEntity.ok("Calificación registrada correctamente");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // También podrías tener un GET para ver las calificaciones de una solicitud
    @GetMapping("/porSolicitud/{idSolicitud}")
    public List<Calificacion> getCalificacionesPorSolicitud(@PathVariable Long idSolicitud) {
        return calificacionRepository.findBySolicitudId(idSolicitud);
    }
}