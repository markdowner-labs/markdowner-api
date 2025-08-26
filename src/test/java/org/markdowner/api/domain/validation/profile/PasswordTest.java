package org.markdowner.api.domain.validation.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class PasswordTest {

    @Test
    @DisplayName("Deve aceitar senha válida com tamanho mínimo")
    void shouldAcceptValidPassword() {
        final var validPassword = "abcdefgh"; // 8 chars
        assertDoesNotThrow(() -> Password.Validation.validate(validPassword));
    }

    @Test
    @DisplayName("Deve aceitar senha com tamanho máximo permitido")
    void shouldAcceptPasswordWithMaxLength() {
        final var maxPassword = "a".repeat(Password.MAX_SIZE); // 100 chars
        assertDoesNotThrow(() -> Password.Validation.validate(maxPassword));
    }

    @Test
    @DisplayName("Deve lançar exceção para senha muito curta")
    void shouldThrowForShortPassword() {
        final var shortPassword = "abc"; // less than 8
        final var description = "Senha muito curta";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Password.Validation.validate(shortPassword);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve conter entre 8 e 100 caracteres");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para senha muito longa")
    void shouldThrowForLongPassword() {
        final var longPassword = "a".repeat(Password.MAX_SIZE + 1); // 101 chars
        final var description = "Senha muito longa";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Password.Validation.validate(longPassword);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve conter entre 8 e 100 caracteres");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para senha nula")
    void shouldThrowForNullPassword() {
        final var description = "Senha nula";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Password.Validation.validate(null);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("não deve ser nulo");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve aceitar senha com tamanho igual ao mínimo permitido")
    void shouldAcceptPasswordWithMinLength() {
        final var minPassword = "a".repeat(Password.MIN_SIZE); // 8 chars
        assertDoesNotThrow(() -> Password.Validation.validate(minPassword));
    }

    @Test
    @DisplayName("Deve lançar exceção para senha com tamanho menor que o mínimo permitido")
    void shouldThrowForPasswordWithLengthLessThanMinSize() {
        final var tooShortPassword = "a".repeat(Password.MIN_SIZE - 1); // 7 chars
        final var description = "Senha menor que o mínimo permitido";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Password.Validation.validate(tooShortPassword);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve conter entre 8 e 100 caracteres");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para senha com tamanho maior que o máximo permitido")
    void shouldThrowForPasswordWithLengthGreaterThanMaxSize() {
        final var tooLongPassword = "a".repeat(Password.MAX_SIZE + 1); // 101 chars
        final var description = "Senha maior que o máximo permitido";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Password.Validation.validate(tooLongPassword);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve conter entre 8 e 100 caracteres");
        assertThat(validations).isEqualTo(expected).as(description);
    }

}