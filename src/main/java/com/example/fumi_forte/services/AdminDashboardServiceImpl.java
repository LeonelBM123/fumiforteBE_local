package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.*;
import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.repository.*;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired private SesionRepository sesionRepository;
    @Autowired private SolicitudServicioRepository solicitudRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private TrabajadorRepository trabajadorRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public AdminDashboardDto obtenerDatosDashboard() {
        KPIsDto kpis = calcularKPIs();

        List<UsuarioTrabajadorDto> trabajadores = trabajadorRepository.findUsuariosTrabajadoresActivos();
        List<SolicitudServicioUsuarioDto> solicitudes = solicitudRepository.findUltimasSolicitudesPendientes(PageRequest.of(0, 10));
        List<UsuarioClienteDto> clientes = usuarioRepository.findClientesActivos(PageRequest.of(0, 10));
        List<SesionDto> calendario = sesionRepository.findSesionesFuturasDelMes();
        List<PagoDto> pagos = pagoRepository.findUltimosPagos(PageRequest.of(0, 10));


        return new AdminDashboardDto(kpis, trabajadores, solicitudes, clientes, calendario, pagos);
    }

    private KPIsDto calcularKPIs() {
        long completados = sesionRepository.countByEstadoEnMesActual("Realizado");
        long pendientes = sesionRepository.countByEstadoEnMesActual("Pendiente");
        long solicitudesPendientes = solicitudRepository.countByEstado("Pendiente");
        BigDecimal ingresos = Optional.ofNullable(pagoRepository.sumMontosPagadosMesActual())
        .orElse(BigDecimal.ZERO);

        return new KPIsDto(completados, pendientes, solicitudesPendientes, ingresos != null ? ingresos : BigDecimal.ZERO);
    }
}
