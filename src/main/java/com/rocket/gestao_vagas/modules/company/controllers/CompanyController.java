package com.rocket.gestao_vagas.modules.company.controllers;

import com.rocket.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocket.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CreateCompanyUseCase createCompanyUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody CompanyEntity companyEntity) {
        var createdUser = this.createCompanyUseCase.execute(companyEntity);

        return ResponseEntity.created(URI.create(createdUser.getId().toString())).build();
    }
}
