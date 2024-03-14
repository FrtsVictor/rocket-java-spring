package com.rocket.gestao_vagas.modules.company.controllers;

import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyRequestDto;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyResponseDto;
import com.rocket.gestao_vagas.modules.company.use_cases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    private final AuthCompanyUseCase authCompanyUseCase;

    public AuthCompanyController(AuthCompanyUseCase authCompanyUseCase) {
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth")
    @Tag(name = "/company/auth", description = "Autenticacao candidato")
    @Operation(
            summary = "Autenticacao de empresas",
            description = "Esta funcao tem como objetivo autenticar uma empresa"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AuthCandidateResponseDto.class)))
            })
    public ResponseEntity<AuthCompanyResponseDto> authenticate(@RequestBody AuthCompanyRequestDto authCompanyRequestDto) {
        return ResponseEntity.ok().body(this.authCompanyUseCase.execute(authCompanyRequestDto));
    }

}
