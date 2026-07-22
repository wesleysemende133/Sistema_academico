package com.backend.siga.features.gestao_academica.repository;

import com.backend.siga.features.gestao_academica.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByBi(String bi);
    Optional<Aluno> findByNuit(String nuit);
    boolean existsByBi(String bi);
    boolean existsByEmail(String email);
    boolean existsByNuit(String nuit);
}
