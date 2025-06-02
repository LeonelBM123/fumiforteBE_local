package com.example.fumi_forte.services;

import com.example.fumi_forte.models.Bitacora;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.BitacoraRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class BitacoraService {
    @Autowired
    private final BitacoraRepository bitacoraRepository;
    @Autowired
    private final UsuarioRepository usuarios;
    public void registrar(String ip, String accion) {
        Bitacora bitacora = new Bitacora();
        bitacora.setAccion(accion);
        bitacora.setFechaHora(LocalDateTime.now());
        bitacora.setIp(ip);

        // Aquí puedes buscar el usuario logueado, si lo necesitas
        // Ejemplo:
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null && auth.isAuthenticated()) {
             String username = auth.getName();
             Usuario usuario = usuarios.findByNombreCompleto(username);
             bitacora.setIdUsuario(usuario.getIdUsuario());
         }
        bitacoraRepository.save(bitacora);
    }
}