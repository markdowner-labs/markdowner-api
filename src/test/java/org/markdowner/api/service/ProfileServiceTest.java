package org.markdowner.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.markdowner.api.domain.model.Profile;
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

        private static Profile profile() {
                return Profile.builder()
                                .id(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"))
                                .name("P. Dias D'Ávila São M.")
                                .description("Especialista em segurança de aplicações.")
                                .birthday(LocalDate.of(1983, 12, 25))
                                .email("anyemail@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

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
                final var expected = List.of("deve ser um endereço de e-mail bem formado");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByEmail(anyString());
        }

        @Test
        @DisplayName("Validação do campo Email - caso nulo")
        public void shouldRejectNullEmailsTest() {
                final var description = "Email: caso nulo";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByEmail(null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByEmail(anyString());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidNameProvider")
        @DisplayName("Validação do campo Nome - casos inválidos")
        public void shouldRejectInvalidNamesTest(final String description, final String name) {
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, name, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um nome bem formado");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @Test
        @DisplayName("Validação do campo lastSeenName - caso onde o nome é longo de mais")
        public void shouldRejectOverSizeName() {
                final var description = "Nome: longo de mais para cer aceito";
                final var longSizeName = "um nome longo demais para ser aceito";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, longSizeName, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve conter no máximo 30 caracteres");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidLimitProvider")
        @DisplayName("Validação do campo limit - caso onde o limite de paginação é zero ou negativo")
        public void shouldRejectZeroOrNegativeLimit(final String description, final int limit) {
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(limit, "P. Alvares Cabral", UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());

                validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(limit, "any name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString());
        }

        @Test
        @DisplayName("Validação do campo lastSeenId - caso o último ID visto seja nulo")
        public void shouldRejectNullLastSeenId() {
                final var description = "lastSeenId: o último id visto está nulo";
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, "Pedro A. Cabral", null, "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @Test
        @DisplayName("Validação do campo name - caso onde o nome esteja em branco")
        public void shouldRejectBlankName() {
                final var description = "Nome: está em branco";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, "Pedro Alvares C.", UUID.randomUUID(), null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve estar em branco");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());

                validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString());
        }

        @Test
        @DisplayName("Validação do campo Nome - caso nulo")
        public void shouldRejectNullNamesTest() {
                final var description = "Nome: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(1, null, UUID.randomUUID(), "any_name");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @Test
        @DisplayName("Validação do campo email - caso válido")
        public void shouldAcceptValidFindByEmail() {
                final var profile = profile();
                when(repository.findByEmail(profile.getEmail())).thenReturn(Optional.of(profile));
                assertThat(service.findByEmail(profile.getEmail())).isEqualTo(Optional.of(profile));
                verify(repository, times(1)).findByEmail(anyString());
        }

        @Test
        @DisplayName("Validação do campo nome - caso válido")
        public void shouldAcceptValidFindByNameContainingIgnoreCase() {
                final var profile = profile();

                when(repository.findByNameContainingIgnoreCase(10, profile.getName())).thenReturn(List.of(profile));
                assertThat(service.findByNameContainingIgnoreCase(10, profile.getName())).isEqualTo(List.of(profile));
                verify(repository, times(1)).findByNameContainingIgnoreCase(anyInt(), anyString());

                when(repository.findByNameContainingIgnoreCase(10, profile.getName(), profile.getId(), profile.getName())).thenReturn(List.of(profile));
                assertThat(service.findByNameContainingIgnoreCase(10, profile.getName(), profile.getId(), profile.getName())).isEqualTo(List.of(profile));
                verify(repository, times(1)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @Test
        @DisplayName("Validação do campo id - caso nulo")
        public void shouldRejectNullIdTest() {
                final var description = "Id: está nulo";
                final UUID nullId = null;
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findById(nullId);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findById(any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo id - caso válido")
        public void shouldAcceptValidFindById() {
                final var profile = profile();
                when(repository.findById(profile.getId())).thenReturn(Optional.of(profile));
                assertThat(service.findById(profile.getId())).isEqualTo(Optional.of(profile));
                verify(repository, times(1)).findById(any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo id - caso não encontrado")
        public void shouldReturnEmptyWhenIdNotFound() {
                final var id = UUID.randomUUID();
                when(repository.findById(id)).thenReturn(Optional.empty());
                assertThat(service.findById(id)).isEmpty();
                verify(repository, times(1)).findById(any(UUID.class));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidLimitProvider")
        @DisplayName("Validação do campo limit em findAll - casos inválidos")
        public void shouldRejectZeroOrNegativeLimitInFindAll(final String description, final int limit) {
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(limit);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt());

                validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(limit, "P. Dias D'Ávila São M.", UUID.randomUUID());
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidNameProvider")
        @DisplayName("Validação do campo lastSeenName em findAll - nome inválido")
        public void shouldRejectInvalidLastSeenNameInFindAll(final String description, final String name) {
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(1, name, UUID.randomUUID());
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um nome bem formado");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo lastSeenId em findAll - caso nulo")
        public void shouldRejectNullLastSeenIdInFindAll() {
                final var description = "lastSeenId: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(1, "P. Dias D'Ávila São M.", null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo lastSeenName em findAll - nome longo demais")
        public void shouldRejectOverSizeLastSeenNameInFindAll() {
                final var description = "lastSeenName: longo demais";
                final var longSizeName = "um nome longo demais para ser aceito";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(1, longSizeName, UUID.randomUUID());
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve conter no máximo 30 caracteres");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @Test
        @DisplayName("Validação do método findAll - caso válido")
        public void shouldAcceptValidFindAll() {
                final var profile = profile();
                when(repository.findAll(10)).thenReturn(List.of(profile));
                assertThat(service.findAll(10)).isEqualTo(List.of(profile));
                verify(repository, times(1)).findAll(anyInt());
                when(repository.findAll(10, profile.getName(), profile.getId())).thenReturn(List.of(profile));
                assertThat(service.findAll(10, profile.getName(), profile.getId())).isEqualTo(List.of(profile));
                verify(repository, times(1)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo limit em findAll - caso nulo")
        public void shouldRejectNullLimitInFindAll() {
                final var description = "limit: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(null, "P. Dias D'Ávila São M.", UUID.randomUUID());
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(), any(UUID.class));
        }

        @Test
        @DisplayName("Validação do campo limit em findAll primeira paginação - caso nulo")
        public void shouldRejectNullLimitInFindAllFirstPagination() {
                final var description = "limit: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(null);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 101 })
        @DisplayName("Validação do campo limit em findAll - caso inválido")
        public void shouldRejectInvalidValuesLimitInFindAll(final int invalidLimit) {
                final var description = "limit: está inválido";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(invalidLimit, "P. Dias D'Ávila São M.", UUID.randomUUID());
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt(), anyString(),  any(UUID.class));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 101 })
        @DisplayName("Validação do campo limit em findAll primeira paginação - caso inválido")
        public void shouldRejectInvalidValuesLimitInFindAllFirstPagination(final int invalidLimit) {
                final var description = "limit: está inválido";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findAll(invalidLimit);
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findAll(anyInt());
        }

        @Test
        @DisplayName("Validação do campo limit em findByNameContainingIgnoreCase - caso nulo")
        public void shouldRejectNullLimitInFindByNameContainingIgnoreCase() {
                final var description = "limit: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(null, "João");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 101 })
        @DisplayName("Validação do campo limit em findByNameContainingIgnoreCase - caso inválido")
        public void shouldRejectInvalidValuesLimitInFindByNameContainingIgnoreCase(final int invalidLimit) {
                final var description = "limit: está inválido";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(invalidLimit, "João");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString());
        }

        @Test
        @DisplayName("Validação do campo limit em findByNameContainingIgnoreCase com paginação - caso nulo")
        public void shouldRejectNullLimitInFindByNameContainingIgnoreCaseWithPagination() {
                final var description = "limit: está nulo";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(null, "P. Dias D'Ávila São M.", UUID.randomUUID(), "João");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("não deve ser nulo");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 101 })
        @DisplayName("Validação do campo limit em findByNameContainingIgnoreCase com paginação - caso inválido")
        public void shouldRejectInvalidValuesLimitInFindByNameContainingIgnoreCaseWithPagination(final int invalidLimit) {
                final var description = "limit: está inválido";
                var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByNameContainingIgnoreCase(invalidLimit, "P. Dias D'Ávila São M.", UUID.randomUUID(), "João");
                }, description).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("deve ser um número positivo menor ou igual à 100");
                assertThat(validations).isEqualTo(expected).as(description);
                verify(repository, never().description(description)).findByNameContainingIgnoreCase(anyInt(), anyString(), any(UUID.class), anyString());
        }

}
