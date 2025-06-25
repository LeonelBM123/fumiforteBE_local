
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.CertificadoReporteDto;
import com.example.fumi_forte.dto.PagoSesionReporteDto;
import com.example.fumi_forte.dto.ProductoReporteDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProductoReporteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductoReporteDto> obtenerTodos() {
        String sql = "SELECT * FROM producto";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProductoReporteDto(
            rs.getLong("id_producto"),
            rs.getString("nombre"),
            rs.getObject("fecha_vencimiento", LocalDate.class),
            rs.getString("descripcion"),
            rs.getInt("stock"),
            rs.getString("unidad_medida")
        ));
    }
}
