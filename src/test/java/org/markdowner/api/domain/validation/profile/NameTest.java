package org.markdowner.api.domain.validation.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class NameTest {

    private static Stream<Arguments> invalidNameProvider() {
        return Stream.of(
                Arguments.of("Nome: número no valor", "João123"),
                Arguments.of("Nome: caractere inválido '!'", "Maria!"),
                Arguments.of("Nome: caractere inválido '@'", "José@Silva"),
                Arguments.of("Nome: caractere inválido '_'", "Ana_Souza"),
                Arguments.of("Nome: espaço no início", " João"),
                Arguments.of("Nome: espaço no final", "Maria "),
                Arguments.of("Nome: dois espaços consecutivos", "João  Silva"),
                Arguments.of("Nome: dois hífens consecutivos", "Ana--Clara"),
                Arguments.of("Nome: dois apóstrofos consecutivos", "O''Neill"),
                Arguments.of("Nome: hífen no final", "Pedro-"),
                Arguments.of("Nome: apóstrofo no final", "Luiz'"),
                Arguments.of("Nome: espaço no final", "Ana "),
                Arguments.of("Nome: string vazia", ""),
                Arguments.of("Nome: apenas espaço", " "),
                Arguments.of("Nome: apenas hífen", "-"),
                Arguments.of("Nome: apenas apóstrofo", "'"),
                Arguments.of("Nome: letra fora do intervalo permitido (estilizada)", "𝓙𝓸𝓼é"),
                Arguments.of("Nome: letra fora do intervalo permitido (turca)", "İlker"),
                Arguments.of("Nome: letra fora do intervalo permitido (polonesa)", "Łukasz"),
                Arguments.of("Nome: ponto antes de leta", ".Pedro Alvares Cabral"),
                Arguments.of("Nome: ponto entre espaços", "Pedro . Alvares Cabral"),
                Arguments.of("Nome: ponto após espaço", "Pedro .Alvares Cabral"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidNameProvider")
    @DisplayName("Validação do campo Name - casos inválidos")
    public void shouldRejectInvalidEmailsTest(final String description, final String name) {
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Name.Validation.validate(name);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("must be a well-formed name");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Validação do campo de name - caso válido")
    public void shouldAcceptValidNamesTest() {
        final var name = "P. Dias-D'Ávila São M.";
        assertThat(Name.Validation.validate(name)).isEqualTo(name);
    }

}
