package com.backend.siga.model.atributos;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Endereco {
    private String rua;
    private String numeroCasa;
    private String bairro;
    private String distrito;
}
