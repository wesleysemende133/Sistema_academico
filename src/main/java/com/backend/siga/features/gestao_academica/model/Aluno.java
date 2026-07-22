package com.backend.siga.features.gestao_academica.model;

import com.backend.siga.features.gestao_academica.model.enums.StatusAluno;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "alunos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(unique = true, nullable = false)
    private String bi;
    
    @Column(unique = true)
    private String nuit;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    private String telefone;
    private String endereco;
    private String provincia;
    
    @Enumerated(EnumType.STRING)
    private StatusAluno status = StatusAluno.ATIVO;
    
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;
    
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
    
    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
