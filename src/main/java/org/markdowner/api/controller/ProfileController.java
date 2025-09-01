package org.markdowner.api.controller;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

import java.util.UUID;

import org.markdowner.api.service.ProfileService;
import org.markdowner.api.util.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(Routes.PROFILE)
public class ProfileController {
    private final ProfileService service;

    @Operation(description = "Busca o aluno pelo id, email ou nome. Também permite paginação e busca incremental.", responses = {
            @ApiResponse(responseCode = "200", description = "Retorna o(s) aluno(s) conforme o filtro aplicado", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> get(

            @Parameter(description = "ID do aluno para busca direta") @RequestParam(required = false) final UUID id,
            @Parameter(description = "Email do aluno para busca direta") @RequestParam(required = false) final String email,
            @Parameter(description = "Nome do aluno para busca por nome") @RequestParam(required = false) final String name,
            @Parameter(description = "Nome do último aluno retornado na paginação") @RequestParam(required = false) final String lastSeenName,
            @Parameter(description = "ID do último aluno retornado na paginação") @RequestParam(required = false) final UUID lastSeenId,
            @Parameter(description = "Limite de resultados retornados (padrão: 100)") @RequestParam(defaultValue = "100", required = false) final int limit) {

        if (nonNull(id)) {
            return ResponseEntity.of(service.findById(id));
        }
        if (hasText(email)) {
            return ResponseEntity.of(service.findByEmail(email));
        }
        if (hasText(name)) {
            if (hasText(lastSeenName) || nonNull(lastSeenId)) {
                return ResponseEntity.ok(service.findByNameContainingIgnoreCase(limit, lastSeenName, lastSeenId, name));
            }
            return ResponseEntity.ok(service.findByNameContainingIgnoreCase(limit, name));
        }
        if (hasText(lastSeenName) || nonNull(lastSeenId)) {
            return ResponseEntity.ok(service.findAll(limit, lastSeenName, lastSeenId));
        }
        return ResponseEntity.ok(service.findAll(limit));
    }

}

/*
 * 1. Criar fixture de build de profiles.
 * 2. Testar individualmente cada parâmetro do método get.
 * 3. Criar capturador de exceções.
 */