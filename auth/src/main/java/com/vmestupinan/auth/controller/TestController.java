package com.vmestupinan.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping
    public String protectedEndpoint() {
        return "Acceso concedido: estás autenticado.";
    }
}
