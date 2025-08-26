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
public class DescriptionTest {

    @Test
    @DisplayName("Deve aceitar descrição válida com tamanho máximo")
    void shouldAcceptValidDescription() {
        final var validDescription = "a".repeat(Description.MAX_SIZE); // 500 chars
        assertDoesNotThrow(() -> Description.Validation.validate(validDescription));
    }

    @Test
    @DisplayName("Deve aceitar descrição vazia")
    void shouldAcceptEmptyDescription() {
        final var emptyDescription = "";
        assertDoesNotThrow(() -> Description.Validation.validate(emptyDescription));
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição muito longa")
    void shouldThrowForLongDescription() {
        final var longDescription = "a".repeat(Description.MAX_SIZE + 1); // 501 chars
        final var description = "Descrição muito longa";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Description.Validation.validate(longDescription);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve conter no máximo 500 caracteres");
        assertThat(validations).isEqualTo(expected);
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição nula")
    void shouldThrowForNullDescription() {
        final String nullDescription = null;
        final var description = "Descrição nula";
        assertDoesNotThrow(() -> Description.Validation.validate(nullDescription), description);
    }
}
