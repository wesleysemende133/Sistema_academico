package com.backend.siga.features.gestao_academica.controller;

import com.backend.siga.features.gestao_academica.dto.AlunoRequest;
import com.backend.siga.features.gestao_academica.dto.AlunoResponse;
import com.backend.siga.features.gestao_academica.service.AlunoService;
import com.backend.siga.features.gestao_academica.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {
    
    private final AlunoService alunoService;
    private final TurmaService turmaService;
    
    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@RequestBody AlunoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(alunoService.criar(request));
    }
    
    @GetMapping
    public ResponseEntity<List<AlunoResponse>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }
    
    @PostMapping("/{alunoId}/matricular/{turmaId}")
    public ResponseEntity<String> matricular(@PathVariable String alunoId, 
                                            @PathVariable String turmaId) {
        String resultado = turmaService.matricularAluno(turmaId, alunoId);
        return ResponseEntity.ok(resultado);
    }
}
