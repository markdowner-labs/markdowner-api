package org.markdowner.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
    public void shouldNotFindProfileByEmailTest() {
        assertThat(repository.findByEmail("not_found@example.org")).isNotPresent();
    }

    @Test
    @DisplayName("deve encontrar um perfil através do email")
    public void shouldFindProfileByEmailTest() {
        final var builder = Profile.builder()
                .id(UUID.fromString("0198a123-c0a1-72e4-bf8a-64d91516778e"))
                .name("Bruno Borges")
                .description("Apaixonado por tecnologia e música.")
                .birthday(LocalDate.of(1992, 04, 23))
                .email("bruno@example.com")
                .password("$2a$10$7Z0zPEtZklljGNH8JHcnRO0pOZAVlBH36Fg7QO9N1LD4thimBL.TW");
        assertThat(repository.findByEmail("bruno@example.com")).isEqualTo(Optional.of(builder.build()));
    }

}
