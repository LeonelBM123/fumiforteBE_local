package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.CertificadoFumigacion;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.repository.CertificadoRepository;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gerente")
public class CertificadoController {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;
    
    // Obtener todos los certificados
    @GetMapping("/listar_certificados")
    public List<CertificadoFumigacion> obtenerTodosLosCertificados() {
        return certificadoRepository.findAll();
    }

    // Actualizar un certificado por id (PUT)
    @PutMapping("/actualizar_certificado/{id}")
    public ResponseEntity<CertificadoFumigacion> actualizarCertificado(
            @PathVariable Long id,
            @RequestBody CertificadoFumigacion datosActualizados) {

        Optional<CertificadoFumigacion> optionalCertificado = certificadoRepository.findById(id);
        if (!optionalCertificado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CertificadoFumigacion certificado = optionalCertificado.get();
        certificado.setFechaEmision(datosActualizados.getFechaEmision());
        certificado.setFechaVencimiento(datosActualizados.getFechaVencimiento());
        certificado.setEstado(datosActualizados.getEstado());

        certificadoRepository.save(certificado);
        return ResponseEntity.ok(certificado);
    }

    // Eliminar un certificado por id (DELETE)
    @DeleteMapping("/eliminar_certificado/{id}")
    public ResponseEntity<Void> eliminarCertificado(@PathVariable Long id) {
        Optional<CertificadoFumigacion> optionalCertificado = certificadoRepository.findById(id);
        if (!optionalCertificado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Buscar solicitudes que usen este certificado
        List<SolicitudServicio> solicitudes = solicitudServicioRepository.findByIdCertificado(id);

        // Desligar el certificado de cada solicitud
        for (SolicitudServicio solicitud : solicitudes) {
            solicitud.setIdCertificado(null);
            solicitudServicioRepository.save(solicitud);
        }

        // Ahora sí, eliminar el certificado
        certificadoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Crear un certificado nuevo (POST)
    @PostMapping("/crear_certificado")
    public ResponseEntity<CertificadoFumigacion> crearCertificado(@RequestBody CertificadoFumigacion nuevoCertificado) {
        CertificadoFumigacion certificadoCreado = certificadoRepository.save(nuevoCertificado);
        return ResponseEntity.ok(certificadoCreado);
    }
}
