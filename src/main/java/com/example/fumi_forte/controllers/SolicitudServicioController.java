package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.ListaSolicitudesMontoSesionDto;
import com.example.fumi_forte.dto.MontosPendientesDto;
import com.example.fumi_forte.dto.SesionMontoDto;
import com.example.fumi_forte.dto.SolicitudServicioUsuarioDto;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.models.Cliente;
import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.repository.ClienteRepository;
import com.example.fumi_forte.repository.SesionRepository;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController

public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;
    @Autowired
    private UsuarioRepository usuarios;
    @Autowired
    private ClienteRepository clientes;
    @Autowired
    private SesionRepository sesionRepository;

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

    // Obtener solicitud por id
    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudServicio> getSolicitudById(@PathVariable Long id) {
        Optional<SolicitudServicio> solicitud = solicitudServicioRepository.findById(id);
        if (solicitud.isPresent()) {
            return ResponseEntity.ok(solicitud.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   
    // PUT: Modificar solicitud de servicio especifica
    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<?> actualizarSolicitudServicio(@PathVariable Long id, @RequestBody SolicitudServicio solicitudActualizado) {
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
        // Creo tantas sesiones como
        solicitudExistente.setRequiereCertificado(solicitudActualizado.getRequiereCertificado());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName(); // luego cambiar por el correo para que no existan duplicados
            Usuario usuario = usuarios.findByNombreCompleto(username);
            solicitudExistente.setIdGerente(usuario.getIdUsuario());
        }
        solicitudExistente.setIdCertificado(solicitudActualizado.getIdCertificado());
        SolicitudServicio solicitudModificado = solicitudServicioRepository.save(solicitudExistente);
        return ResponseEntity.ok(solicitudModificado);
    }
    
    // PUT: Modificar el monto de la solicitud de servicio especifica
    @PutMapping("/solicitudes/actualizar_monto/{id}")
    public ResponseEntity<?> actualizarMontoSS(@PathVariable Long id, @RequestBody SolicitudServicio solicitudActualizado) {
        Optional<SolicitudServicio> solicitudOptional = solicitudServicioRepository.findById(id);

        if (!solicitudOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudServicio solicitudExistente = solicitudOptional.get();

        solicitudExistente.setMontoPendienteCotizacion(solicitudActualizado.getMontoPendienteCotizacion());
        SolicitudServicio solicitudModificado = solicitudServicioRepository.save(solicitudExistente);
        
        return ResponseEntity.ok(solicitudModificado);
    }
    
    // PUT: Modificar el nro sesiones de la solicitud de servicio especifica
    @PutMapping("/solicitudes/actualizar_numero_sesiones/{id}")
    public ResponseEntity<?> actualizarNumeroSesionesSS(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Optional<SolicitudServicio> solicitudOptional = solicitudServicioRepository.findById(id);

        if (!solicitudOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudServicio solicitudExistente = solicitudOptional.get();

        Integer cantidadSesiones = body.get("cantidadSesiones");
        solicitudExistente.setCantidadSesiones(cantidadSesiones.shortValue()); // convertir a Short si hace falta

        SolicitudServicio solicitudModificado = solicitudServicioRepository.save(solicitudExistente);

        return ResponseEntity.ok(solicitudModificado);
    }

    

    // GET: Obtener datos completos de la solicitud de servicio especifica
    @GetMapping("/solicitud_servicio_detallado/{id}")
    public ResponseEntity<SolicitudServicioUsuarioDto> getSolicitudCompletaByID(@PathVariable Long id) {
        Optional<SolicitudServicio> optionalSolicitud = solicitudServicioRepository.findById(id);

        if (!optionalSolicitud.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudServicio solicitud = optionalSolicitud.get();

        // Obtener el cliente usando el id_cliente que ya viene en la solicitud
        Long idCliente = solicitud.getIdCliente();
        Optional<Cliente> optionalCliente = clientes.findById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = optionalCliente.get();
        Usuario usuario = cliente.getUsuario();

        // Construir el DTO
        SolicitudServicioUsuarioDto dto = new SolicitudServicioUsuarioDto();
        dto.setIdSolicitudServicio(solicitud.getIdSolicitudServicio());
        dto.setIdCliente(idCliente);
        dto.setNombre(usuario.getNombreCompleto());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setTipoCliente(cliente.getTipoCliente());
        dto.setEstado(solicitud.getEstado());
        dto.setDireccionEscrita(solicitud.getDireccionEscrita());
        dto.setUbicacionGps(solicitud.getUbicacionGps());

        return ResponseEntity.ok(dto);
    }

    // GET: Obtiene todas las solicitudes realizadas por un cliente especifico
    @GetMapping("/solicitudes/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudServicio>> getSolicitudesByClienteId(@PathVariable Long idCliente) {
        List<SolicitudServicio> solicitudes = solicitudServicioRepository.findByIdCliente(idCliente);

        if (solicitudes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si no hay nada
        }

        return ResponseEntity.ok(solicitudes); // 200 con la lista
    }

    // GET: Obtener el monto pendiente de cotizacion y sesion de un cliente especifico y servicio especifico
    @GetMapping("/solicitudes/monto_pendiente/{idCliente}/{idSolicitudServicio}")
    public ResponseEntity<?> obtenerMontosPendientes(
        @PathVariable Long idCliente,
        @PathVariable Long idSolicitudServicio) {

        Optional<SolicitudServicio> solicitudOpt = solicitudServicioRepository.findById(idSolicitudServicio);

        if (!solicitudOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudServicio solicitud = solicitudOpt.get();

        if (!solicitud.getIdCliente().equals(idCliente)) {
            return ResponseEntity.badRequest().body("El cliente no tiene acceso a esta solicitud.");
        }

        BigDecimal montoCotizacion = solicitud.getMontoPendienteCotizacion();

        List<Sesion> sesiones = sesionRepository.findBySolicitudServicio_IdSolicitudServicio(idSolicitudServicio);

        List<BigDecimal> montosSesion = sesiones.stream()
                .map(Sesion::getMontoPendienteSesion)
                .collect(Collectors.toList());

        List<String> estadosSesion = sesiones.stream()
                .map(Sesion::getEstado)
                .collect(Collectors.toList());

        MontosPendientesDto respuesta = new MontosPendientesDto(
            solicitud.getIdCliente(),
            solicitud.getIdSolicitudServicio(),
            montoCotizacion,
            montosSesion,
            estadosSesion
        );
        return ResponseEntity.ok(respuesta);
    }

    // GET: Obtener lista de montos pendientes de sesiones dado un id cliente especifico
    @GetMapping("/solicitudes/monto_pendiente_sesiones/{idCliente}")
    public ResponseEntity<?> obtenerMontosPendientesSesiones(@PathVariable Long idCliente) {

        // 1. Traer todas las solicitudes de ese cliente
        List<SolicitudServicio> solicitudes = solicitudServicioRepository.findByIdCliente(idCliente);

        if (solicitudes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // 2. Construir la respuesta
        List<ListaSolicitudesMontoSesionDto> respuesta = solicitudes.stream().map(solicitud -> {
            List<Sesion> sesiones = sesionRepository
                    .findBySolicitudServicio_IdSolicitudServicio(solicitud.getIdSolicitudServicio());

            List<SesionMontoDto> sesionesDto = sesiones.stream()
                    .map(s -> new SesionMontoDto(s.getIdSesion(), s.getMontoPendienteSesion(), s.getEstado()))
                    .toList();

            return new ListaSolicitudesMontoSesionDto(solicitud.getIdSolicitudServicio(), sesionesDto);
        }).toList();

        return ResponseEntity.ok(respuesta);
    }

    // NUEVO: Obtener solicitudes del cliente autenticado
    @GetMapping("/clienteSolicitudes")
    public List<SolicitudServicio> getSolicitudesDelCliente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Usuario usuario = usuarios.findByNombreCompleto(username);
            return solicitudServicioRepository.findByIdClienteAndEstado(usuario.getIdUsuario(), "Aprobado");
        }
        return List.of();
    }
}