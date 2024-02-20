package com.rocket.gestao_vagas.modules.company.controllers;

import com.rocket.gestao_vagas.modules.company.dto.CreateJobRequestDto;
import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import com.rocket.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    private CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public void create(@Valid @RequestBody CreateJobRequestDto createJobRequestDto, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var job = JobEntity.builder()
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobRequestDto.getDescription())
                .level(createJobRequestDto.getLevel())
                .benefits(createJobRequestDto.getBenefits())
                .build();

        this.createJobUseCase.execute(job);
    }
}
