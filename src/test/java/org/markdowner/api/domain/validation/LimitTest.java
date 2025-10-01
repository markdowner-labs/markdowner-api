package org.markdowner.api.domain.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class LimitTest {

    private static final int MAX_LIMIT = 100;

    @ParameterizedTest
    @ValueSource(ints = {

            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
            53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
            78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100

    })
    @DisplayName("Deve aceitar limites válidos")
    void shouldAcceptValidLimits(final int validLimit) {
        assertDoesNotThrow(() -> Limit.Validation.validate(validLimit));
    }

    @Test
    @DisplayName("Deve aceitar limite no valor máximo permitido")
    void shouldAcceptMaxLimit() {
        assertDoesNotThrow(() -> Limit.Validation.validate(MAX_LIMIT));
    }

    @Test
    @DisplayName("Deve aceitar limite no valor mínimo permitido")
    void shouldAcceptMinLimit() {
        final var minLimit = 1;
        assertDoesNotThrow(() -> Limit.Validation.validate(minLimit));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 101 })
    @DisplayName("Deve lançar exceção para limites inválidos")
    void shouldThrowForInvalidLimits(final int invalidLimit) {
        final var description = "Limite inválido: " + invalidLimit;
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Limit.Validation.validate(invalidLimit);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ser um número positivo menor ou igual à 100");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para limite nulo")
    void shouldThrowForNullLimit() {
        final var description = "Limite nulo não é permitido";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Limit.Validation.validate(null);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("não deve ser nulo");
        assertThat(validations).isEqualTo(expected).as(description);
    }

}
