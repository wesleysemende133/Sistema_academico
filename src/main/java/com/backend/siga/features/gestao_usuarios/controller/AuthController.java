package com.backend.siga.features.gestao_usuarios.controller;

import com.backend.siga.features.gestao_usuarios.dto.CriarUsuarioRequest;
import com.backend.siga.features.gestao_usuarios.dto.LoginRequest;
import com.backend.siga.features.gestao_usuarios.dto.LoginResponse;
import com.backend.siga.features.gestao_usuarios.model.Usuario;
import com.backend.siga.features.gestao_usuarios.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody CriarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.criarUsuario(request));
    }
}
