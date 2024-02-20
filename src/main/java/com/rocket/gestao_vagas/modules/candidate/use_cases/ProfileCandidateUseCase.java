package com.rocket.gestao_vagas.modules.candidate.use_cases;

import com.rocket.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public ProfileCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateResponseDto execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ProfileCandidateResponseDto.builder()
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .description(candidate.getDescription())
                .build();
    }
}
