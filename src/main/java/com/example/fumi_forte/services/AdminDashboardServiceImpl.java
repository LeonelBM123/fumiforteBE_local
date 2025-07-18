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
    @Autowired private ProductoRepository productoRepository;

    @Override
    public AdminDashboardDto obtenerDatosDashboard() {
        KPIsDto kpis = calcularKPIs();

        List<TrabajadorDto> trabajadores = trabajadorRepository.findAllActivos();
        List<SolicitudServicioUsuarioDto> solicitudes = solicitudRepository.findSolicitudesPendientes();
        List<UsuarioClienteDto> clientes = usuarioRepository.findClientesConDatos(PageRequest.of(0, 5));
        List<SesionDto> calendario = sesionRepository.findSesionesFuturas();
        List<ProductoDto> inventario = productoRepository.findInventario();

        return new AdminDashboardDto(kpis, trabajadores, solicitudes, clientes, calendario, inventario);
    }

    private KPIsDto calcularKPIs() {
        long completados = sesionRepository.countByEstado("COMPLETADO");
        long programados = sesionRepository.countByEstado("PROGRAMADO");
        long solicitudesPendientes = solicitudRepository.countByEstado("PENDIENTE");
        BigDecimal ingresos = Optional.ofNullable(pagoRepository.sumMontosPagadosMesActual())
        .orElse(BigDecimal.ZERO);

        return new KPIsDto(completados, programados, solicitudesPendientes, ingresos != null ? ingresos : BigDecimal.ZERO);
    }
}
