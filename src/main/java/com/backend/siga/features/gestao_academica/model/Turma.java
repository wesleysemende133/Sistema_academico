package com.backend.siga.features.gestao_academica.model;

import com.backend.siga.features.gestao_academica.model.enums.StatusTurma;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String disciplina;
    
    @Column(nullable = false)
    private String professor;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false)
    private Integer semestre;
    
    @Column(nullable = false)
    private Integer vagas;
    
    @Enumerated(EnumType.STRING)
    private StatusTurma status = StatusTurma.ABERTA;
    
    @ManyToMany
    @JoinTable(
        name = "turma_alunos",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos = new ArrayList<>();
    
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
