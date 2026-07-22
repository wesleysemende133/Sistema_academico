package com.backend.siga.features.gestao_usuarios.dto;

import lombok.Data;

@Data
public class CriarUsuarioRequest {
    private String username;
    private String email;
    private String password;
    private String nomeCompleto;
    private String role;
    private String alunoId;
}
