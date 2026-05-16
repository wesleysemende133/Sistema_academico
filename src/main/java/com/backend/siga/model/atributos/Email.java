package com.backend.siga.model.atributos;

import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Representa um endereço de email válido como um objeto de valor.
 *
 * Usamos um Email embutido para encapsular a validação e evitar
 * uso direto de String para o atributo de email da entidade.
 */
@Embeddable
public class Email {

    private static final int MAX_LENGTH = 150;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    @Column(nullable = false, unique = true, length = MAX_LENGTH)
    private String valor;

    protected Email() {
        // Construtor protegido exigido pelo JPA para instanciar o embeddable.
    }

    private Email(String valor) {
        this.valor = validateEmail(valor);
    }

    public static Email of(String valor) {
        return new Email(valor);
    }

    public String getValor() {
        return valor;
    }

    private static String validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("O email não pode ser nulo.");
        }

        String trimmed = email.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("O email não pode estar vazio.");
        }

        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("O email não pode ter mais de " + MAX_LENGTH + " caracteres.");
        }

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("O email informado não é válido: " + email);
        }

        return trimmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
