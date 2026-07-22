package com.backend.siga.features.gestao_academica.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AlunoRequest {
    private String nome;
    private String email;
    private String bi;
    private String nuit;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;
    private String provincia;
}
