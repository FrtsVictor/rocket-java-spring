package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import com.rocket.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.rocket.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
    }

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody() CandidateEntity candidate) {
        var createdCandidate = this.createCandidateUseCase.execute(candidate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ProfileCandidateResponseDto get(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        return this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
    }

}
