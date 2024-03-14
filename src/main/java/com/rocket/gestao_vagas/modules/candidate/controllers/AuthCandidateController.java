package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.use_cases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    private final AuthCandidateUseCase authCandidateUseCase;

    public AuthCandidateController(AuthCandidateUseCase authCandidateUseCase) {
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth")
    @Tag(name = "/candidate/auth", description = "Autenticacao candidato")
    @Operation(
            summary = "Autenticacao de candidatos",
            description = "Esta funcao tem como objetivo autenticar um candidato"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AuthCandidateResponseDto.class)))
            })
    public AuthCandidateResponseDto auth(@Valid @RequestBody AuthCandidateRequestDto dto) {
        return this.authCandidateUseCase.execute(dto);
    }
}
