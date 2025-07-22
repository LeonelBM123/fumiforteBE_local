package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.UsuarioClienteDto;
import com.example.fumi_forte.dto.UsuarioReporteDto;
import com.example.fumi_forte.dto.UsuarioTrabajadorDto;
import com.example.fumi_forte.models.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    Usuario findByCorreo(String Correo);
    Boolean existsByCorreo(String Correo);
    Usuario findByNombreCompleto(String nombreCompleto);
    List<Usuario> findByRol(String rol);
    
    @Query("SELECT new com.example.fumi_forte.dto.UsuarioClienteDto(u.idUsuario, u.nombreCompleto, u.telefono, u.estado, c.tipoCliente) " +
            "FROM Cliente c JOIN c.usuario u " +
            "WHERE u.rol = 'Cliente' AND u.estado = 'Activo' " +
            "ORDER BY u.idUsuario DESC")
    List<UsuarioClienteDto> findClientesActivos(Pageable pageable);

    
    @Query(
        value = "SELECT new com.example.fumi_forte.dto.UsuarioReporteDto(u.idUsuario, u.nombreCompleto, u.correo, u.estado, u.rol, u.telefono, u.direccion) FROM Usuario u",
        countQuery = "SELECT count(u) FROM Usuario u"
    )
    Page<UsuarioReporteDto> findAllUsuariosPages(Pageable pageable);
}
