/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.dto;

import com.example.fumi_forte.models.Sesion;
import java.util.List;
import lombok.Data;

@Data
public class ParticipaDto {
    private Sesion sesion;
    private List<Long> trabajadoresId;
}
