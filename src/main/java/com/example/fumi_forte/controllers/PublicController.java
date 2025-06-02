package com.example.fumi_forte.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pu")
@RequiredArgsConstructor
public class PublicController {
    @GetMapping("/home")
    public String home(){
        return "public home";
    }
}
