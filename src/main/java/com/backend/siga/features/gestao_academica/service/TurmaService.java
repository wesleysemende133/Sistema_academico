package com.backend.siga.features.gestao_academica.service;

import com.backend.siga.features.gestao_academica.model.Aluno;
import com.backend.siga.features.gestao_academica.model.Turma;
import com.backend.siga.features.gestao_academica.model.enums.StatusAluno;
import com.backend.siga.features.gestao_academica.model.enums.StatusTurma;
import com.backend.siga.features.gestao_academica.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {
    
    private final TurmaRepository repository;
    private final AlunoService alunoService;
    
    @Transactional
    public Turma criar(String disciplina, String professor, Integer ano, 
                       Integer semestre, Integer vagas) {
        Turma turma = new Turma();
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        turma.setAno(ano);
        turma.setSemestre(semestre);
        turma.setVagas(vagas);
        turma.setStatus(StatusTurma.ABERTA);
        return repository.save(turma);
    }
    
    public List<Turma> listarTodos() {
        return repository.findAll();
    }
    
    public Turma buscarPorId(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }
    
    @Transactional
    public String matricularAluno(String turmaId, String alunoId) {
        Turma turma = buscarPorId(turmaId);
        Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
        
        if (turma.getStatus() != StatusTurma.ABERTA) {
            throw new RuntimeException("Turma não está aberta para matrículas");
        }
        
        if (turma.getAlunos().size() >= turma.getVagas()) {
            throw new RuntimeException("Turma lotada!");
        }
        
        if (aluno.getStatus() != StatusAluno.ATIVO) {
            throw new RuntimeException("Aluno não está ativo");
        }
        
        boolean jaMatriculado = turma.getAlunos().stream()
            .anyMatch(a -> a.getId().equals(alunoId));
        
        if (jaMatriculado) {
            throw new RuntimeException("Aluno já está matriculado");
        }
        
        turma.getAlunos().add(aluno);
        repository.save(turma);
        
        return "Aluno matriculado com sucesso!";
    }
}
