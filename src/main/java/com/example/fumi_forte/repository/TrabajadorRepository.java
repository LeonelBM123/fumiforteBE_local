package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Trabajador;
import com.example.fumi_forte.dto.TrabajadorDto;
import com.example.fumi_forte.dto.UsuarioTrabajadorDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByUsuario_IdUsuario(Long idUsuario);
    List<Trabajador> findByUsuario_Estado(String estado);
    
   @Query("SELECT new com.example.fumi_forte.dto.UsuarioTrabajadorDto(u.idUsuario, u.nombreCompleto, u.telefono, u.estado) " +
          "FROM Trabajador t JOIN t.usuario u WHERE u.estado = 'Activo'")
    List<UsuarioTrabajadorDto> findUsuariosTrabajadoresActivos();
}

