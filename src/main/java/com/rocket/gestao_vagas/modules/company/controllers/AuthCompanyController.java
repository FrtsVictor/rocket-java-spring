package com.rocket.gestao_vagas.modules.company.controllers;

import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyRequestDto;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyResponseDto;
import com.rocket.gestao_vagas.modules.company.use_cases.AuthCompanyUseCase;
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
    public ResponseEntity<AuthCompanyResponseDto> authenticate(@RequestBody AuthCompanyRequestDto authCompanyRequestDto) {
        return ResponseEntity.ok().body(this.authCompanyUseCase.execute(authCompanyRequestDto));
    }

}
