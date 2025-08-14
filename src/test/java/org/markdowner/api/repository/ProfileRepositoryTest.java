package org.markdowner.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.markdowner.api.domain.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfileRepositoryTest {

        @Autowired
        private ProfileRepository repository;

        private static final Profile profileAnaIsidoro() {
                return Profile.builder()
                                .id(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"))
                                .name("Ana Isidoro")
                                .description("Especialista em segurança de aplicações.")
                                .birthday(LocalDate.of(1983, 12, 25))
                                .email("ana.isidoro@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        private static final Profile profileDianaFernandes() {
                return Profile.builder()
                                .id(UUID.fromString("019926b9-1561-7000-6090-c113559eb471"))
                                .name("Diana Fernandes")
                                .description("Especialista em e-commerce e vendas.")
                                .birthday(LocalDate.of(1995, 4, 13))
                                .email("diana@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        private static final Profile profileJulianaRios() {
                return Profile.builder()
                                .id(UUID.fromString("019acd02-8d61-7000-dc08-f222fa8c30f4"))
                                .name("Juliana Rios")
                                .description("Estudante de engenharia de dados.")
                                .birthday(LocalDate.of(2000, 2, 9))
                                .email("juliana.rios@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        private static final Profile profileMarianaPaz() {
                return Profile.builder()
                                .id(UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"))
                                .name("Mariana Paz")
                                .description("Estudante de engenharia de dados.")
                                .birthday(LocalDate.of(2000, 12, 1))
                                .email("mariana.paz@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        private static final Profile profileRenataYamanaka() {
                return Profile.builder()
                                .id(UUID.fromString("019a7050-1561-7000-762c-4de2f6a38808"))
                                .name("Renata Yamanaka")
                                .description("Analista de dados e ciência de dados.")
                                .birthday(LocalDate.of(1994, 5, 16))
                                .email("renata.yamanaka@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        private static final Profile profileVitorViana() {
                return Profile.builder()
                                .id(UUID.fromString("01997e45-3161-7000-4774-f18b86a14137"))
                                .name("Vitor Viana")
                                .description("Consultor financeiro e investidor.")
                                .birthday(LocalDate.of(1982, 10, 11))
                                .email("vitor.viana@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build();
        }

        @Test
        @DisplayName("não deve encontrar nenhum email idêntico")
        public void notFound_findByEmailTest() {
                // when
                final var when = repository.findByEmail("not_found@example.org");
                // then
                assertThat(when).isNotPresent();
        }

        @Test
        @DisplayName("não deve encontrar nenhum email similar")
        public void notFoundSameEmail_findByEmailTest() {
                // when
                final var when = repository.findByEmail("JuliAna.rios@example.com");
                // then
                assertThat(when).isNotPresent();
        }

        @Test
        @DisplayName("deve encontrar 1 email idêntico")
        public void foundEqualEmail_findByEmailTest() {
                // given
                final var given = Optional.of(profileJulianaRios());
                // when
                final var when = repository.findByEmail("juliana.rios@example.com");
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
                final var when = repository.findByNameContainingIgnoreCase(6, "ana");
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve encontrar 2 nomes similares por ordem alfabética")
        public void foundLimit02_findByNameContainingIgnoreCaseTest() {
                // given
                final var givenStepsOne = List.of(profileAnaIsidoro(), profileDianaFernandes());
                final var givenStepsTwo = List.of(profileJulianaRios(), profileMarianaPaz());
                final var givenStepsTree = List.of(profileRenataYamanaka(), profileVitorViana());
                // when
                final var whenStepsOne = repository.findByNameContainingIgnoreCase(2, "ana");
                final var whenStepsTwo = repository.findByNameContainingIgnoreCase(
                                2, "Diana Fernandes",
                                UUID.fromString("019926b9-1561-7000-6090-c113559eb471"),
                                "ana");
                final var whenStepsTree = repository.findByNameContainingIgnoreCase(
                                2,
                                "Mariana Paz",
                                UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"),
                                "ana");
                // then
                assertThat(whenStepsOne).isEqualTo(givenStepsOne);
                assertThat(whenStepsTwo).isEqualTo(givenStepsTwo);
                assertThat(whenStepsTree).isEqualTo(givenStepsTree);
        }

        @Test
        @DisplayName("deve encontrar nomes similares em `ignore case`")
        public void foundIgnoreCase_findByNameContainingIgnoreCaseTest() {
                // given
                final var given = List.of(profileDianaFernandes());
                // when
                final var whenStartUpperCase = repository.findByNameContainingIgnoreCase(10, "Fernandes");
                final var whenFullLowerCase = repository.findByNameContainingIgnoreCase(10, "fernandes");
                final var whenFullUpperCase = repository.findByNameContainingIgnoreCase(10, "FERNANDES");
                final var whenEndUpperCase = repository.findByNameContainingIgnoreCase(10, "fernandeS");
                final var whenStartSpace = repository.findByNameContainingIgnoreCase(10, " FERNANDES");
                final var whenEnterSpace = repository.findByNameContainingIgnoreCase(10, " FERNANDES ");
                final var whenEndSpace = repository.findByNameContainingIgnoreCase(10, "FERNANDES ");
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
                final var whenFindByNameContainingIgnoreCase = repository.findByNameContainingIgnoreCase(10,
                                "listrange");
                // then
                assertThat(whenFindByNameContainingIgnoreCase).isEmpty();
        }

}
