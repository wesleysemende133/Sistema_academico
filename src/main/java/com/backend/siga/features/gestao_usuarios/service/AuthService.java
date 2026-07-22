package com.backend.siga.features.gestao_usuarios.service;

import com.backend.siga.config.JwtUtil;
import com.backend.siga.features.gestao_usuarios.dto.CriarUsuarioRequest;
import com.backend.siga.features.gestao_usuarios.dto.LoginRequest;
import com.backend.siga.features.gestao_usuarios.dto.LoginResponse;
import com.backend.siga.features.gestao_usuarios.model.Usuario;
import com.backend.siga.features.gestao_usuarios.repository.UsuarioRepository;
import com.backend.siga.features.gestao_academica.model.Aluno;
import com.backend.siga.features.gestao_academica.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }
        
        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }
        
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());
        
        return new LoginResponse(
            token,
            usuario.getUsername(),
            usuario.getNomeCompleto(),
            usuario.getRole(),
            usuario.getAlunoId()
        );
    }
    
    @Transactional
    public Usuario criarUsuario(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já existe");
        }
        
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        
        if ("ALUNO".equals(request.getRole()) && request.getAlunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        }
        
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setRole(request.getRole());
        usuario.setAlunoId(request.getAlunoId());
        usuario.setAtivo(true);
        
        return usuarioRepository.save(usuario);
    }
}
