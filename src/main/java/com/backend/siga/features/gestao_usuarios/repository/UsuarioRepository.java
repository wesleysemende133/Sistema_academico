package com.backend.siga.features.gestao_usuarios.repository;

import com.backend.siga.features.gestao_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByAlunoId(String alunoId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
