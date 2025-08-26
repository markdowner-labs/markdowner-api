package org.markdowner.api.domain.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YearTest {

    private static final long MIN_YEARS = 18;
    private static final long MAX_YEARS = 60;

    @Year(min = MIN_YEARS, max = MAX_YEARS, message = "deve ter entre 18 e 60 anos")
    private LocalDate birthDate;

    private static class TestBean {
        @Year(min = MIN_YEARS, max = MAX_YEARS, message = "deve ter entre 18 e 60 anos", nullable = false)
        private final LocalDate birthDate;

        TestBean(final LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    private void validate(final LocalDate date) {
        final var bean = new TestBean(date);
        final var factory = Validation.buildDefaultValidatorFactory();
        final var violations = factory.getValidator().validate(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Test
    @DisplayName("Deve aceitar data dentro do intervalo permitido")
    void shouldAcceptValidYear() {
        final LocalDate validDate = LocalDate.now().minusYears(30);
        assertDoesNotThrow(() -> validate(validDate));
    }

    @Test
    @DisplayName("Deve aceitar data no limite mínimo")
    void shouldAcceptMinYear() {
        final LocalDate minDate = LocalDate.now().minusYears(MIN_YEARS);
        assertDoesNotThrow(() -> validate(minDate));
    }

    @Test
    @DisplayName("Deve aceitar data no limite máximo")
    void shouldAcceptMaxYear() {
        final LocalDate maxDate = LocalDate.now().minusYears(MAX_YEARS);
        assertDoesNotThrow(() -> validate(maxDate));
    }

    @Test
    @DisplayName("Deve lançar exceção para data menor que o mínimo permitido")
    void shouldThrowForYearLessThanMin() {
        final LocalDate tooRecent = LocalDate.now().minusYears(MIN_YEARS - 1);
        final var description = "Ano menor que o mínimo permitido";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            validate(tooRecent);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ter entre 18 e 60 anos");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para data maior que o máximo permitido")
    void shouldThrowForYearGreaterThanMax() {
        final LocalDate tooOld = LocalDate.now().minusYears(MAX_YEARS + 1);
        final var description = "Ano maior que o máximo permitido";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            validate(tooOld);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ter entre 18 e 60 anos");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve lançar exceção para data nula quando nullable=false")
    void shouldThrowForNullYearWhenNotNullable() {
        final var description = "Ano nulo";
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            validate(null);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ter entre 18 e 60 anos");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Deve aceitar data nula quando nullable=true")
    void shouldAcceptNullYearWhenNullable() {
        class NullableBean {
            @Year(min = MIN_YEARS, max = MAX_YEARS, message = "deve ter entre 18 e 60 anos", nullable = true)
            private final LocalDate birthDate;

            NullableBean(final LocalDate birthDate) {
                this.birthDate = birthDate;
            }
        }
        final var bean = new NullableBean(null);
        final var factory = Validation.buildDefaultValidatorFactory();
        final var violations = factory.getValidator().validate(bean);
        assertThat(violations).isEmpty();
    }
}
