package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.use_cases.AuthCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    private final AuthCandidateUseCase authCandidateUseCase;

    public AuthCandidateController(AuthCandidateUseCase authCandidateUseCase) {
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth")
    public AuthCandidateResponseDto auth(@Valid @RequestBody AuthCandidateRequestDto dto) {
        return this.authCandidateUseCase.execute(dto);
    }
}
