package org.markdowner.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.markdowner.api.domain.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class ProfileServiceTest {

        @Autowired
        private ProfileService service;

        private static final Profile profileAnaIsidoro() {
                return Profile.builder()
                                .id(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"))
                                .name("Ana Isidoro")
                                .description("Especialista em segurança de aplicações.")
                                .birthday(LocalDate.of(1983, 12, 25))
                                .email("ana.isidoro@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileDianaFernandes() {
                return Profile.builder()
                                .id(UUID.fromString("019926b9-1561-7000-6090-c113559eb471"))
                                .name("Diana Fernandes")
                                .description("Especialista em e-commerce e vendas.")
                                .birthday(LocalDate.of(1995, 4, 13))
                                .email("diana@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileJulianaRios() {
                return Profile.builder()
                                .id(UUID.fromString("019acd02-8d61-7000-dc08-f222fa8c30f4"))
                                .name("Juliana Rios")
                                .description("Estudante de engenharia de dados.")
                                .birthday(LocalDate.of(2000, 2, 9))
                                .email("juliana.rios@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileMarianaPaz() {
                return Profile.builder()
                                .id(UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"))
                                .name("Mariana Paz")
                                .description("Estudante de engenharia de dados.")
                                .birthday(LocalDate.of(2000, 12, 1))
                                .email("mariana.paz@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileRenataYamanaka() {
                return Profile.builder()
                                .id(UUID.fromString("019a7050-1561-7000-762c-4de2f6a38808"))
                                .name("Renata Yamanaka")
                                .description("Analista de dados e ciência de dados.")
                                .birthday(LocalDate.of(1994, 5, 16))
                                .email("renata.yamanaka@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileVitorViana() {
                return Profile.builder()
                                .id(UUID.fromString("01997e45-3161-7000-4774-f18b86a14137"))
                                .name("Vitor Viana")
                                .description("Consultor financeiro e investidor.")
                                .birthday(LocalDate.of(1982, 10, 11))
                                .email("vitor.viana@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
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
                                Arguments.of("Email: domínio iniciando com ponto", "juliana.rios@.example.com"));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidEmailProvider")
        @DisplayName("Validação do campo Email - casos inválidos")
        public void shouldRejectInvalidEmailsTest(String description, String email) {
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByEmail(email);
                }).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must be a well-formed email address");
                assertThat(validations).isEqualTo(expected);
        }

        @Test
        @DisplayName("Validação do campo Email - casos em branco ou nulo")
        public void shouldRejectBlankAndNullEmailsTest() {
                final var validations = assertThrows(ConstraintViolationException.class, () -> {
                        service.findByEmail("");
                }).getConstraintViolations().stream().map(ConstraintViolation::getMessage);
                final var expected = List.of("must be a well-formed email address");
                assertThat(validations).isEqualTo(expected);
        }

        @Test
        @DisplayName("não deve encontrar nenhum email idêntico")
        public void notFound_findByEmailTest() {
                // when
                final var when = service.findByEmail("not_found@example.org");
                // then
                assertThat(when).isNotPresent();
        }

        @Test
        @DisplayName("deve encontrar 1 email idêntico")
        public void foundEqualEmail_findByEmailTest() {
                // given
                final var given = Optional.of(profileJulianaRios());
                // when
                final var when = service.findByEmail("juliana.rios@example.com");
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve encontrar 6 nomes similares por ordem alfabética")
        public void foundLimit6_findByNameContainingIgnoreCaseTest() {
                // given
                final var given = List.of(
                                profileAnaIsidoro(),
                                profileDianaFernandes(),
                                profileJulianaRios(),
                                profileMarianaPaz(),
                                profileRenataYamanaka(),
                                profileVitorViana());
                // when
                final var when = service.findByNameContainingIgnoreCase(6, "ana");
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve encontrar 2 nomes similares por ordem alfabética")
        public void foundLimit02_findByNameContainingIgnoreCaseTest() {
                final var limit = 2;
                final var searchTerm = "ana";
                // given
                final var givenStepsOne = List.of(profileAnaIsidoro(), profileDianaFernandes());
                final var givenStepsTwo = List.of(profileJulianaRios(), profileMarianaPaz());
                final var givenStepsTree = List.of(profileRenataYamanaka(), profileVitorViana());
                // when
                final var whenStepsOne = service.findByNameContainingIgnoreCase(limit, searchTerm);
                final var whenStepsTwo = service.findByNameContainingIgnoreCase(
                                limit, "Diana Fernandes",
                                UUID.fromString("019926b9-1561-7000-6090-c113559eb471"),
                                searchTerm);
                final var whenStepsTree = service.findByNameContainingIgnoreCase(
                                limit,
                                "Mariana Paz",
                                UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"),
                                searchTerm);
                // then
                assertThat(whenStepsOne).isEqualTo(givenStepsOne);
                assertThat(whenStepsTwo).isEqualTo(givenStepsTwo);
                assertThat(whenStepsTree).isEqualTo(givenStepsTree);
        }

        @Test
        @DisplayName("deve encontrar nomes similares em `ignore case`")
        public void foundIgnoreCase_findByNameContainingIgnoreCaseTest() {
                final var limit = 10;
                // given
                final var given = List.of(profileDianaFernandes());
                // when
                final var whenStartUpperCase = service.findByNameContainingIgnoreCase(limit, "Fernandes");
                final var whenFullLowerCase = service.findByNameContainingIgnoreCase(limit, "fernandes");
                final var whenFullUpperCase = service.findByNameContainingIgnoreCase(limit, "FERNANDES");
                final var whenEndUpperCase = service.findByNameContainingIgnoreCase(limit, "fernandeS");
                final var whenStartSpace = service.findByNameContainingIgnoreCase(limit, " FERNANDES");
                final var whenEnterSpace = service.findByNameContainingIgnoreCase(limit, " FERNANDES ");
                final var whenEndSpace = service.findByNameContainingIgnoreCase(limit, "FERNANDES ");
                // then
                assertThat(whenStartUpperCase).isEqualTo(given);
                assertThat(whenFullLowerCase).isEqualTo(given);
                assertThat(whenFullUpperCase).isEqualTo(given);
                assertThat(whenEndUpperCase).isEqualTo(given);
                assertThat(whenStartSpace).isEqualTo(given);
                assertThat(whenEnterSpace).isEqualTo(given);
                assertThat(whenEndSpace).isEqualTo(given);
        }

        @Test
        @DisplayName("deve determinar que o nomes similares não foram encontrados")
        public void notFound_findByNameContainingIgnoreCaseTest() {
                // when
                final var whenFindByNameContainingIgnoreCase = service.findByNameContainingIgnoreCase(
                                10,
                                "listrange");
                // then
                assertThat(whenFindByNameContainingIgnoreCase).isEmpty();
        }

        @Test
        @DisplayName("deve encontrar uma sequência de nomes iguais uns aos outros")
        public void foundEqualsSequenceName_findByNameContainingIgnoreCaseTest() {
                final var limit = 2;
                final var searchTerm = "Ícaro Queiroz";
                final var lastSeenName = searchTerm;
                final var builder = Profile
                                .builder()
                                .name("Ícaro Queiroz")
                                .description("Gerente de projetos de software.")
                                .birthday(LocalDate.of(1980, 06, 16))
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW");
                // given
                final var givenStepsOne = builder
                                .id(UUID.fromString("019b3928-1961-7000-8291-81abacac2031"))
                                .email("icaro.queiroz4@example.com")
                                .build();
                final var givenStepsTwo = builder
                                .id(UUID.fromString("019b3e4e-7561-7000-08a3-ee595001af07"))
                                .email("icaro.queiroz3@example.com")
                                .build();
                final var givenStepsTree = builder
                                .id(UUID.fromString("019b4374-d161-7000-b34e-ae5170c4149d"))
                                .email("icaro.queiroz2@example.com")
                                .build();
                final var givenStepsFour = builder
                                .id(UUID.fromString("019b489b-2d61-7000-d755-207f0f2a1824"))
                                .email("icaro.queiroz1@example.com")
                                .build();
                final var givenStepsFive = builder
                                .id(UUID.fromString("019b4dc1-8961-7000-1fdf-212992682387"))
                                .email("icaro.queiroz0@example.com")
                                .build();
                // when
                UUID lastSeenId;
                final var whenStepsOne = service.findByNameContainingIgnoreCase(limit, searchTerm);
                lastSeenId = UUID.fromString("019b3e4e-7561-7000-08a3-ee595001af07");
                final var whenStepsTwo = service.findByNameContainingIgnoreCase(
                                limit,
                                lastSeenName,
                                lastSeenId,
                                searchTerm);
                lastSeenId = UUID.fromString("019b489b-2d61-7000-d755-207f0f2a1824");
                final var whenStepsTree = service.findByNameContainingIgnoreCase(
                                limit,
                                lastSeenName,
                                lastSeenId,
                                searchTerm);
                // then
                assertThat(whenStepsOne).isEqualTo(List.of(givenStepsOne, givenStepsTwo));
                assertThat(whenStepsTwo).isEqualTo(List.of(givenStepsTree, givenStepsFour));
                assertThat(whenStepsTree).isEqualTo(List.of(givenStepsFive));
        }

}
