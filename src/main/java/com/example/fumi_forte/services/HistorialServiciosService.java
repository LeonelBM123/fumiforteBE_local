package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.HistorialSesionesDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HistorialServiciosService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<HistorialSesionesDto> obtenerSesiones() {
        String sql = """
                     SELECT 
                       sesion.id_solicitud_servicio, 
                       sesion.id_sesion,
                       sesion.nro_sesion,
                       string_agg(trabajador_usuario.nombre_completo, ', ') AS trabajadores,
                       sesion.fecha, 
                       sesion.hora,
                       cliente_usuario.nombre_completo AS nombre_cliente,
                       cliente_usuario.telefono,
                       solicitud_servicio.direccion_escrita, 
                       cliente.tipo_cliente, 
                       sesion.monto_pendiente_sesion,
                       COALESCE(pago.estado, 'Sin pago') AS estado_pago,
                       historial.certificado,
                       historial.factura
                     FROM sesion
                     JOIN solicitud_servicio ON sesion.id_solicitud_servicio = solicitud_servicio.id_solicitud_servicio
                     JOIN cliente ON solicitud_servicio.id_cliente = cliente.id_cliente
                     JOIN usuario AS cliente_usuario ON cliente.id_cliente = cliente_usuario.id_usuario
                     LEFT JOIN participa ON sesion.id_sesion = participa.id_sesion
                     LEFT JOIN usuario AS trabajador_usuario ON participa.id_trabajador = trabajador_usuario.id_usuario
                     LEFT JOIN pago_sesion ON sesion.id_sesion = pago_sesion.id_sesion
                     LEFT JOIN pago ON pago_sesion.id_pago = pago.id_pago
                     LEFT JOIN historial_servicio AS historial ON historial.id_sesion = sesion.id_sesion
                     GROUP BY 
                       sesion.id_solicitud_servicio, 
                       sesion.id_sesion,
                       sesion.nro_sesion, 
                       sesion.fecha, 
                       sesion.hora, 
                       cliente_usuario.nombre_completo,
                       cliente_usuario.telefono,
                       solicitud_servicio.direccion_escrita, 
                       cliente.tipo_cliente, 
                       sesion.monto_pendiente_sesion,
                       pago.estado,
                       historial.certificado,
                       historial.factura
                     ORDER BY sesion.id_solicitud_servicio DESC, sesion.nro_sesion;
                     """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            HistorialSesionesDto dto = new HistorialSesionesDto();
            dto.setIdSolicitudServicio(rs.getInt("id_solicitud_servicio"));
            dto.setIdSesion(rs.getInt("id_sesion"));
            dto.setNroSesion(rs.getInt("nro_sesion"));
            dto.setTrabajadores(rs.getString("trabajadores"));
            dto.setFecha(rs.getDate("fecha").toLocalDate());
            dto.setHora(rs.getTime("hora").toLocalTime());
            dto.setNombreCliente(rs.getString("nombre_cliente"));
            dto.setTelefono(rs.getString("telefono"));
            dto.setDireccionEscrita(rs.getString("direccion_escrita"));
            dto.setTipoCliente(rs.getString("tipo_cliente"));
            dto.setMontoPendienteSesion(rs.getBigDecimal("monto_pendiente_sesion"));
            dto.setEstadoPago(rs.getString("estado_pago"));
            dto.setFactura(rs.getString("factura"));
            dto.setCertificado(rs.getString("certificado"));

            return dto;
        });
    }
}