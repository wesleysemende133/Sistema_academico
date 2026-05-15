package com.backend.siga.model.atributos;

import java.time.LocalDate;

import com.backend.siga.model.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class DocumentoIdentificacao {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    /**
     * Regra de Negócio: Valida se o período entre emissão e validade é legal.
     * Para BI, o intervalo deve ser de exatamente 5 anos.
     */
    public boolean isPeriodoValido() {
        if (tipoDocumento == TipoDocumento.BI && dataEmissao != null && dataValidade != null) {
            // Verifica se a validade é exatamente 5 anos após a emissão
            return dataEmissao.plusYears(5).equals(dataValidade);
        }
        return true;
    }
}
