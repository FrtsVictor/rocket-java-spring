package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import com.rocket.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import com.rocket.gestao_vagas.modules.candidate.use_cases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController()
@RequestMapping("/candidate")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;

    private final ProfileCandidateUseCase profileCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase, ProfileCandidateUseCase profileCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
    }

    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody() CandidateEntity candidate) {
        var createdCandidate = this.createCandidateUseCase.execute(candidate);
        return ResponseEntity.created(URI.create("/")).body(createdCandidate.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ProfileCandidateResponseDto> get(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        var cadidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

        return ResponseEntity.ok().body(cadidate);
    }

}
