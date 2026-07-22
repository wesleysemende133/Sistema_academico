package com.backend.siga.features.gestao_academica.dto;

import com.backend.siga.features.gestao_academica.model.Aluno;
import lombok.Data;

@Data
public class AlunoResponse {
    private String id;
    private String nome;
    private String email;
    private String bi;
    private String nuit;
    private String matricula;
    private String status;
    
    public static AlunoResponse from(Aluno aluno) {
        AlunoResponse response = new AlunoResponse();
        response.setId(aluno.getId());
        response.setNome(aluno.getNome());
        response.setEmail(aluno.getEmail());
        response.setBi(aluno.getBi());
        response.setNuit(aluno.getNuit());
        response.setMatricula("MZ" + aluno.getId().substring(0, 6).toUpperCase());
        response.setStatus(aluno.getStatus().name());
        return response;
    }
}
