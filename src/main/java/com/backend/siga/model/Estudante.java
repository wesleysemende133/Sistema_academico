package com.backend.siga.model;

import java.time.LocalDate;

import com.backend.siga.model.atributos.DocumentoIdentificacao;
import com.backend.siga.model.atributos.Email;
import com.backend.siga.model.atributos.Endereco;
import com.backend.siga.model.atributos.Naturalidade;
import com.backend.siga.model.atributos.Filiacao;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * Email agora é um objeto de valor embutido. Validado via @Valid.
     */
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "email", unique = true, nullable = false, length = 150))
    @Valid
    @NotNull
    private Email email;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Size(max = 20)
    @Column(length = 20)
    private String telefone;

    @NotBlank
    @Size(min = 9, max = 9)
    @Column(unique = true, nullable = false, length = 9)
    private String nuit;

    @Embedded
    private DocumentoIdentificacao documento;

    @Embedded
    private Naturalidade naturalidade;

    // Filiação
    @Embedded
    private Filiacao filiacao;

    @Embedded
    private Endereco endereco;

}
    