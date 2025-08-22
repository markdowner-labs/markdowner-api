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
public class EmailTest {

    private static Stream<Arguments> invalidEmailProvider() {
        return Stream.of(
                Arguments.of("Email: letra maiúscula no usuário", "Juliana.rios@example.com"),
                Arguments.of("Email: caractere inválido '!' no usuário", "juliana!rios@example.com"),
                Arguments.of("Email: espaço no usuário", "juliana rios@example.com"),
                Arguments.of("Email: espaço antes do domínio", "juliana.rios@ example.com"),
                Arguments.of("Email: espaço antes do TLD", "juliana.rios@example .com"),
                Arguments.of("Email: ausência de arroba", "juliana.riosexample.com"),
                Arguments.of("Email: dois arrobas", "juliana.rios@@example.com"),
                Arguments.of("Email: ausência de TLD", "juliana.rios@example"),
                Arguments.of("Email: TLD com menos de duas letras", "juliana.rios@example.c"),
                Arguments.of("Email: número no TLD", "juliana.rios@example.c0m"),
                Arguments.of("Email: maiúsculas no TLD", "juliana.rios@example.CoM"),
                Arguments.of("Email: domínio iniciando com hífen", "juliana.rios@-example.com"),
                Arguments.of("Email: dois pontos consecutivos no domínio", "juliana.rios@example..com"),
                Arguments.of("Email: domínio terminando com hífen", "juliana.rios@example-.com"),
                Arguments.of("Email: caractere acentuado no usuário", "juliána.rios@example.com"),
                Arguments.of("Email: vírgula no TLD", "juliana.rios@example,com"),
                Arguments.of("Email: caractere inválido '!' no domínio", "juliana.rios@exam!ple.com"),
                Arguments.of("Email: espaço no meio do domínio", "juliana.rios@exa mple.com"),
                Arguments.of("Email: domínio iniciando com ponto", "juliana.rios@.example.com"),
                Arguments.of("Email: endereço de email em branco", ""),
                Arguments.of(
                        "Email: endereço de email com mais de 76 caracteres",
                        "rprsgefxrayrpaydsncgdxpsnhwpryazfrrbjzkugvqimfidwpcjiyapipyvrehko@example.com"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidEmailProvider")
    @DisplayName("Validação do campo Email - casos inválidos")
    public void shouldRejectInvalidEmailsTest(final String description, final String email) {
        final var validations = assertThrows(ConstraintViolationException.class, () -> {
            Email.Validation.validate(email);
        }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        final var expected = List.of("deve ser um endereço de e-mail bem formado");
        assertThat(validations).isEqualTo(expected).as(description);
    }

    @Test
    @DisplayName("Validação do campo de email - caso válido")
    public void shouldAcceptValidEmailsTest() {
        final var email = "any.address@example.com.br";
        assertThat(Email.Validation.validate(email)).isEqualTo(email);
    }

}
