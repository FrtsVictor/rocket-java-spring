package com.rocket.gestao_vagas.modules.company.controllers;

import com.rocket.gestao_vagas.modules.company.dto.CreateJobRequestDto;
import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import com.rocket.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "job", description = "Criar vaga")
    @Operation(
            summary = "Criaçào de vagas",
            description = "Esta funcao è responsavel pela criacao de vagas por empresa"
    )
    @ApiResponse(
            responseCode = "201",
            content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UUID.class)))
            })
    public ResponseEntity<UUID> create(@Valid @RequestBody CreateJobRequestDto createJobRequestDto, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var job = JobEntity.builder()
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobRequestDto.getDescription())
                .level(createJobRequestDto.getLevel())
                .benefits(createJobRequestDto.getBenefits())
                .build();

        var createdJob = this.createJobUseCase.execute(job);

        return ResponseEntity.created(URI.create("/")).body(createdJob.getId());
    }
}
