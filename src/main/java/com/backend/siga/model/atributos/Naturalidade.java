package com.backend.siga.model.atributos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Naturalidade {

    @Column(length = 100)
    private String nacionalidade;

    @Column(length = 100)
    private String provincia;

    @Column(length = 100)
    private String distrito;

    protected Naturalidade() {
    }

    public Naturalidade(String nacionalidade, String provincia, String distrito) {
        this.nacionalidade = nacionalidade;
        this.provincia = provincia;
        this.distrito = distrito;
    }

    public String getNacionalidade() { return nacionalidade; }
    public String getProvincia() { return provincia; }
    public String getDistrito() { return distrito; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Naturalidade)) return false;
        Naturalidade that = (Naturalidade) o;
        return Objects.equals(nacionalidade, that.nacionalidade) &&
                Objects.equals(provincia, that.provincia) &&
                Objects.equals(distrito, that.distrito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nacionalidade, provincia, distrito);
    }
}
