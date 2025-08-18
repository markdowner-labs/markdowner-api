package org.markdowner.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.markdowner.api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class ProfileServiceTest {

        @MockitoBean
        private ProfileRepository repository;

        @Autowired
        private ProfileService service;

        private static Stream<Arguments> invalidLimitProvider() {
                return Stream.of(
                                Arguments.of("Limit: limite de paginação negativo", -1),
                                Arguments.of("Limit: limite de paginação zero", 0));
        }

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
        @MethodSource("invalidEmailProvider")
        @DisplayName("Validação do campo Email - casos inválidos")
        public void shouldRejectInvalidEmailsTest(final String description, final String email) {
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByEmail(email);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must be a well-formed email address");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByEmail(email);
        }

        @Test
        @DisplayName("Validação do campo Email - caso nulo")
        public void shouldRejectNullEmailsTest() {
                final var description = "Email: caso nulo";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByEmail(null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must not be null email address");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByEmail(null);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidNameProvider")
        @DisplayName("Validação do campo Nome - casos inválidos")
        public void shouldRejectInvalidNamesTest(final String description, final String name) {
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, name, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must be a well-formed name");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description))
                                .findByNameContainingIgnoreCase(1, name, UUID.randomUUID(), "any_name");
        }

        @Test
        @DisplayName("Validação do campo lastSeenName - caso onde o nome é longo de mais")
        public void shouldRejectOverSizeName() {
                final var description = "Nome: longo de mais para cer aceito";
                final var longSizeName = "um nome longo demais para ser aceito";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, longSizeName, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("size must contain a maximum of 30 characters");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description))
                                .findByNameContainingIgnoreCase(1, longSizeName, UUID.randomUUID(), "any_name");
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidLimitProvider")
        @DisplayName("Validação do campo limit - caso onde o limite de paginação é zero ou negativo")
        public void shouldRejectZeroOrNegativeLimit(final String description, final int limit) {
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(limit, "P. Alvares Cabral", UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must be greater than 0");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(limit, "P. Alvares Cabral", UUID.randomUUID(), "any_name");

                validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(limit, "any name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(limit, "any name");
        }

        @Test
        @DisplayName("Validação do campo lastSeenId - caso o último ID visto seja nulo")
        public void shouldRejectNullLastSeenId() {
                final var description = "lastSeenId: o último id visto está nulo";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, "Pedro A. Cabral", null, "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must not be null");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(1, "Pedro A. Cabral", null, "any_name");
        }

        @Test
        @DisplayName("Validação do campo name - caso onde o nome esteja em branco")
        public void shouldRejectBlankName() {
                final var description = "Nome: está em branco";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, "Pedro Alvares C.", UUID.randomUUID(), null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must not be blank");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(1, "Pedro Alvares C.", UUID.randomUUID(), null);

                validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(1, null);
        }

        @Test
        @DisplayName("Validação do campo Nome - caso nulo")
        public void shouldRejectNullNamesTest() {
                final var description = "Nome: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, null, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must not be null name");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(1, null, UUID.randomUUID(), "any_name");
        }

}
