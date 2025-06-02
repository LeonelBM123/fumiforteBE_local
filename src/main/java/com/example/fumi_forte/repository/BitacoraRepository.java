/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PC
 */
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    
//    // Todas las bitácoras, ordenadas por fecha DESC (el más nuevo primero)
//    List<Bitacora> findAllByOrderByFechaHoraDesc();
//
//    // Bitácoras de un usuario específico, ordenadas por fecha
//    List<Bitacora> findByUsuarioIdUsuarioOrderByFechaHoraDesc(Long idUsuario);
}
