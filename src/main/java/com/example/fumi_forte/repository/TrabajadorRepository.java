package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Trabajador;
import com.example.fumi_forte.dto.TrabajadorDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByUsuario_IdUsuario(Long idUsuario);
    List<Trabajador> findByUsuario_Estado(String estado);
}

