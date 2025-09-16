package org.markdowner.api.controller;

import static java.util.Objects.nonNull;

import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String lastSeenName,
            @RequestParam(required = false) final UUID lastSeenId,
            @RequestParam(defaultValue = "100", required = false) final int limit) {
        if (nonNull(id)) {
            return ResponseEntity.of(service.findById(id));
        }
        if (nonNull(email)) {
            return ResponseEntity.of(service.findByEmail(email));
        }
        if (nonNull(name)) {
            if (nonNull(lastSeenName) || nonNull(lastSeenId)) {
                return ResponseEntity.ok(service.findByNameContainingIgnoreCase(limit, lastSeenName, lastSeenId, name));
            }
            return ResponseEntity.ok(service.findByNameContainingIgnoreCase(limit, name));
        }
        if (nonNull(lastSeenName) || nonNull(lastSeenId)) {
            return ResponseEntity.ok(service.findAll(limit, lastSeenName, lastSeenId));
        }
        return ResponseEntity.ok(service.findAll(limit));
    }

}

/*
 * 1. Criar cache redis.
 * 2. Testar individualmente cada parâmetro do método get.
 */