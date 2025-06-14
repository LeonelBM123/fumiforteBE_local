package com.example.fumi_forte.dto;

import com.example.fumi_forte.models.Sesion;
import java.util.List;
import lombok.Data;

@Data
public class ParticipaDto {
    private Sesion sesion;
    private List<TrabajadorDto> trabajadores;
}
