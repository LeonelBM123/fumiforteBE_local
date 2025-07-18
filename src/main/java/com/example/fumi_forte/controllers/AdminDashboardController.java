package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.AdminDashboardDto;
import com.example.fumi_forte.services.AdminDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @PreAuthorize("hasAuthority('Gerente')")
    @GetMapping
    public AdminDashboardDto obtenerDashboard() {
        return adminDashboardService.obtenerDatosDashboard();
    }
}