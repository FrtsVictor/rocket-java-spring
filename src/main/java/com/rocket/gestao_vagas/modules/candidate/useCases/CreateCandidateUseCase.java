package com.rocket.gestao_vagas.modules.candidate.useCases;

import com.rocket.gestao_vagas.exceptions.UserFoundException;
import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import com.rocket.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public  CreateCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    public CandidateEntity execute(CandidateEntity candidate){
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        return this.candidateRepository.save(candidate);
    }
}
