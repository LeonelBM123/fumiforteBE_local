package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Sesion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findBySolicitudServicio_IdSolicitudServicio(Long idSolicitudServicio);
}
