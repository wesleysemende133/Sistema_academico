package com.backend.siga.features.gestao_academica.repository;

import com.backend.siga.features.gestao_academica.model.Turma;
import com.backend.siga.features.gestao_academica.model.enums.StatusTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, String> {
    List<Turma> findByStatus(StatusTurma status);
    List<Turma> findByProfessor(String professor);
    List<Turma> findByAnoAndSemestre(Integer ano, Integer semestre);
}
