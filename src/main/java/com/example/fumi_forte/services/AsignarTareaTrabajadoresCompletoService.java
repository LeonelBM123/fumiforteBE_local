package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.DatosFinalesTTDto;
import com.example.fumi_forte.dto.SesionDto;
import com.example.fumi_forte.models.*;
import com.example.fumi_forte.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AsignarTareaTrabajadoresCompletoService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private CotizacionRepository cotizacionRepo;
    @Autowired private SesionRepository sesionRepo;
    @Autowired private ParticipaRepository participaRepo;
    @Autowired private PagoRepository pagoRepo;
    @Autowired private PagoCotizacionRepository pagoCotiRepo;
    @Autowired private PagoSesionRepository pagoSesionRepo;
    @Autowired private SolicitudServicioRepository solicitudRepo;
    @Autowired private CertificadoRepository certificadoRepo;

    @Transactional
    public void procesarSolicitudCompleta(DatosFinalesTTDto datos) {
        // 1. Obtener ID del gerente logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario usuario = usuarioRepo.findByNombreCompleto(username);

        Long idGerente = usuario.getIdUsuario();

        // 2. Obtener solicitud
        SolicitudServicio solicitud = solicitudRepo.findById(datos.getIdSolicitudServicio())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 3. Crear cotización
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setCostoCotizacion(datos.getMontoPendienteCotizacion());
        cotizacion.setSolicitudServicio(solicitud);
        cotizacion.setGerente(new Gerente(idGerente));
        cotizacion = cotizacionRepo.save(cotizacion);

        // 4. Crear sesiones y asignar trabajadores
        List<Sesion> sesionesGuardadas = new ArrayList<>();

        for (SesionDto sesionDto : datos.getSesiones()) {
            Sesion sesion = new Sesion();
            sesion.setFecha(sesionDto.getFecha());
            sesion.setHora(sesionDto.getHora());
            sesion.setMontoPendienteSesion(sesionDto.getMonto());
            sesion.setEstado("Pendiente");
            sesion.setNroSesion(sesionDto.getSesion().shortValue());
            sesion.setSolicitudServicio(solicitud);

            sesion = sesionRepo.save(sesion);
            sesionesGuardadas.add(sesion);

            for (Long idTrabajador : sesionDto.getTrabajadores()) {
                Trabajador trabajador = new Trabajador(idTrabajador);
                participaRepo.save(new Participa(sesion, trabajador));
            }
        }

        // 5. Actualizar cantidad de sesiones
        solicitud.setCantidadSesiones((short) sesionesGuardadas.size());
        solicitudRepo.save(solicitud);

        // 6. Crear pagos
        if (datos.getMontoPendienteCotizacion().compareTo(BigDecimal.ZERO) > 0) {
            Cliente cliente = new Cliente(datos.getIdCliente());
            Pago pagoCotizacion = new Pago(datos.getMontoPendienteCotizacion(), null, null, cliente, "Inpaga");
            pagoCotizacion = pagoRepo.save(pagoCotizacion);
            pagoCotiRepo.save(new PagoCotizacion(pagoCotizacion, solicitud));
        }

        for (Sesion sesion : sesionesGuardadas) {
            if (sesion.getMontoPendienteSesion().compareTo(BigDecimal.ZERO) > 0) {
                Cliente cliente = new Cliente(datos.getIdCliente());
                Pago pagoSesion = new Pago(sesion.getMontoPendienteSesion(), null, null, cliente, "Inpaga");
                pagoSesion = pagoRepo.save(pagoSesion);
                pagoSesionRepo.save(new PagoSesion(pagoSesion, sesion));
            }
        }

        // 7. Crear certificado si es requerido y no existe
        if (solicitud.getIdCertificado() == null &&
            "Sí".equalsIgnoreCase(datos.getRequiereCertificado())) {

            CertificadoFumigacion cert = new CertificadoFumigacion();
            cert.setFechaEmision(null);
            cert.setFechaVencimiento(null);
            cert.setEstado("Pendiente");
            certificadoRepo.save(cert);

            solicitud.setIdCertificado(cert.getIdCertificado());
            solicitudRepo.save(solicitud);
        }

        // 8. Actualizar estado y monto pendiente
        solicitud.setIdGerente(idGerente);
        solicitud.setEstado("Aprobado");
        solicitud.setMontoPendienteCotizacion(datos.getMontoPendienteCotizacion());
        solicitudRepo.save(solicitud);
    }
}
