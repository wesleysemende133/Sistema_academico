package com.backend.siga.config;

import com.backend.siga.features.gestao_usuarios.model.Usuario;
import com.backend.siga.features.gestao_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@siga.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNomeCompleto("Administrador");
            admin.setRole("ADMIN");
            admin.setAtivo(true);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuário admin criado: admin / admin123");
        }
        
        if (!usuarioRepository.existsByUsername("aluno")) {
            Usuario aluno = new Usuario();
            aluno.setUsername("aluno");
            aluno.setEmail("aluno@siga.com");
            aluno.setPassword(passwordEncoder.encode("aluno123"));
            aluno.setNomeCompleto("Aluno Teste");
            aluno.setRole("ALUNO");
            aluno.setAtivo(true);
            usuarioRepository.save(aluno);
            System.out.println("✅ Usuário aluno criado: aluno / aluno123");
        }
    }
}
