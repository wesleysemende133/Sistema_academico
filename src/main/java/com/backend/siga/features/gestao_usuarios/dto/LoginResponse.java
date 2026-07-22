package com.backend.siga.features.gestao_usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String nome;
    private String role;
    private String alunoId;
}
