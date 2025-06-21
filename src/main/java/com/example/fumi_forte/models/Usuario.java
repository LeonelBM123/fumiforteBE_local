package com.example.fumi_forte.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name="usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String direccion;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "contraseña", nullable = false, length = 100)
    private String contraseña;

    @Column(nullable = false, length = 50)
    private String rol;
    
    @Column(name= "estado", nullable = false, length = 15)
    private String estado;
}
