package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public AuthCandidateResponseDto auth(@Valid @RequestBody AuthCandidateRequestDto dto) {
        return this.authCandidateUseCase.execute(dto);
    }
}
