package com.backend.siga.model;

import java.time.LocalDate;

import com.backend.siga.model.atributos.DocumentoIdentificacao;
import com.backend.siga.model.atributos.Email;
import com.backend.siga.model.atributos.Endereco;
import com.backend.siga.model.enums.Sexo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudantes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudante {
    
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * Email agora é um objeto de valor embutido.
     *
     * @AttributeOverride garante que o valor interno 'valor'
     * seja persistido na coluna 'email' com as restrições corretas.
     */
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "email", unique = true, nullable = false, length = 150))
    private Email email;

    @Column(nullable = false)
    private LocalDate dataNascimento; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    private String telefone;

    @Column(unique = true, nullable = false, length = 9)
    private String nuit;

    @Embedded
    private DocumentoIdentificacao documento;

    // Atributos de Naturalidade (Podem ser agrupados)
    private String nacionalidade;
    private String provincia;
    private String distrito;

    // Filiação
    private String nomePai;
    private String nomeMae;

    @Embedded
    private Endereco endereco;
}
    