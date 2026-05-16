package com.backend.siga.model.atributos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Filiacao {

    @Column(length = 150)
    private String nomePai;

    @Column(length = 150)
    private String nomeMae;

    protected Filiacao() {}

    public Filiacao(String nomePai, String nomeMae) {
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
    }

    public String getNomePai() { return nomePai; }
    public String getNomeMae() { return nomeMae; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filiacao)) return false;
        Filiacao that = (Filiacao) o;
        return Objects.equals(nomePai, that.nomePai) && Objects.equals(nomeMae, that.nomeMae);
    }

    @Override
    public int hashCode() { return Objects.hash(nomePai, nomeMae); }
}
