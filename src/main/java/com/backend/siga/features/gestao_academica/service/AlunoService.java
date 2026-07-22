package com.backend.siga.features.gestao_academica.service;

import com.backend.siga.features.gestao_academica.dto.AlunoRequest;
import com.backend.siga.features.gestao_academica.dto.AlunoResponse;
import com.backend.siga.features.gestao_academica.model.Aluno;
import com.backend.siga.features.gestao_academica.model.enums.StatusAluno;
import com.backend.siga.features.gestao_academica.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoService {
    
    private final AlunoRepository repository;
    
    @Transactional
    public AlunoResponse criar(AlunoRequest request) {
        if (repository.existsByBi(request.getBi())) {
            throw new RuntimeException("BI já cadastrado");
        }
        
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        if (request.getNuit() != null && !request.getNuit().isEmpty() && 
            repository.existsByNuit(request.getNuit())) {
            throw new RuntimeException("NUIT já cadastrado");
        }
        
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setBi(request.getBi());
        aluno.setNuit(request.getNuit());
        aluno.setDataNascimento(request.getDataNascimento());
        aluno.setTelefone(request.getTelefone());
        aluno.setEndereco(request.getEndereco());
        aluno.setProvincia(request.getProvincia());
        aluno.setStatus(StatusAluno.ATIVO);
        
        return AlunoResponse.from(repository.save(aluno));
    }
    
    public List<AlunoResponse> listarTodos() {
        return repository.findAll().stream()
            .map(AlunoResponse::from)
            .collect(Collectors.toList());
    }
    
    public AlunoResponse buscarPorId(String id) {
        Aluno aluno = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        return AlunoResponse.from(aluno);
    }
    
    public Aluno buscarAlunoPorId(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }
}
