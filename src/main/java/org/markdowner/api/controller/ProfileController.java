package org.markdowner.api.controller;

import static java.util.Objects.isNull;
import static org.markdowner.api.util.ResponseEntityUtils.JsonViewer;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

import java.util.UUID;

import org.markdowner.api.service.ProfileService;
import org.markdowner.api.util.ResourceException;
import org.markdowner.api.util.Routes;
import org.markdowner.api.util.Viewer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = Routes.PROFILE, produces = { APPLICATION_JSON_VALUE })
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
        if (!isNull(id)) {
            final var responseBody = service.findById(id).orElseThrow(ResourceException::notFound);
            return ResponseEntity.ok(JsonViewer(Viewer.Public.class, responseBody));
        }
        if (hasText(email)) {
            final var responseBody = service.findByEmail(email).orElseThrow(ResourceException::notFound);
            return ResponseEntity.ok(JsonViewer(Viewer.Public.class, responseBody));
        }
        if (hasText(name)) {
            final var responseBody = (hasText(lastSeenName) || !isNull(lastSeenId))
                    ? service.findByNameContainingIgnoreCase(limit, lastSeenName, lastSeenId, name)
                    : service.findByNameContainingIgnoreCase(limit, name);
            if (isEmpty(responseBody)) {
                throw ResourceException.notFound();
            }
            return ResponseEntity.ok(JsonViewer(Viewer.Public.class, responseBody));
        }
        final var responseBody = (hasText(lastSeenName) || !isNull(lastSeenId))
                ? service.findAll(limit, lastSeenName, lastSeenId)
                : service.findAll(limit);
        if (isEmpty(responseBody)) {
            throw ResourceException.notFound();
        }
        return ResponseEntity.ok(JsonViewer(Viewer.Public.class, responseBody));
    }

}

/*
 * 1. Criar cache redis.
 * 2. Testar individualmente cada parâmetro do método get. (levantar casos de
 * teste usando o service)
 */