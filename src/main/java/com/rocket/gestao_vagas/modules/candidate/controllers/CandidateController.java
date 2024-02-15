package com.rocket.gestao_vagas.modules.candidate.controllers;

import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import com.rocket.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private final CreateCandidateUseCase createCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
    }

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody() CandidateEntity candidate) {
        var createdCandidate = this.createCandidateUseCase.execute(candidate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
