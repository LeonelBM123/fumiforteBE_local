/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.AuthRequestDto;
import com.example.fumi_forte.models.SecurityUser;
import com.example.fumi_forte.services.BitacoraService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import static java.lang.System.console;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PC
 */
@RestController

public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @BitacoraLog("Login de usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ✅ Guarda la autenticación en la sesión manualmente
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
                    
            //mandarId
            // Obtener el ID del usuario autenticado
            Object principal = authentication.getPrincipal();
            Long userId = null;

            if (principal instanceof SecurityUser securityUser) {
                userId = securityUser.getUser().getIdUsuario(); // 👈 Aquí accedes al ID
                session.setAttribute("userId", userId);
            }
            
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            // Retornar las roles junto al mensaje
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Autenticación Exitosa");
            response.put("authorities", roles);
            response.put("userId", userId);
            return ResponseEntity.ok(response);
            //return ResponseEntity.ok(Collections.singletonMap("message","Autentificacion Exitosa"));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Credenciales Invalidas"));
        }
    }
    
    @GetMapping("/get_iduser")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
