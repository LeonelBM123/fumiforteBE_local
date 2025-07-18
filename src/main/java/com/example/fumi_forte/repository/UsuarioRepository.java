package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.UsuarioClienteDto;
import com.example.fumi_forte.dto.UsuarioTrabajadorDto;
import com.example.fumi_forte.models.Usuario;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByCorreo(String Correo);
    Boolean existsByCorreo(String Correo);
    Usuario findByNombreCompleto(String nombreCompleto);
    List<Usuario> findByRol(String rol);
    
    @Query("SELECT new com.example.fumi_forte.dto.UsuarioClienteDto(u.idUsuario, u.nombreCompleto, u.telefono, u.estado, c.tipoCliente) " +
            "FROM Cliente c JOIN c.usuario u " +
            "WHERE u.rol = 'Cliente' AND u.estado = 'Activo' " +
            "ORDER BY u.idUsuario DESC")
    List<UsuarioClienteDto> findClientesActivos(Pageable pageable);

}
