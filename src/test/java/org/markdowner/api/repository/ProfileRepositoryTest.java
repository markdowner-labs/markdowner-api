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

    @Test
    @DisplayName("não deve encontrar um perfil através do email")
    public void found_findByEmailTest() {
        assertThat(repository.findByEmail("not_found@example.org")).isNotPresent();
    }

    @Test
    @DisplayName("deve encontrar todos os perfis do settlement.sql pelo email")
    public void notFound_findByEmailTest() {
        assertThat(repository.findByEmail("alice@example.com")).isEqualTo(Optional.of(Profile.builder()
                .id(UUID.fromString("01989bad-6161-7000-0ae9-f440b10578ec"))
                .name("Alice Alves")
                .description("Uma pessoa criativa e dedicada.")
                .birthday(LocalDate.of(1988, 1, 10))
                .email("alice@example.com")
                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build()));
    }

    @Test
    @DisplayName("deve buscar nomes por ordem alfabética")
    public void foundLimit10_findByNameContainingIgnoreCaseTest() {
        assertThat(repository.findByNameContainingIgnoreCase(6, "ana")).isEqualTo(List.of(
                Profile.builder()
                        .id(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"))
                        .name("Ana Isidoro")
                        .description("Especialista em segurança de aplicações.")
                        .birthday(LocalDate.of(1983, 12, 25))
                        .email("ana.isidoro@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("019926b9-1561-7000-6090-c113559eb471"))
                        .name("Diana Fernandes")
                        .description("Especialista em e-commerce e vendas.")
                        .birthday(LocalDate.of(1995, 4, 13))
                        .email("diana@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("019acd02-8d61-7000-dc08-f222fa8c30f4"))
                        .name("Juliana Rios")
                        .description("Estudante de engenharia de dados.")
                        .birthday(LocalDate.of(2000, 2, 9))
                        .email("juliana.rios@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"))
                        .name("Mariana Paz")
                        .description("Estudante de engenharia de dados.")
                        .birthday(LocalDate.of(2000, 12, 1))
                        .email("mariana.paz@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("019a7050-1561-7000-762c-4de2f6a38808"))
                        .name("Renata Yamanaka")
                        .description("Analista de dados e ciência de dados.")
                        .birthday(LocalDate.of(1994, 5, 16))
                        .email("renata.yamanaka@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("01997e45-3161-7000-4774-f18b86a14137"))
                        .name("Vitor Viana")
                        .description("Consultor financeiro e investidor.")
                        .birthday(LocalDate.of(1982, 10, 11))
                        .email("vitor.viana@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build()));
    }

    @Test
    @DisplayName("deve buscar nomes por ordem alfabética")
    public void foundLimit02_findByNameContainingIgnoreCaseTest() {
        assertThat(repository.findByNameContainingIgnoreCase(2, "ana")).isEqualTo(List.of(
                Profile.builder()
                        .id(UUID.fromString("019b248e-a961-7000-233d-b99937ef11d4"))
                        .name("Ana Isidoro")
                        .description("Especialista em segurança de aplicações.")
                        .birthday(LocalDate.of(1983, 12, 25))
                        .email("ana.isidoro@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("019926b9-1561-7000-6090-c113559eb471"))
                        .name("Diana Fernandes")
                        .description("Especialista em e-commerce e vendas.")
                        .birthday(LocalDate.of(1995, 4, 13))
                        .email("diana@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build()));
        assertThat(repository.findByNameContainingIgnoreCase(2, "Diana Fernandes", "ana")).isEqualTo(List.of(
                Profile.builder()
                        .id(UUID.fromString("019acd02-8d61-7000-dc08-f222fa8c30f4"))
                        .name("Juliana Rios")
                        .description("Estudante de engenharia de dados.")
                        .birthday(LocalDate.of(2000, 2, 9))
                        .email("juliana.rios@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("0199d5d1-4d61-7000-18f0-9689e06c1745"))
                        .name("Mariana Paz")
                        .description("Estudante de engenharia de dados.")
                        .birthday(LocalDate.of(2000, 12, 1))
                        .email("mariana.paz@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build()));
        assertThat(repository.findByNameContainingIgnoreCase(2, "Mariana Paz", "ana")).isEqualTo(List.of(
                Profile.builder()
                        .id(UUID.fromString("019a7050-1561-7000-762c-4de2f6a38808"))
                        .name("Renata Yamanaka")
                        .description("Analista de dados e ciência de dados.")
                        .birthday(LocalDate.of(1994, 5, 16))
                        .email("renata.yamanaka@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build(),
                Profile.builder()
                        .id(UUID.fromString("01997e45-3161-7000-4774-f18b86a14137"))
                        .name("Vitor Viana")
                        .description("Consultor financeiro e investidor.")
                        .birthday(LocalDate.of(1982, 10, 11))
                        .email("vitor.viana@example.com")
                        .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build()));
    }

    @Test
    @DisplayName("deve buscar nomes por ordem alfabética")
    public void notFound_findByNameContainingIgnoreCaseTest() {
        assertThat(repository.findByNameContainingIgnoreCase(10, "listrange")).isEmpty();
    }

    @Test
    @DisplayName("deve buscar nomes por ordem alfabética")
    public void foundIgnoreCase_findByNameContainingIgnoreCaseTest() {
        final var result = List.of(Profile.builder()
                .id(UUID.fromString("019926b9-1561-7000-6090-c113559eb471"))
                .name("Diana Fernandes")
                .description("Especialista em e-commerce e vendas.")
                .birthday(LocalDate.of(1995, 4, 13))
                .email("diana@example.com")
                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW").build());
        assertThat(repository.findByNameContainingIgnoreCase(10, "Fernandes")).isEqualTo(result);
        assertThat(repository.findByNameContainingIgnoreCase(10, "fernandes")).isEqualTo(result);
        assertThat(repository.findByNameContainingIgnoreCase(10, "FERNANDES")).isEqualTo(result);
        assertThat(repository.findByNameContainingIgnoreCase(10, "fernandeS")).isEqualTo(result);
    }

}
