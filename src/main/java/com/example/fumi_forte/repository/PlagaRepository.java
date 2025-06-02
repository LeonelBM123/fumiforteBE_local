/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Plaga;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PC
 */
public interface PlagaRepository extends JpaRepository<Plaga, Long>{
    Boolean existsByNombre(String Nombre); 
}
