package com.backend.siga.features.gestao_academica.controller;

import com.backend.siga.features.gestao_academica.model.Turma;
import com.backend.siga.features.gestao_academica.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaController {
    
    private final TurmaService turmaService;
    
    @PostMapping
    public ResponseEntity<Turma> criar(@RequestParam String disciplina,
                                       @RequestParam String professor,
                                       @RequestParam Integer ano,
                                       @RequestParam Integer semestre,
                                       @RequestParam Integer vagas) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(turmaService.criar(disciplina, professor, ano, semestre, vagas));
    }
    
    @GetMapping
    public ResponseEntity<List<Turma>> listarTodos() {
        return ResponseEntity.ok(turmaService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(turmaService.buscarPorId(id));
    }
}
