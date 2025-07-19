package com.example.fumi_forte.dto;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
public class AdminDashboardDto {
    private KPIsDto kpis;
    private List<UsuarioTrabajadorDto> trabajadores;
    private List<SolicitudServicioUsuarioDto> solicitudes;
    private List<UsuarioClienteDto> clientes;
    private List<SesionDto> calendario;
    private List<PagoDto> pagos;
}
