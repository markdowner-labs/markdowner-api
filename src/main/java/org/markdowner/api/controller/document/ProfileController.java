package org.markdowner.api.controller.document;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProfileController {

    @Operation(description = "Busca o aluno pelo id, email ou nome. Também permite paginação e busca incremental.", responses = {
            @ApiResponse(responseCode = "200", description = "Retorna o(s) aluno(s) conforme o filtro aplicado", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<?> get(
            @Parameter(description = "ID do aluno para busca direta") @RequestParam(required = false) UUID id,
            @Parameter(description = "Email do aluno para busca direta") @RequestParam(required = false) String email,
            @Parameter(description = "Nome do aluno para busca por nome") @RequestParam(required = false) String name,
            @Parameter(description = "Nome do último aluno retornado na paginação") @RequestParam(required = false) String lastSeenName,
            @Parameter(description = "ID do último aluno retornado na paginação") @RequestParam(required = false) UUID lastSeenId,
            @Parameter(description = "Limite de resultados retornados (padrão: 100)") @RequestParam(defaultValue = "100", required = false) int limit);
}
