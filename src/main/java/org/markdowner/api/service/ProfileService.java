package org.markdowner.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.markdowner.api.domain.model.Profile;
import org.markdowner.api.domain.validation.Limit;
import org.markdowner.api.domain.validation.profile.Email;
import org.markdowner.api.domain.validation.profile.Name;
import org.markdowner.api.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Validated
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public Optional<Profile> findById(@NotNull final UUID id) {
        return repository.findById(id);
    }

    public Optional<Profile> findByEmail(final @Email String email) {
        return repository.findByEmail(email);
    }

    public List<Profile> findAll(final @Limit Integer limit) {
        return repository.findAll(limit);
    }

    public List<Profile> findAll(
            final @Limit Integer limit,
            final @Name String lastSeenName,
            final @NotNull UUID lastSeenId) {
        return repository.findAll(limit, lastSeenName, lastSeenId);
    }

    public List<Profile> findByNameContainingIgnoreCase(
            final @Limit Integer limit,
            final @NotBlank String name) {
        return repository.findByNameContainingIgnoreCase(limit, name);
    }

    public List<Profile> findByNameContainingIgnoreCase(
            final @Limit Integer limit,
            final @Name String lastSeenName,
            final @NotNull UUID lastSeenId,
            final @NotBlank String name) {
        return repository.findByNameContainingIgnoreCase(limit, lastSeenName, lastSeenId, name);
    }

}
