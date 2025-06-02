package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gerente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gerente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerente")
    private Long idGerente;

    @Column(name = "profesion", nullable = false, length = 50)
    private String profesion;

    @OneToOne
    @JoinColumn(name = "id_gerente", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
}
