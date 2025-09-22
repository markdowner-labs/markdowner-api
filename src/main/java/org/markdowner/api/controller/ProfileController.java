package org.markdowner.api.controller;

import static java.util.Objects.nonNull;

import java.util.UUID;

import org.markdowner.api.domain.model.Profile;
import org.markdowner.api.service.ProfileService;
import org.markdowner.api.util.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = Routes.PROFILE, produces = { "application/json" })
public class ProfileController {
    private final ProfileService service;

    private Profile hideSensitiveInfo(final Profile profile) {
        return profile.toBuilder().birthday(null).email(null).build();
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String lastSeenName,
            @RequestParam(required = false) final UUID lastSeenId,
            @RequestParam(defaultValue = "100", required = false) final int limit) {
        if (nonNull(id)) {
            return ResponseEntity.of(service.findById(id).map(this::hideSensitiveInfo));
        }
        if (nonNull(email)) {
            return ResponseEntity.of(service.findByEmail(email).map(this::hideSensitiveInfo));
        }
        if (nonNull(name)) {
            final var profiles = (nonNull(lastSeenName) || nonNull(lastSeenId))
                    ? service.findByNameContainingIgnoreCase(limit, lastSeenName, lastSeenId, name)
                    : service.findByNameContainingIgnoreCase(limit, name);
            return ResponseEntity.ok(profiles.stream().map(this::hideSensitiveInfo));
        }
        final var profiles = (nonNull(lastSeenName) || nonNull(lastSeenId))
                ? service.findAll(limit, lastSeenName, lastSeenId)
                : service.findAll(limit);
        return ResponseEntity.ok(profiles.stream().map(this::hideSensitiveInfo));
    }

}

/*
 * 1. Criar cache redis.
 * 2. Testar individualmente cada parâmetro do método get. (levantar casos de
 * teste usando o service)
 * 3. Criar classe de paginação.
 * 4. Ocultar:
 * nascimento (exibição opcional)
 * email (exibição opcional)
 * senha (sem exibição)
 */