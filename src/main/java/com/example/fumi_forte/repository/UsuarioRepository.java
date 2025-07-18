package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.UsuarioClienteDto;
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
    
   @Query("""
        SELECT new com.example.fumi_forte.dto.UsuarioClienteDto(
            u.nombreCompleto,
            u.telefono,
            c.tipoCliente
        )
        FROM Cliente c
        JOIN c.usuario u
        ORDER BY u.idUsuario DESC
    """)
    List<UsuarioClienteDto> findClientesConDatos(Pageable pageable);
}
