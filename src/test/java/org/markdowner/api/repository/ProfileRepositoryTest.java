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

        private static final Profile profileAlbertoInacio() {
                return Profile.builder()
                                .id(UUID.fromString("019a9ea9-5161-7000-03f1-098e3277407e"))
                                .name("Alberto Inácio")
                                .description("Gerente de produto e inovação.")
                                .birthday(LocalDate.of(1980, 4, 15))
                                .email("alberto.inacio@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileAliceAlves() {
                return Profile.builder()
                                .id(UUID.fromString("01989bad-6161-7000-0ae9-f440b10578ec"))
                                .name("Alice Alves")
                                .description("Uma pessoa criativa e dedicada.")
                                .birthday(LocalDate.of(1988, 1, 10))
                                .email("alice@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileAlineCastro() {
                return Profile.builder()
                                .id(UUID.fromString("01999804-fd61-7000-7c85-7376e99e6b3f"))
                                .name("Aline Castro")
                                .description("Analista de sistemas e DevOps.")
                                .birthday(LocalDate.of(1990, 9, 15))
                                .email("aline.castro@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileAmandaEsteves() {
                return Profile.builder()
                                .id(UUID.fromString("019a18c3-f961-7000-f06d-02b1741682aa"))
                                .name("Amanda Esteves")
                                .description("Especialista em marketing de conteúdo.")
                                .birthday(LocalDate.of(1984, 7, 29))
                                .email("amanda.esteves@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

        private static final Profile profileArturCordeiro() {
                return Profile.builder()
                                .id(UUID.fromString("01991746-0161-7000-de9f-dc071c54c03f"))
                                .name("Artur Cordeiro")
                                .description("Analista de sistemas com foco em dados.")
                                .birthday(LocalDate.of(1987, 3, 9))
                                .email("artur@example.com")
                                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW")
                                .build();
        }

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
                final var limit = 2;
                final var searchTerm = "ana";
                // given
                final var givenStepsOne = List.of(profileAnaIsidoro(), profileDianaFernandes());
                final var givenStepsTwo = List.of(profileJulianaRios(), profileMarianaPaz());
                final var givenStepsTree = List.of(profileRenataYamanaka(), profileVitorViana());
                // when
                final var whenStepsOne = repository.findByNameContainingIgnoreCase(limit, searchTerm);
                final var whenStepsTwo = repository.findByNameContainingIgnoreCase(
                                limit, "Diana Fernandes",
                                UUID.fromString("019926b9-1561-7000-6090-c113559eb471"),
                                searchTerm);
                final var whenStepsTree = repository.findByNameContainingIgnoreCase(
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
                final var whenStartUpperCase = repository.findByNameContainingIgnoreCase(limit, "Fernandes");
                final var whenFullLowerCase = repository.findByNameContainingIgnoreCase(limit, "fernandes");
                final var whenFullUpperCase = repository.findByNameContainingIgnoreCase(limit, "FERNANDES");
                final var whenEndUpperCase = repository.findByNameContainingIgnoreCase(limit, "fernandeS");
                final var whenStartSpace = repository.findByNameContainingIgnoreCase(limit, " FERNANDES");
                final var whenEnterSpace = repository.findByNameContainingIgnoreCase(limit, " FERNANDES ");
                final var whenEndSpace = repository.findByNameContainingIgnoreCase(limit, "FERNANDES ");
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
                final var whenFindByNameContainingIgnoreCase = repository.findByNameContainingIgnoreCase(
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
                final var whenStepsOne = repository.findByNameContainingIgnoreCase(limit, searchTerm);
                lastSeenId = UUID.fromString("019b3e4e-7561-7000-08a3-ee595001af07");
                final var whenStepsTwo = repository.findByNameContainingIgnoreCase(
                                limit,
                                lastSeenName,
                                lastSeenId,
                                searchTerm);
                lastSeenId = UUID.fromString("019b489b-2d61-7000-d755-207f0f2a1824");
                final var whenStepsTree = repository.findByNameContainingIgnoreCase(
                                limit,
                                lastSeenName,
                                lastSeenId,
                                searchTerm);
                // then
                assertThat(whenStepsOne).isEqualTo(List.of(givenStepsOne, givenStepsTwo));
                assertThat(whenStepsTwo).isEqualTo(List.of(givenStepsTree, givenStepsFour));
                assertThat(whenStepsTree).isEqualTo(List.of(givenStepsFive));
        }

        @Test
        @DisplayName("não deve encontrar nenhum id inexistente")
        public void notFound_findByIdTest() {
                // when
                final var when = repository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                // then
                assertThat(when).isNotPresent();
        }

        @Test
        @DisplayName("deve encontrar o perfil pelo id")
        public void found_findByIdTest() {
                // given
                final var given = Optional.of(profileAnaIsidoro());
                // when
                final var when = repository.findById(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"));
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve encontrar outro perfil pelo id")
        public void foundOther_findByIdTest() {
                // given
                final var given = Optional.of(profileJulianaRios());
                // when
                final var when = repository.findById(UUID.fromString("019acd02-8d61-7000-dc08-f222fa8c30f4"));
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve retornar todos os perfis limitados pelo parâmetro")
        public void foundLimit6_findAllTest() {
                // given
                final var given = List.of(
                                profileAlbertoInacio(),
                                profileAliceAlves(),
                                profileAlineCastro(),
                                profileAmandaEsteves(),
                                profileAnaIsidoro(),
                                profileArturCordeiro()
                );
                // when
                final var when = repository.findAll(6);
                // then
                assertThat(when).isEqualTo(given);
        }

        @Test
        @DisplayName("deve retornar os próximos perfis após o lastSeen")
        public void foundNextPage_findAllTest() {
                final var limit = 2;
                // given
                final var givenStepsOne = List.of(profileAlbertoInacio(), profileAliceAlves());
                final var givenStepsTwo = List.of(profileAlineCastro(), profileAmandaEsteves());
                final var givenStepsThree = List.of(profileAnaIsidoro(), profileArturCordeiro());
                // when
                final var whenStepsOne = repository.findAll(limit);
                final var whenStepsTwo = repository.findAll(
                                limit,
                                "Alice Alves",
                                UUID.fromString("01989bad-6161-7000-0ae9-f440b10578ec")
                );
                final var whenStepsThree = repository.findAll(
                                limit,
                                "Amanda Esteves",
                                UUID.fromString("019a18c3-f961-7000-f06d-02b1741682aa")
                );
                // then
                assertThat(whenStepsOne).isEqualTo(givenStepsOne);
                assertThat(whenStepsTwo).isEqualTo(givenStepsTwo);
                assertThat(whenStepsThree).isEqualTo(givenStepsThree);
        }

}
