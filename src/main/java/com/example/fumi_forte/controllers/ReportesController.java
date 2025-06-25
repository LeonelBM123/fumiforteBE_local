/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.CertificadoReporteDto;
import com.example.fumi_forte.dto.UsuarioReporteDto;
import com.example.fumi_forte.services.CertificadoReporteService;
import com.example.fumi_forte.services.UsuarioReporteService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReportesController {
    @Autowired
    private UsuarioReporteService usuarioService;

    @PostMapping("/usuario")
    public List<UsuarioReporteDto> buscarUsuarios(
        @RequestBody Map<String, String> filtros) {
        String nombre = "%"+filtros.get("nombre")+"%";
        String estado = "%"+filtros.get("estado")+"%";
        String rol = "%"+filtros.get("rol")+"%";
        return usuarioService.buscarUsuarios(nombre, estado, rol);
    }
    
    private final CertificadoReporteService certificadoService;

    @PostMapping("/certificados")
    public List<CertificadoReporteDto> buscarPorEstado(@RequestBody Map<String, String> filtros) {
        String estado = filtros.get("estado");
        return certificadoService.buscarPorEstado(estado);
    }
}
