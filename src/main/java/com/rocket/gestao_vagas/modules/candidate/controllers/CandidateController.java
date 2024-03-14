package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import com.rocket.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import com.rocket.gestao_vagas.modules.candidate.use_cases.ProfileCandidateUseCase;
import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import com.rocket.gestao_vagas.modules.company.use_cases.ListAllJobsByDescriptionContainingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController()
@RequestMapping("/candidate")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;

    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final ListAllJobsByDescriptionContainingUseCase listAllJobsByDescriptionContainingUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase, ProfileCandidateUseCase profileCandidateUseCase, ListAllJobsByDescriptionContainingUseCase listAllJobsByDescriptionContainingUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
        this.listAllJobsByDescriptionContainingUseCase = listAllJobsByDescriptionContainingUseCase;
    }

    @PostMapping()
    @Tag(name = "candidate", description = "Criar candidato")
    @Operation(
            summary = "Criaçào de candidatos",
            description = "Criacao de candidato"
    )
    @ApiResponse(
            responseCode = "201",
            content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UUID.class)))
            })
    public ResponseEntity<UUID> create(@Valid @RequestBody() CandidateEntity candidate) {
        var createdCandidate = this.createCandidateUseCase.execute(candidate);
        return ResponseEntity.created(URI.create("/")).body(createdCandidate.getId());
    }

    @GetMapping
    @Tag(name = "Candidate", description = "Inforcaçoes do candidato")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ProfileCandidateResponseDto> get(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        var cadidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

        return ResponseEntity.ok().body(cadidate);
    }

    @GetMapping("/job")
    @Tag(name = "Jobs", description = "Lista de vagas disponiveis")
    @Operation(
            summary = "Listagem de vagas",
            description = "Listagem de vagas disponiveis por candidato"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Set<JobEntity>> listAllJobs(@RequestParam String filter) {
        return ResponseEntity.ok(this.listAllJobsByDescriptionContainingUseCase.execute(filter));
    }

}
