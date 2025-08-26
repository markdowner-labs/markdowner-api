package org.markdowner.api.domain.validation.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class BirthdayTest {

    @Test
    @DisplayName("Deve aceitar aniversário válido (maior de idade)")
    void shouldAcceptValidBirthday() {
        final var validBirthday = LocalDate.now().minusYears(Birthday.MIN_YEAR).minusDays(1);
        assertDoesNotThrow(() -> Birthday.Validation.validate(validBirthday));
    }

    @Test
    @DisplayName("Deve lançar exceção para aniversário nulo")
    void shouldThrowForNullBirthday() {
        final var description = "Aniversário nulo";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Birthday.Validation.validate(null);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("não deve ser nulo");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para aniversário menor que o mínimo permitido")
    void shouldThrowForBirthdayLessThanMinYear() {
        final var underageBirthday = LocalDate.now().minusYears(Birthday.MIN_YEAR).plusDays(1);
        final var description = "Menor que " + Birthday.MIN_YEAR + " anos";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Birthday.Validation.validate(underageBirthday);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ter mais de " + Birthday.MIN_YEAR + " anos");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve aceitar aniversário exatamente no limite de idade")
    void shouldAcceptBirthdayAtMinYear() {
        final var minBirthday = LocalDate.now().minusYears(Birthday.MIN_YEAR);
        assertDoesNotThrow(() -> Birthday.Validation.validate(minBirthday));
    }

}
